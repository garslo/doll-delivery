package com.garslo.dolldelivery

import scala.io.Source

class DataLoader {
  // Map edge lines look like
  // Map("startLocation" -> "<alphanumeric>", "endLocation" -> "<alphanumeric", "destination" -> <digits>)
  val edgeRegex = """Map\("startLocation"\s*->\s*"(.*)"\s*,\s*"endLocation"\s*->\s*"(.*)"\s*,\s*"distance"\s*->\s*(\d+)\)"""

  val startVertexRegex = """startingLocation:\s*"(.+)""""
  val endVertexRegex = """targetLocation:\s*"(.+)""""

  def load(filename: String): Option[String] = {
    try {
      val file = Source.fromFile(filename)
    } catch {
      case e: java.io.FileNotFoundException =>
        return None
    }
    Some("")
  }

  def isEdge(line: String) = line matches edgeRegex
  def isStartVertexDelcaration(line: String) = line matches startVertexRegex
  def isEndVertexDelcaration(line: String) = line matches endVertexRegex

  def extractEdge(line: String) = {
    val EdgeRegex = edgeRegex.r
    val EdgeRegex(startLocation, endLocation, distance) = line
    Map("startLocation" -> startLocation,
      "endLocation" -> endLocation,
      "distance" -> (distance toInt))
  }

  def getEdges(lines: Seq[String]) = lines filter isEdge map extractEdge

  def hasStartEndDeclarations(lines: Seq[String]) = {
    val declarations = lines filter ((line) =>
      isStartVertexDelcaration(line) || isEndVertexDelcaration(line))
    declarations.length >= 2
  }

  // TODO: Refactor these two. They're much too similar
  def getStartVertex(lines: Seq[String]) = {
    val startVertexLine = lines filter isStartVertexDelcaration
    val StartVertexRegex = startVertexRegex.r
    // TODO: reconsider the explicit (0)
    val StartVertexRegex(startVertex) = startVertexLine(0)
    startVertex
  }

  def getEndVertex(lines: Seq[String]) = {
    val endVertexLine = lines filter isEndVertexDelcaration
    val EndVertexRegex = endVertexRegex.r
    // TODO: reconsider the explicit (0)
    val EndVertexRegex(endVertex) = endVertexLine(0)
    endVertex
  }
}
