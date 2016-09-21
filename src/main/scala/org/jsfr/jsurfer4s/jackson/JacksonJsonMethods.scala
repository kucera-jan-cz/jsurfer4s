package org.jsfr.jsurfer4s.jackson

import com.fasterxml.jackson.databind.JsonNode
import org.jsfr.json.JsonSurfer
import org.jsfr.jsurfer4s.{JsonMethods, SurferExecutor}

trait JacksonJsonMethods extends JsonMethods {
  override def buildJsonSurfer(): JsonSurfer = {
    JsonSurfer.jackson()
  }
}

object JacksonJsonMethods extends JacksonJsonMethods {
  implicit def tupleToListener[I <: JsonNode](tuple: (String, (I) => Unit)): JacksonSurferListener[I] = {
    new JacksonSurferListener(tuple._1, tuple._2)
  }

  implicit object SurferExecutor {
    def apply(in: String): SurferExecutor[JsonNode] = {
      new SurferExecutor[JsonNode](in, JsonSurfer.jackson())
    }
  }

}