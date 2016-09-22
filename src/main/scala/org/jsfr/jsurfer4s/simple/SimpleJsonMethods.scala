package org.jsfr.jsurfer4s.simple

import org.jsfr.json.JsonSurfer
import org.jsfr.json.SurfingConfiguration.Builder
import org.jsfr.jsurfer4s.{JsonMethods, SurferConfigBuilderFactory, SurferExecutor}

object SimpleJsonMethods extends JsonMethods {
  implicit def tupleToListener[I <: AnyRef](tuple: (String, I => Unit)): SimpleSurferListener[I] = {
    new SimpleSurferListener(tuple._1, tuple._2)
  }

  implicit val builderFactory = new SurferConfigBuilderFactory {
    override def build(): Builder = JsonSurfer.simple().configBuilder()
  }

  implicit object SurferExecutor {
    def apply(in: String): SurferExecutor[AnyRef] = {
      new SurferExecutor[AnyRef](in)
    }
  }

}
