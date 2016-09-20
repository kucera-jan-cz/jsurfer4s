package org.jsfr.jsurfer4s.listener

import org.jsfr.json.{JsonPathListener, ParsingContext}

/**
  *
  */
trait CompletableListener[I] extends JsonPathListener {
  /**
    * Signal that parsing has been finished and listener can be completed.
    * Typically used for signaling promises/future that parsed results can be returned.
    */
  def complete(): Unit

  def onValue(value: I): Unit

  override final def onValue(value: AnyRef, context: ParsingContext): Unit = {
    onValue(value.asInstanceOf[I])
  }
}
