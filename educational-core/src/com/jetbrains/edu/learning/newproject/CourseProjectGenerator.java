/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jetbrains.edu.learning.newproject;

import com.intellij.facet.ui.ValidationResult;
import com.intellij.ide.util.projectWizard.AbstractNewProjectStep;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.DirectoryProjectGenerator;
import com.jetbrains.edu.learning.courseFormat.Course;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import javax.swing.*;

public abstract class CourseProjectGenerator<S> implements DirectoryProjectGenerator<S> {
  @NotNull protected final Course myCourse;

  public CourseProjectGenerator(@NotNull final Course course) {
    myCourse = course;
  }

  public boolean beforeProjectGenerated() {
    return true;
  }

  @TestOnly
  public final void generateTestProject(@NotNull Project project, @NotNull VirtualFile baseDir,
                                        @NotNull S settings, @NotNull Module module) {
    generateProject(project, baseDir, settings, module);
  }

  public void afterProjectGenerated(@NotNull Project project, @NotNull S projectSettings) {
  }

  // 'projectSettings' must have S type but due to some reasons:
  //  * We don't know generic parameter of EduPluginConfigurator after it was gotten through extension point mechanism
  //  * Kotlin and Java do type erasure a little bit differently
  // we use Object instead of S and cast to S when it needed
  @SuppressWarnings("unchecked")
  public void createCourseProject(@NotNull String location, @NotNull Object projectSettings) {
    if (!beforeProjectGenerated()) {
      return;
    }
    Project createdProject = AbstractNewProjectStep.doGenerateProject(null, location, this, projectSettings);
    if (createdProject == null) {
      return;
    }
    afterProjectGenerated(createdProject, (S) projectSettings);
  }

  @Nls
  @NotNull
  @Override
  public String getName() {
    return "";
  }

  @Nullable
  @Override
  public Icon getLogo() {
    return null;
  }

  @NotNull
  @Override
  public ValidationResult validate(@NotNull String baseDirPath) {
    return ValidationResult.OK;
  }
}
