package org.jsfr.jsurfer4s

import java.io.Reader

import org.jsfr.json.JsonSurfer
import org.jsfr.jsurfer4s.listener.SurferListener

trait JsonMethods {
  def parse(in: Reader, listeners: SurferListener*): Unit = {
    parse(in, listeners)
  }

  def parse(in: Reader, listeners: Iterable[SurferListener]): Unit = {
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
