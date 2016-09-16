package org.jsfr.jsurfer4s.simple

import org.jsfr.json.{JsonPathListener, ParsingContext}
import org.jsfr.jsurfer4s.SurferListener
import org.json.simple.JSONObject

case class SimpleSurferListener(jsonPath: String, func: (JSONObject) => Unit) extends SurferListener {
  def toJsonPathListener(): JsonPathListener = {
    new SimpleJsonListener(func)
  }

  override def getJsonPath: String = jsonPath
}

private class SimpleJsonListener(func: (JSONObject) => Unit) extends JsonPathListener {
  override def onValue(value: AnyRef, context: ParsingContext): Unit = {
    val node = value.asInstanceOf[JSONObject]
    func.apply(node)
  }
}