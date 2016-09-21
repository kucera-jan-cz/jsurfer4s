package org.jsfr.jsurfer4s.simple

import org.jsfr.json.{JsonPathListener, ParsingContext}
import org.jsfr.jsurfer4s.listener.SurferListener

case class SimpleSurferListener(jsonPath: String, func: (AnyRef) => Unit) extends SurferListener {
  def toJsonPathListener(): JsonPathListener = {
    new SimpleJsonListener(func)
  }

  override def getJsonPath: String = jsonPath
}

private class SimpleJsonListener(func: (AnyRef) => Unit) extends JsonPathListener {
  override def onValue(value: AnyRef, context: ParsingContext): Unit = {
    func.apply(value)
  }
}