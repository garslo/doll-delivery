package com.garslo.dolldelivery

import org.specs2._

object ExtractorSpec extends mutable.Specification {
  "Extractor" should {
    val validator = new Validator(MapSyntax)
    val extractor = new Extractor(validator)

    "extract proper information from lines" in {
      val line = """Map("startLocation" -> "initial", "endLocation" -> "final", "distance" -> 10)"""
      val edge = extractor.extractEdge(line)

      edge.get("startLocation") must be equalTo(Some("initial"))
      edge.get("endLocation") must be equalTo(Some("final"))
      edge.get("distance") must be equalTo(Some(10))
    }

    "extract the proper number of edges" in {
      val edges = Seq(
        """Map("startLocation" -> "initial", "endLocation" -> "final", "distance" -> 10)""",
        "bad line, it's ignored",
        """Map("startLocation" -> "Kruthika's abode", "endLocation" -> "Mark's crib", "distance" -> 9)"""
      )
      val result = extractor.getEdges(edges)

      result.length must be equalTo(2)
    }

    "extract the proper start vertex" in {
      val lines = Seq(
        """startingLocation: "the start location"""",
        """targetLocation: "the target location""""
      )
      val result = extractor.getVertex(lines, "start")

      result must be equalTo("the start location")
    }

    "extract the proper end vertex" in {
      val lines = Seq(
        """startingLocation: "the start location"""",
        """targetLocation: "the target location""""
      )
      val result = extractor.getVertex(lines, "end")

      result must be equalTo("the target location")
    }
  }
}
