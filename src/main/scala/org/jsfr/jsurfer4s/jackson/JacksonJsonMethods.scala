package org.jsfr.jsurfer4s.jackson

import com.fasterxml.jackson.databind.JsonNode
import org.jsfr.json.JsonSurfer
import org.jsfr.json.SurfingConfiguration.Builder
import org.jsfr.jsurfer4s.{JsonMethods, SurferConfigBuilderFactory, SurferExecutor}

object JacksonJsonMethods extends JsonMethods {
  implicit def tupleToListener[I <: JsonNode](tuple: (String, (I) => Unit)): JacksonSurferListener[I] = {
    new JacksonSurferListener(tuple._1, tuple._2)
  }

  implicit val builderFactory = new SurferConfigBuilderFactory {
    override def build(): Builder = JsonSurfer.jackson().configBuilder()
  }

  implicit object SurferExecutor {
    def apply(in: String): SurferExecutor[JsonNode] = {
      new SurferExecutor[JsonNode](in)
    }
  }

}