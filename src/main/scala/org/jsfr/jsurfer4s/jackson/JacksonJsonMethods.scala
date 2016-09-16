package org.jsfr.jsurfer4s.jackson

import com.fasterxml.jackson.databind.JsonNode
import org.jsfr.json.JsonSurfer
import org.jsfr.jsurfer4s.JsonMethods

trait JacksonJsonMethods extends JsonMethods[JacksonSurferListener] {
  override def buildJsonSurfer(): JsonSurfer = {
    JsonSurfer.jackson()
  }
}

object JacksonJsonMethods extends JacksonJsonMethods {
  implicit def tupleToListener(tuple: (String, (JsonNode) => Unit)): JacksonSurferListener = {
    new JacksonSurferListener(tuple._1, tuple._2)
  }
}
