package com.jetbrains.edu.learning.twitter;

import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.project.Project;
import com.jetbrains.edu.learning.checker.CheckListener;
import com.jetbrains.edu.learning.StudyTwitterPluginConfigurator;
import com.jetbrains.edu.learning.courseFormat.CheckStatus;
import com.jetbrains.edu.learning.courseFormat.tasks.Task;
import org.jetbrains.annotations.NotNull;

public class StudyTwitterAction implements CheckListener {

  private CheckStatus myStatusBeforeCheck;

  @Override
  public void beforeCheck(@NotNull Project project, @NotNull Task task) {
    myStatusBeforeCheck = task.getStatus();
  }

  @Override
  public void afterCheck(@NotNull Project project, @NotNull Task task) {
    for (StudyTwitterPluginConfigurator twitterPluginConfigurator : Extensions.getExtensions(StudyTwitterPluginConfigurator.EP_NAME)) {
      if (twitterPluginConfigurator.askToTweet(project, task, myStatusBeforeCheck)) {
        StudyTwitterUtils.createTwitterDialogAndShow(project, twitterPluginConfigurator, task);
      }
    }
  }
}
