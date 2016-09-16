package org.jsfr.jsurfer4s

import java.io.StringReader

import com.fasterxml.jackson.databind.JsonNode
import com.typesafe.scalalogging.Logger
import org.jsfr.jsurfer4s.jackson.JacksonJsonMethods._
import org.jsfr.jsurfer4s.jackson.JacksonSurferListener

object SaxParsingJson {
  private val logger = Logger(SaxParsingJson.getClass)

  def main(args: Array[String]) {
    val json =
      """
        	 			|{
        	 			|	"status" : {"X": 1},
        	 			| "hits": {
        	 			| 	"bucket" : [
        	 			|  		{"A": 1},
        	 			|    	{"B": 1},
        	 			|     {"C": 2}
        	 			|   ]
        	 			| }
        	 			|}
      			""".stripMargin
    val reader = new StringReader(json)
    parse(reader,
      "$.hits.bucket[*]" -> { (node: JsonNode) => {
        logger.info("B: {}", node)
      }
      },
      "$.status.X" -> { (node: JsonNode) => {
        logger.info("X: {}", node)
      }
      }
    )
    val reader2 = new StringReader(json)
    val listeners = List[JacksonSurferListener](
      "$.status.X" -> { (node: JsonNode) => {
        logger.info("X: {}", node)
      }
      },
      "$.hits.bucket[*]" -> { (node: JsonNode) => {
        logger.info("B: {}", node)
      }
      }
    )
    parse(reader2, listeners)
  }
}
