package org.jsfr.jsurfer4s.listener

import org.jsfr.json.JsonPathListener

trait SurferListener {
  def getJsonPath: String

  def toJsonPathListener: JsonPathListener
}
