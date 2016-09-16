package org.jsfr.jsurfer4s

import java.io.StringReader

import com.google.gson.JsonElement
import com.typesafe.scalalogging.Logger
import org.jsfr.jsurfer4s.gson.GSONJsonMethods._
import org.jsfr.jsurfer4s.gson.GSONSurferListener


object SaxParsingGSON {
  private val logger = Logger(SaxParsingGSON.getClass)

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
      "$.hits.bucket[*]" -> { (node: JsonElement) => {
        logger.info("B: {}", node)
      }
      },
      "$.status.X" -> { (node: JsonElement) => {
        logger.info("X: {}", node)
      }
      }
    )
    val reader2 = new StringReader(json)
    val listeners = List[GSONSurferListener](
      "$.status.X" -> { (node: JsonElement) => {
        logger.info("X: {}", node)
      }
      },
      "$.hits.bucket[*]" -> { (node: JsonElement) => {
        logger.info("B: {}", node)
      }
      }
    )
    parse(reader2, listeners)
  }
}
