package org.jsfr.jsurfer4s

import java.io.Reader

import org.jsfr.json.JsonSurfer

trait JsonMethods[T <: SurferListener] {
  def parse(in: Reader, listeners: T*): Unit = {
    parse(in, listeners)
  }

  def parse(in: Reader, listeners: Iterable[T]): Unit = {
    val surfer = buildJsonSurfer()
    val builder = surfer.configBuilder()
    for {listener <- listeners} {
      builder.bind(listener.getJsonPath, listener.toJsonPathListener)
    }
    val config = builder.build()
    surfer.surf(in, config)
  }

  def buildJsonSurfer(): JsonSurfer
}
