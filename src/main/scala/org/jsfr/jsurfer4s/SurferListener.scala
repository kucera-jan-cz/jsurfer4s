package org.jsfr.jsurfer4s

import com.fasterxml.jackson.databind.JsonNode
import org.jsfr.json.{JsonPathListener, ParsingContext}

case class SurferListener(jsonPath: String, func: (JsonNode) => Unit) {
  def toJsonPathListener(): JsonPathListener = {
    new JsonListener(func)
  }
}

object SurferListener {
  implicit def tupleToListener(tuple: Tuple2[String, (JsonNode) => Unit]): SurferListener = {
    new SurferListener(tuple._1, tuple._2)
  }
}

private class JsonListener(func: (JsonNode) => Unit) extends JsonPathListener {
  override def onValue(value: AnyRef, context: ParsingContext): Unit = {
    val node = value.asInstanceOf[JsonNode]
    func.apply(node)
  }
}