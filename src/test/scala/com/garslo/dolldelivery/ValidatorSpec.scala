package com.garslo.dolldelivery

import org.specs2._

object ValidatorSpec extends mutable.Specification {
  "Syntax Validator" should {
    val validator = new Validator(MapSyntax)
    "recognize valid edge lines" in {
      val line = """Map("startLocation" -> "Kruthika's abode", "endLocation" -> "Mark's crib", "distance" -> 9)"""
      validator.isEdge(line) must beTrue
    }

    "fail on invalid edge lines" in {
      validator.isEdge("Not an edge line") must beFalse
    }

    "recognize the initial vertex" in {
      val declaration = """startingLocation: "foo""""
      val result = validator.isSpecialDeclaration(declaration, "start")

      result must beTrue

      val badDeclaration = """notAStartingLocation: "nothing""""
      val badResult = validator.isSpecialDeclaration(badDeclaration, "start")

      badResult must beFalse
    }

    "recognize the final vertex" in {
      val declaration = """targetLocation: "foo""""
      val result = validator.isSpecialDeclaration(declaration, "end")

      result must beTrue

      val badDeclaration = """notATargetLocation: "nothing""""
      val badResult = validator.isSpecialDeclaration(badDeclaration, "end")

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
