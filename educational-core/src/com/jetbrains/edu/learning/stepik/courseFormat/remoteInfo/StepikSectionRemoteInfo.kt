package com.jetbrains.edu.learning.stepik.courseFormat.remoteInfo

class StepikSectionRemoteInfo : StepikRemoteInfo() {
  var id: Int = 0
  var units: MutableList<Int> = mutableListOf()
  var courseId: Int = 0
  var position: Int = 0
}