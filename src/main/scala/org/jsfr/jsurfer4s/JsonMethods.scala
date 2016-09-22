package org.jsfr.jsurfer4s

import java.io.Reader

import org.jsfr.jsurfer4s.listener.SurferListener

trait JsonMethods {
  def parse(in: Reader, listeners: SurferListener*)(implicit builderFactory: SurferConfigBuilderFactory): Unit = {
    parse(in, listeners)
  }

  def parse(in: Reader, listeners: Iterable[SurferListener])(implicit builderFactory: SurferConfigBuilderFactory): Unit = {
    val builder = builderFactory.build()
    for {listener <- listeners} {
      builder.bind(listener.getJsonPath, listener.toJsonPathListener)
    }
    builder.buildAndSurf(in)
  }
}
