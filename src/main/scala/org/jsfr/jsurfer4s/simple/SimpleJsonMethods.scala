package org.jsfr.jsurfer4s.simple

import org.jsfr.json.JsonSurfer
import org.jsfr.jsurfer4s.{JsonMethods, SurferExecutor}
import org.json.simple.JSONObject

trait SimpleJsonMethods extends JsonMethods {
  override def buildJsonSurfer(): JsonSurfer = {
    JsonSurfer.simple()
  }
}

//@TODO check what type is passed to Listeners
object SimpleJsonMethods extends SimpleJsonMethods {
  implicit def tupleToListener(tuple: (String, (JSONObject) => Unit)): SimpleSurferListener = {
    new SimpleSurferListener(tuple._1, tuple._2)
  }

  implicit object SurferExecutor {
    def apply(in: String): SurferExecutor[JSONObject] = {
      new SurferExecutor[JSONObject](in, JsonSurfer.simple())
    }
  }

}
