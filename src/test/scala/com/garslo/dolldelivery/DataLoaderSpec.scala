package com.garslo.dolldelivery

import scala.io.Source
import org.specs2._

object DataLoaderSpec extends mutable.Specification {
//  "LoaderForMapSyntax" should {
//    val dataLoader = new LoaderForMapSyntax()
//    val dataFiles = Seq(
//      "src/test/scala/com/garslo/dolldelivery/resources/test_data_1.txt"
//    )
//
//    "fail on non-existent input file" in {
//      val result = dataLoader.load("this-file-doesn't-exist")
//      result must_==(None)
//    }
//
//    "pass on existing file" in {
//      val result = dataLoader.load(dataFiles(0))
//      result match {
//        case Some(edges) => success
//        case None => failure
//      }
//    }
//  }
}

object MapSyntaxSpec extends mutable.Specification {
  "ValidatorForMapSyntax" should {
    val validator = new MapSyntaxValidator()
    "recognize valid edge lines" in {
      val line = """Map("startLocation" -> "Kruthika's abode", "endLocation" -> "Mark's crib", "distance" -> 9)"""
      validator.isEdge(line) must beTrue
    }

    "fail on invalid edge lines" in {
      validator.isEdge("Not an edge line") must beFalse
    }

    "recognize the initial vertex" in {
      val declaration = """startingLocation: "foo""""
      val result = validator.isStartVertexDelcaration(declaration)

      result must beTrue

      val badDeclaration = """notAStartingLocation: "nothing""""
      val badResult = validator.isStartVertexDelcaration(badDeclaration)

      badResult must beFalse
    }

    "recognize the final vertex" in {
      val declaration = """targetLocation: "foo""""
      val result = validator.isEndVertexDelcaration(declaration)

      result must beTrue

      val badDeclaration = """notATargetLocation: "nothing""""
      val badResult = validator.isEndVertexDelcaration(badDeclaration)

      badResult must beFalse
    }

    "detect start and end declaration existence" in {
      val goodLines = Seq(
        """startingLocation: "somewhere"""",
        """targetLocation: "else""""
      )
      val result = validator.hasStartEndDeclarations(goodLines)
      result must beTrue

      val badLines = Seq(
        """startingLocation: "is ok"""",
        """noTargetLocation: "is not ok""""
      )
      val badResult = validator.hasStartEndDeclarations(badLines)
      badResult must beFalse
    }
  }
}

object ExtractorSpec extends mutable.Specification {
  "Extractor" should {
    val validator = new MapSyntaxValidator()
    val extractor = new Extractor(validator)

    "extract proper information from lines" in {
      val line = """Map("startLocation" -> "initial", "endLocation" -> "final", "distance" -> 10)"""
      val edge = extractor.extractEdge(line)

      edge.get("startLocation") must_==(Some("initial"))
      edge.get("endLocation") must_==(Some("final"))
      edge.get("distance") must_==(Some(10))
    }

    "extract the proper number of edges" in {
      val edges = Seq(
        """Map("startLocation" -> "initial", "endLocation" -> "final", "distance" -> 10)""",
        "bad line, it's ignored",
        """Map("startLocation" -> "Kruthika's abode", "endLocation" -> "Mark's crib", "distance" -> 9)"""
      )
      val result = extractor.getEdges(edges)

      result.length must_==(2)
    }

    "extract the proper start vertex" in {
      val lines = Seq(
        """startingLocation: "the start location"""",
        """targetLocation: "the target location""""
      )
      val result = extractor.getStartVertex(lines)

      result must_==("the start location")
    }

    "extract the proper end vertex" in {
      val lines = Seq(
        """startingLocation: "the start location"""",
        """targetLocation: "the target location""""
      )
      val result = extractor.getEndVertex(lines)

      result must_==("the target location")
    }
  }
}
