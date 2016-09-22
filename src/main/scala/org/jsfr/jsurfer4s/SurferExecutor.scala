package org.jsfr.jsurfer4s

import org.jsfr.jsurfer4s.listener.{CollectAllListener, CollectOneListener, CompletableListener}

import scala.collection.mutable
import scala.concurrent.{Future, Promise}

/**
  *
  * @param in            input JSON which should be parsed
  * @param configFactory constructs JsonSurfer configuration including appropriate surfer (Jackson, GSON, Simple).
  * @tparam B base type which all JSON types (object, array, primitive) extends
  */
class SurferExecutor[B](in: String)(implicit configFactory: SurferConfigBuilderFactory) {
  private val allListeners = mutable.ArrayBuffer.empty[CompletableListener[B]]
  private val builder = configFactory.build()
  private var executed = false

  def collectAll[I <: B, T](jsonPath: String, func: I => T): Future[Seq[T]] = {
    val promise = Promise[Seq[T]]()
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
    if (!executed) {
      executed = true
      executeOnlyOnce()
    }
  }

  private def executeOnlyOnce(): Unit = {
    builder.buildAndSurf(in)
    for {f <- allListeners} {
      f.complete()
    }
  }
}