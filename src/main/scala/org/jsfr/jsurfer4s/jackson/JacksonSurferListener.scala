package org.jsfr.jsurfer4s.jackson

import com.fasterxml.jackson.databind.JsonNode
import org.jsfr.json.{JsonPathListener, ParsingContext}
import org.jsfr.jsurfer4s.SurferListener

case class JacksonSurferListener(jsonPath: String, func: (JsonNode) => Unit) extends SurferListener {
  def toJsonPathListener(): JsonPathListener = {
    new JacksonJsonListener(func)
  }

  override def getJsonPath: String = jsonPath
}

private class JacksonJsonListener(func: (JsonNode) => Unit) extends JsonPathListener {
  override def onValue(value: AnyRef, context: ParsingContext): Unit = {
    val node = value.asInstanceOf[JsonNode]
    func.apply(node)
  }
}