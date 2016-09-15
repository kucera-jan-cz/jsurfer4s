package org.jsfr.jsurfer4s
import java.io.Reader

import org.jsfr.json.provider.JacksonProvider
import org.jsfr.json.{JacksonParser, JsonSurfer}

trait JacksonJsonMethods {
  def parse(in: Reader, listeners: SurferListener*): Unit = {
    parse(in, listeners)
  }

  def parse(in: Reader, listeners: Iterable[SurferListener]): Unit = {
    val surfer = new JsonSurfer(JacksonParser.INSTANCE, JacksonProvider.INSTANCE)
    val builder = surfer.configBuilder()
    for{listener <- listeners} {
      builder.bind(listener.jsonPath, listener.toJsonPathListener())
    }
    val config = builder.build()
    surfer.surf(in, config)
  }
}

object JacksonJsonMethods extends JacksonJsonMethods
