package com.jetbrains.edu.python.coursecreator;

import com.intellij.openapi.ui.ComboBox;
import com.jetbrains.edu.coursecreator.actions.CCChangeCourseInfo;
import com.jetbrains.edu.coursecreator.ui.CCCourseInfoPanel;
import com.jetbrains.edu.learning.courseFormat.Course;
import com.jetbrains.python.PythonLanguage;
import com.jetbrains.python.psi.LanguageLevel;

import javax.swing.*;

import static com.jetbrains.edu.python.learning.PyConfigurator.PYTHON_2;
import static com.jetbrains.edu.python.learning.PyConfigurator.PYTHON_3;

@SuppressWarnings("ComponentNotRegistered") // Edu-Python.xml
public class PyCCChangeCourseInfo extends CCChangeCourseInfo {
  public static final String ALL_VERSIONS = "All versions";

  public PyCCChangeCourseInfo() {
    super();
  }

  protected void setVersion(Course course, CCCourseInfoPanel panel) {
    if (PythonLanguage.getInstance().equals(course.getLanguageById())) {
      String version = panel.getLanguageVersion();
      String language = PythonLanguage.getInstance().getID();
      if (version != null && !ALL_VERSIONS.equals(version)) {
        language += " " + version;
      }
      course.setLanguage(language);
    }
  }

  protected void setupLanguageLevels(Course course, CCCourseInfoPanel panel) {
    if (PythonLanguage.getInstance().equals(course.getLanguageById())) {
      JLabel languageLevelLabel = panel.getLanguageLevelLabel();
      languageLevelLabel.setText("Python:");
      languageLevelLabel.setVisible(true);
      ComboBox<String> languageLevelCombobox = panel.getLanguageLevelCombobox();
      languageLevelCombobox.addItem(ALL_VERSIONS);
      languageLevelCombobox.addItem(PYTHON_3);
      languageLevelCombobox.addItem(PYTHON_2);
      for (LanguageLevel level : LanguageLevel.values()) {
        languageLevelCombobox.addItem(level.toString());
      }
      languageLevelCombobox.setVisible(true);
      final String version = course.getLanguageVersion();
      if (version != null) {
        languageLevelCombobox.setSelectedItem(version);
      }
      else {
        languageLevelCombobox.setSelectedItem(ALL_VERSIONS);
      }
    }
  }
}
