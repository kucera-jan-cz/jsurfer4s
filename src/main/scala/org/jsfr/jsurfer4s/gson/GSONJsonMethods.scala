package org.jsfr.jsurfer4s.gson

import com.google.gson.JsonElement
import org.jsfr.json.JsonSurfer
import org.jsfr.jsurfer4s.{JsonMethods, SurferExecutor}

trait GSONJsonMethods extends JsonMethods {
  override def buildJsonSurfer(): JsonSurfer = {
    JsonSurfer.gson()
  }
}

//@TODO check what type is passed to Listeners
object GSONJsonMethods extends GSONJsonMethods {
  implicit def tupleToListener[I <: JsonElement](tuple: (String, (I) => Unit)): GSONSurferListener[I] = {
    new GSONSurferListener(tuple._1, tuple._2)
  }

  implicit object SurferExecutor {
    def apply(in: String): SurferExecutor[JsonElement] = {
      new SurferExecutor[JsonElement](in, JsonSurfer.gson())
    }
  }
}
