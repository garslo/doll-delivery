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
}
