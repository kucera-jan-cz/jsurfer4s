package org.jsfr.jsurfer4s

import org.jsfr.json.JsonSurfer
import org.jsfr.jsurfer4s.listener.{CollectAllListener, CollectOneListener, CompletableListener}

import scala.collection.mutable
import scala.concurrent.{Future, Promise}

/**
  *
  * @param in     input JSON which should be parsed
  * @param surfer corresponding JsonSurfer defined to appropriate JSON library registered
  * @tparam B base type which all JSON types (object, array, primitive) extends
  */
class SurferExecutor[B](in: String, surfer: JsonSurfer) {
  private val allListeners = mutable.ArrayBuffer.empty[CompletableListener[B]]
  private val builder = surfer.configBuilder()

  def collectAll[I <: B, T](jsonPath: String, func: I => T): Future[List[T]] = {
    val promise = Promise[List[T]]()
    val listener = new CollectAllListener[I, T](func, promise)
    register(jsonPath, listener)
    promise.future
  }

  def collectOne[I <: B, T](jsonPath: String, func: I => T): Future[T] = {
    val promise = Promise[T]()
    val listener = new CollectOneListener[I, T](func, promise)
    register(jsonPath, listener)
    promise.future
  }

  def register[I <: B](jsonPath: String, listener: CompletableListener[I]): Unit = {
    builder.bind(jsonPath, listener)
    allListeners += listener.asInstanceOf[CompletableListener[B]]
  }

  def execute(): Unit = {
    //@TODO - handle multiple invocations
    surfer.surf(in, builder.build())
    for {f <- allListeners} {
      f.complete()
    }
  }
}