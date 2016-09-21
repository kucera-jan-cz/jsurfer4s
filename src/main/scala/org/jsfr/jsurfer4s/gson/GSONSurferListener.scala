package org.jsfr.jsurfer4s.gson

import com.google.gson.JsonElement
import org.jsfr.json.{JsonPathListener, ParsingContext}
import org.jsfr.jsurfer4s.listener.SurferListener

/**
  *
  * @param jsonPath
  * @param func
  * @tparam I
  */
case class GSONSurferListener[I <: JsonElement](jsonPath: String, func: I => Unit) extends SurferListener {
  override def getJsonPath: String = jsonPath

  override def toJsonPathListener(): JsonPathListener = {
    new GSONPathListener(func)
  }

}

private class GSONPathListener[I <: JsonElement](func: I => Unit) extends JsonPathListener {
  override def onValue(value: AnyRef, context: ParsingContext): Unit = {
    val node = value.asInstanceOf[I]
    func.apply(node)
  }
}
