package org.jsfr.jsurfer4s

import java.io.StringReader

import com.typesafe.scalalogging.Logger
import org.jsfr.jsurfer4s.simple.SimpleJsonMethods._
import org.jsfr.jsurfer4s.simple.SimpleSurferListener
import org.json.simple.JSONObject

object SaxParsingSimple {
  private val logger = Logger(SaxParsingSimple.getClass)

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
      "$.hits.bucket[*]" -> { (node: JSONObject) => {
        logger.info("B: {}", node)
      }
      },
      "$.status.X" -> { (node: JSONObject) => {
        logger.info("X: {}", node)
      }
      }
    )
    val reader2 = new StringReader(json)
    val listeners = List[SimpleSurferListener](
      "$.status.X" -> { (node: JSONObject) => {
        logger.info("X: {}", node)
      }
      },
      "$.hits.bucket[*]" -> { (node: JSONObject) => {
        logger.info("B: {}", node)
      }
      }
    )
    parse(reader2, listeners)
  }
}
