package org.jsfr.jsurfer4s.listener

import scala.concurrent.Promise
import scala.util.Try

class CollectAllListener[I, T](func: (I) => T, promise: Promise[List[T]]) extends CompletableListener[I] {
  private var result = new scala.collection.mutable.ArrayBuffer[T]()

  override def complete(): Unit = {
    require(!promise.isCompleted, "Promise must be incomplete")
    //@TODO - consider whether List is appropriate
    promise.complete(Try {
      result.toList
    })
  }

  @Override
  override def onValue(value: I): Unit = {
    val parsed = func.apply(value)
    result += parsed
  }
}
