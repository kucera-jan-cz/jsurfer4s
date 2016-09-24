package org.jsfr.jsurfer4s.examples

import java.io.{Reader, StringReader}

import com.fasterxml.jackson.databind.JsonNode
import com.jayway.jsonpath.JsonPath
import com.typesafe.scalalogging.Logger
import org.jsfr.jsurfer4s.jackson.JacksonJsonMethods._
import org.jsfr.jsurfer4s.listener.SurferListener
import org.scalatest.FunSuite

import scala.io.Source

class ParseExample extends FunSuite {
	private val logger = Logger(classOf[ParseExample])

	test("show all names") {
		parse(loadJson(),
			"$.Search[*].Title" -> { (node: JsonNode) => logger.info("{}", node) }
		)
	}

	test("show only movies") {
		val movieFilter = (node: JsonNode) => {
			if (node.get("Type").asText("").equals("movie")) {
				logger.info("{}", node.get("Title"))
			}
		}
		parse(loadJson(),
			"$.Search[*]" -> movieFilter
		)
	}

	test("show only new games or movies") {
		val conditionFilter = (node: JsonNode) => node.get("Year").asInt(0) > 2000
		val gameOrMovieFilter = (node: JsonNode) => List("game", "movie").contains(node.get("Type").asText(""))
		val conditions = List(conditionFilter, gameOrMovieFilter)
		val surferListeners: List[SurferListener] = List(
			"$.Search[*]" -> { (node: JsonNode) =>
				if (conditions.exists(f => f.apply(node))) {
					logger.info("{}", node)
				}
			}
		)
		parse(loadJson(), surferListeners)
	}

	private def loadJson(): Reader = {
		val json = Source.fromInputStream(getClass.getResourceAsStream("/json/movies.json")).mkString
		val reader = new StringReader(json)
		reader
	}
}
