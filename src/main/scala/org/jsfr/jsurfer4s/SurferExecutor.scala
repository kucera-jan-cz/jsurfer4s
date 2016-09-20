package org.jsfr.jsurfer4s

import org.jsfr.json.JsonSurfer
import org.jsfr.jsurfer4s.listener.{CollectAllListener, CollectOneListener, CompletableListener}

import scala.collection.mutable
import scala.concurrent.{Future, Promise}

class SurferExecutor[I](in: String, surfer: JsonSurfer) {
  private val allListeners = mutable.ArrayBuffer.empty[CompletableListener[I]]
  private val builder = surfer.configBuilder()

  def collectAll[T](jsonPath: String, func: I => T): Future[List[T]] = {
    val promise = Promise[List[T]]()
    val listener = new CollectAllListener[I, T](func, promise)
    register(jsonPath, listener)
    promise.future
  }

  def collectOne[T](jsonPath: String, func: I => T): Future[T] = {
    val promise = Promise[T]()
    val listener = new CollectOneListener[I, T](func, promise)
    register(jsonPath, listener)
    promise.future
  }

  def register(jsonPath: String, listener: CompletableListener[I]): Unit = {
    builder.bind(jsonPath, listener)
    allListeners += listener
  }

  def execute(): Unit = {
    //@TODO - handle multiple invocations
    surfer.surf(in, builder.build())
    for {f <- allListeners} {
      f.complete()
    }
  }
}