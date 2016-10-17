package com.jetbrains.edu.learning.projectView;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.ui.JBColor;
import com.jetbrains.edu.learning.StudyUtils;
import com.jetbrains.edu.learning.core.EduNames;
import com.jetbrains.edu.learning.courseFormat.StudyItem;
import com.jetbrains.edu.learning.courseFormat.StudyStatus;
import com.jetbrains.edu.learning.courseFormat.Task;
import com.jetbrains.edu.learning.navigation.StudyNavigator;
import icons.InteractiveLearningIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TaskDirectoryNode extends StudyDirectoryNode {
  @NotNull protected final Project myProject;
  protected final ViewSettings myViewSettings;
  @NotNull protected final Task myTask;

  public TaskDirectoryNode(@NotNull Project project,
                           PsiDirectory value,
                           ViewSettings viewSettings,
                           @NotNull Task task) {
    super(project, value, viewSettings);
    myProject = project;
    myViewSettings = viewSettings;
    myTask = task;
  }

  @Override
  public int getWeight() {
    return myTask.getIndex();
  }

  @Override
  protected void updateImpl(PresentationData data) {
    StudyStatus status = myTask.getStatus();
    String subtaskInfo = myTask.hasSubtasks() ? getSubtaskInfo() : null;
    if (status == StudyStatus.Unchecked) {
      updatePresentation(data, myTask.getName(), JBColor.BLACK, InteractiveLearningIcons.Task, subtaskInfo);
      return;
    }
    boolean isSolved = status == StudyStatus.Solved;
    JBColor color = isSolved ? LIGHT_GREEN : JBColor.RED;
    Icon icon = isSolved ? InteractiveLearningIcons.TaskCompl : InteractiveLearningIcons.TaskProbl;
    updatePresentation(data, myTask.getName(), color, icon, subtaskInfo);
  }

  private String getSubtaskInfo() {
    int index = myTask.getActiveSubtaskIndex() + 1;
    return EduNames.SUBTASK + " " + index + "/" + myTask.getSubtaskNum();
  }

  @Override
  public boolean expandOnDoubleClick() {
    return false;
  }

  @Override
  public boolean canNavigate() {
    return true;
  }

  @Override
  public void navigate(boolean requestFocus) {
    StudyNavigator.navigateToTask(myProject, myTask);
  }

  @Nullable
  @Override
  public AbstractTreeNode modifyChildNode(AbstractTreeNode childNode) {
    Object value = childNode.getValue();
    if (value instanceof PsiElement) {
      PsiFile psiFile = ((PsiElement) value).getContainingFile();
      VirtualFile virtualFile = psiFile.getVirtualFile();
      if (virtualFile == null) {
        return null;
      }
      return StudyUtils.getTaskFile(myProject, virtualFile) != null ? childNode : null;
    }
    return null;
  }

  @Override
  public StudyDirectoryNode createChildDirectoryNode(StudyItem item, PsiDirectory value) {
    return null;
  }
}
