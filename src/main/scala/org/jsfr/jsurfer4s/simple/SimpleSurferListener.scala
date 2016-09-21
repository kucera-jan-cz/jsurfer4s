package org.jsfr.jsurfer4s.simple

import org.jsfr.json.{JsonPathListener, ParsingContext}
import org.jsfr.jsurfer4s.listener.SurferListener

case class SimpleSurferListener[I <: AnyRef](jsonPath: String, func: (I) => Unit) extends SurferListener {
  def toJsonPathListener(): JsonPathListener = {
    new SimpleJsonListener(func)
  }

  override def getJsonPath: String = jsonPath
}

private class SimpleJsonListener[I <: AnyRef](func: (I) => Unit) extends JsonPathListener {
  override def onValue(value: AnyRef, context: ParsingContext): Unit = {
    val node = value.asInstanceOf[I]
    func.apply(node)
  }
}