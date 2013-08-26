package com.garslo.dolldelivery

trait MapSyntax {
  // Map edge lines look like
  // Map("startLocation" -> "<alphanumeric>", "endLocation" -> "<alphanumeric", "destination" -> <digits>)
  val edgeRegex = """Map\("startLocation"\s*->\s*"(.*)"\s*,\s*"endLocation"\s*->\s*"(.*)"\s*,\s*"distance"\s*->\s*(\d+)\),?"""

  val startVertexRegex = """startingLocation:\s*"(.+)""""
  val endVertexRegex = """targetLocation:\s*"(.+)""""
}

class MapSyntaxValidator extends DataValidator with MapSyntax {
  def isEdge(line: String) = line matches edgeRegex
  def isStartVertexDelcaration(line: String) = line matches startVertexRegex
  def isEndVertexDelcaration(line: String) = line matches endVertexRegex

  def hasStartEndDeclarations(lines: Seq[String]) = {
    val declarations = lines filter ((line) =>
      isStartVertexDelcaration(line) || isEndVertexDelcaration(line))
    declarations.length >= 2
  }
}
