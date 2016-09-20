package org.jsfr.jsurfer4s.gson

import com.google.gson.JsonElement
import org.jsfr.json.{JsonPathListener, ParsingContext}
import org.jsfr.jsurfer4s.listener.SurferListener

case class GSONSurferListener(jsonPath: String, func: (JsonElement) => Unit) extends SurferListener {
  override def getJsonPath: String = jsonPath

  override def toJsonPathListener(): JsonPathListener = {
    new GSONPathListener(func)
  }

}

private class GSONPathListener(func: (JsonElement) => Unit) extends JsonPathListener {
  override def onValue(value: AnyRef, context: ParsingContext): Unit = {
    val node = value.asInstanceOf[JsonElement]
    func.apply(node)
  }
}
