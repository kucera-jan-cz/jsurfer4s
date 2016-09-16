package org.jsfr.jsurfer4s.gson

import com.google.gson.JsonElement
import org.jsfr.json.JsonSurfer
import org.jsfr.jsurfer4s.JsonMethods

trait GSONJsonMethods extends JsonMethods[GSONSurferListener] {
  override def buildJsonSurfer(): JsonSurfer = {
    JsonSurfer.gson()
  }
}

object GSONJsonMethods extends GSONJsonMethods {
  implicit def tupleToListener(tuple: (String, (JsonElement) => Unit)): GSONSurferListener = {
    new GSONSurferListener(tuple._1, tuple._2)
  }
}
