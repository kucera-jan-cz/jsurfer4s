package org.jsfr.jsurfer4s.gson

import com.google.gson.JsonElement
import org.jsfr.json.JsonSurfer
import org.jsfr.json.SurfingConfiguration.Builder
import org.jsfr.jsurfer4s.{JsonMethods, SurferConfigBuilderFactory, SurferExecutor}

object GSONJsonMethods extends JsonMethods {
  implicit def tupleToListener[I <: JsonElement](tuple: (String, (I) => Unit)): GSONSurferListener[I] = {
    new GSONSurferListener(tuple._1, tuple._2)
  }

  implicit val builderFactory = new SurferConfigBuilderFactory {
    override def build(): Builder = JsonSurfer.gson().configBuilder()
  }

  implicit object SurferExecutor {
    def apply(in: String): SurferExecutor[JsonElement] = {
      new SurferExecutor[JsonElement](in)
    }
  }
}
