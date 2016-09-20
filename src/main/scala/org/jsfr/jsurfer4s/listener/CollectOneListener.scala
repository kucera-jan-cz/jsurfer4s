package org.jsfr.jsurfer4s.listener

import scala.concurrent.Promise
import scala.util.Try

class CollectOneListener[I, T](func: (I) => T, promise: Promise[T]) extends CompletableListener[I] {

  override def complete(): Unit = {
    promise.tryFailure(new NoSuchElementException)
  }

  @Override
  override def onValue(value: I): Unit = {
    if (!promise.isCompleted) {
      promise.complete(Try {
        func.apply(value)
      })
    }
  }
}
