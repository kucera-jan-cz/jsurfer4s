package org.jsfr.jsurfer4s.simple

import org.jsfr.json.JsonSurfer
import org.jsfr.jsurfer4s.JsonMethods
import org.json.simple.JSONObject

trait SimpleJsonMethods extends JsonMethods[SimpleSurferListener] {
  override def buildJsonSurfer(): JsonSurfer = {
    JsonSurfer.simple()
  }
}

object SimpleJsonMethods extends SimpleJsonMethods {
  implicit def tupleToListener(tuple: (String, (JSONObject) => Unit)): SimpleSurferListener = {
    new SimpleSurferListener(tuple._1, tuple._2)
  }
}
