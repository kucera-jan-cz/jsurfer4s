package org.jsfr.jsurfer4s

import org.jsfr.json.SurfingConfiguration

/**
  * Factory building SurfingConfiguration builder with already associated JsonSurfer.
  * Used for altering Surfer configuration (f.e with ErrorStrategy etc).
  *
  * Example:<pre>
  * implicit val builderFactory = new SurferConfigBuilderFactory {
  * val strategy: ErrorHandlingStrategy = ...
  * override def build(): SurfingConfiguration.Builder = {
  *      JsonSurfer.jason().configBuilder().withErrorStrategy(strategy)
  * }
  * }
  * </pre>
  *
  */
trait SurferConfigBuilderFactory {
  def build(): SurfingConfiguration.Builder
}
