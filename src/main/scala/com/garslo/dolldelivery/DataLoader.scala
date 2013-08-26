package com.garslo.dolldelivery

import scala.io.Source

class Extractor(syntax: DataValidator) extends DataExtractor {
  def extractEdge(line: String) = {
    val EdgeRegex = syntax.edgeRegex.r
    val EdgeRegex(startLocation, endLocation, distance) = line
    Map("startLocation" -> startLocation,
      "endLocation" -> endLocation,
      "distance" -> (distance toInt))
  }

  def getEdges(lines: Seq[String]) = lines filter syntax.isEdge map extractEdge

  // TODO: Refactor these two. They're much too similar
  def getStartVertex(lines: Seq[String]) = {
    val startVertexLine = lines filter syntax.isStartVertexDelcaration
    val StartVertexRegex = syntax.startVertexRegex.r
    // TODO: reconsider the explicit (0)
    val StartVertexRegex(startVertex) = startVertexLine(0)
    startVertex
  }

  def getEndVertex(lines: Seq[String]) = {
    val endVertexLine = lines filter syntax.isEndVertexDelcaration
    val EndVertexRegex = syntax.endVertexRegex.r
    // TODO: reconsider the explicit (0)
    val EndVertexRegex(endVertex) = endVertexLine(0)
    endVertex
  }
}

class FileLoader(extractor: DataExtractor) extends DataLoader {
  def load(filename: String) = {
    val file = Source.fromFile(filename).mkString
    val lines = file.split("\\r*\\n") map { _.trim }
    val edges = extractor.getEdges(lines)
    val start = extractor.getStartVertex(lines)
    val end = extractor.getEndVertex(lines)
    (start, end, edges)
  }
}
