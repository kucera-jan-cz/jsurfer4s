package org.jsfr.jsurfer4s

import com.fasterxml.jackson.databind.JsonNode
import org.jsfr.json.JsonPathListener
import org.jsfr.jsurfer4s.jackson.JacksonSurferListener

trait SurferListener {
  def getJsonPath: String

  def toJsonPathListener: JsonPathListener

  implicit def tupleToListener(tuple: (String, (JsonNode) => Unit)): JacksonSurferListener = {
    new JacksonSurferListener(tuple._1, tuple._2)
  }
}
