package org.jsfr.jsurfer4s

import java.io.StringReader

import com.typesafe.scalalogging.Logger
import org.jsfr.jsurfer4s.listener.{CompletableListener, SurferListener}
import org.jsfr.jsurfer4s.simple.SimpleJsonMethods._
import org.json.simple.JSONObject

import scala.util.{Failure, Success}

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
      "$.status.X" -> { (node: AnyRef) => {
        logger.info("X: {}", node)
      }
      }
    )
    val reader2 = new StringReader(json)
    val listeners = List[SurferListener](
      "$.status.X" -> { (node: AnyRef) => {
        logger.info("X: {}", node)
      }
      },
      "$.hits.bucket[*]" -> { (node: AnyRef) => {
        logger.info("B: {}", node)
      }
      }
    )
    parse(reader2, listeners)
    import scala.concurrent.ExecutionContext.Implicits.global
    val async = SurferExecutor(json)
    val xValue = async.collectOne("$.status", (node: JSONObject) => {
      val value: Int = node.get("X").asInstanceOf[Int]
      value
    })
    val hits = async.collectAll("$.hits.bucket[*]", (node: JSONObject) => {
      val result = node.keySet().iterator().next().toString
      result
    })
    async.register("$.hits.bucket[*]", new BucketCounter())
    xValue.onSuccess {
      case x: Int => logger.info("Future X: {}", x)
    }
    async.execute()
    logger.info("Complete: {}", hits.isCompleted)
    hits.onComplete(
      {
        case Success(buckets) => logger.info("Future Buckets: {}", buckets)
        case Failure(ex) => logger.info("Future failed:", ex)
      }
    )
    Thread.sleep(1000)
  }

  private class BucketCounter[I] extends CompletableListener[I] {
    private var counter = 0

    override def complete(): Unit = {
      logger.info("Found {} buckets", counter)
    }

    override def onValue(value: I): Unit = {
      counter += 1
    }
  }

}
