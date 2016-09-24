package org.jsfr.jsurfer4s.examples

import java.io.{Reader, StringReader}

import com.fasterxml.jackson.databind.JsonNode
import com.typesafe.scalalogging.Logger
import org.jsfr.jsurfer4s.jackson.JacksonJsonMethods._
import org.scalatest.FunSuite

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.io.Source

class ExecutorExample extends FunSuite {
	private val logger = Logger(classOf[ExecutorExample])
	test("collect example") {
		import scala.concurrent.ExecutionContext.Implicits.global
		val exec = SurferExecutor(loadJson())

		val onlyMovie = (node: JsonNode) => {
			node.get("Type").asText("") match {
				case "movie" => Some(node.get("Title").asText())
				case _ => None
			}
		}

		val totalFuture: Future[Int] = exec.collectOne("$.totalResults", { node: JsonNode => node.asInt() })
		val moviesFuture: Future[Seq[Option[String]]] = exec.collectAll("$.Search[*]", onlyMovie)
		exec.execute()
		val movies = Await.result(moviesFuture, Duration.Zero).flatten
		print(s"From total ${Await.result(totalFuture, Duration.Zero)} of items only ${movies.size} are movies, their list:{}")
		for{movie <- movies} {
			logger.info("{}", movie)
		}
	}

	private def loadJson(): String = {
		val json = Source.fromInputStream(getClass.getResourceAsStream("/json/movies.json")).mkString
		json
	}

}
