package org.jsfr.jsurfer4s.simple

import org.jsfr.json.JsonSurfer
import org.jsfr.jsurfer4s.{JsonMethods, SurferExecutor}

trait SimpleJsonMethods extends JsonMethods {
  override def buildJsonSurfer(): JsonSurfer = {
    JsonSurfer.simple()
  }
}

//@TODO check what type is passed to Listeners
object SimpleJsonMethods extends SimpleJsonMethods {
  implicit def tupleToListener[I <: AnyRef](tuple: (String, I => Unit)): SimpleSurferListener[I] = {
    new SimpleSurferListener(tuple._1, tuple._2)
  }

  implicit object SurferExecutor {
    def apply(in: String): SurferExecutor[AnyRef] = {
      new SurferExecutor[AnyRef](in, JsonSurfer.simple())
    }
  }

}
