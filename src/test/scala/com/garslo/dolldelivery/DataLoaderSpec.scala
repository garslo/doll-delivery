package com.garslo.dolldelivery

import scala.io.Source
import org.specs2._

object DataLoaderSpec extends mutable.Specification {
  "DataLoader" should {
    val dataLoader = new DataLoader()
    val dataFiles = Seq(
      "src/test/scala/com/garslo/dolldelivery/resources/test_data_1.txt"
    )

    "fail on non-existent input file" in {
      val result = dataLoader.load("this-file-doesn't-exist")
      result must_==(None)
    }

    "pass on existing file" in {
      val result = dataLoader.load(dataFiles(0))
      result match {
        case Some(edges) => success
        case None => failure
      }
    }

    "recognize valid edge lines" in {
      val line = """Map("startLocation" -> "Kruthika's abode", "endLocation" -> "Mark's crib", "distance" -> 9)"""
      dataLoader.isEdge(line) must beTrue
    }

    "fail on invalid edge lines" in {
      dataLoader.isEdge("Not an edge line") must beFalse
    }

    "extract proper information from lines" in {
      val line = """Map("startLocation" -> "initial", "endLocation" -> "final", "distance" -> 10)"""
      val edge = dataLoader.extractEdge(line)

      edge.get("startLocation") must_==(Some("initial"))
      edge.get("endLocation") must_==(Some("final"))
      edge.get("distance") must_==(Some(10))
    }

    "extracts the proper number of edges" in {
      val edges = Seq(
        """Map("startLocation" -> "initial", "endLocation" -> "final", "distance" -> 10)""",
        "bad line, it's ignored",
        """Map("startLocation" -> "Kruthika's abode", "endLocation" -> "Mark's crib", "distance" -> 9)"""
      )
      val result = dataLoader.getEdges(edges)

      result.length must_==(2)
    }

    "recognizes the initial vertex" in {
      val declaration = """startingLocation: "foo""""
      val result = dataLoader.isStartVertexDelcaration(declaration)

      result must beTrue

      val badDeclaration = """notAStartingLocation: "nothing""""
      val badResult = dataLoader.isStartVertexDelcaration(badDeclaration)

      badResult must beFalse
    }

    "recognizes the final vertex" in {
      val declaration = """targetLocation: "foo""""
      val result = dataLoader.isEndVertexDelcaration(declaration)

      result must beTrue

      val badDeclaration = """notATargetLocation: "nothing""""
      val badResult = dataLoader.isEndVertexDelcaration(badDeclaration)

      badResult must beFalse
    }

    "extracts the proper intial vertex" in {
    "detects start and end declaration existence" in {
      val goodLines = Seq(
        """startingLocation: "somewhere"""",
        """targetLocation: "else""""
      )
      val result = dataLoader.hasStartEndDeclarations(goodLines)

      result must beTrue

      val badLines = Seq(
        """startingLocation: "is ok"""",
        """noTargetLocation: "is not ok""""
      )
      val badResult = dataLoader.hasStartEndDeclarations(badLines)

      badResult must beFalse
    }

    "extracts the proper start vertex" in {
      val lines = Seq(
        """startingLocation: "the start location"""",
        """targetLocation: "the target location""""
      )
      val result = dataLoader.getStartVertex(lines)

      result must_==("the start location")
    }

    "extracts the proper end vertex" in {
      val lines = Seq(
        """startingLocation: "the start location"""",
        """targetLocation: "the target location""""
      )
      val result = dataLoader.getEndVertex(lines)

      result must_==("the target location")
    }
  }
}
