package org.jsfr.jsurfer4s.jackson

import com.fasterxml.jackson.databind.JsonNode
import org.jsfr.json.{JsonPathListener, ParsingContext}
import org.jsfr.jsurfer4s.listener.SurferListener

case class JacksonSurferListener[I <: JsonNode](jsonPath: String, func: (I) => Unit) extends SurferListener {
  def toJsonPathListener(): JsonPathListener = {
    new JacksonJsonListener(func)
  }

  override def getJsonPath: String = jsonPath
}

private class JacksonJsonListener[I <: JsonNode](func: (I) => Unit) extends JsonPathListener {
  override def onValue(value: AnyRef, context: ParsingContext): Unit = {
    val node = value.asInstanceOf[I]
    func.apply(node)
  }
}