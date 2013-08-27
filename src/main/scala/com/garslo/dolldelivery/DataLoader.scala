package com.garslo.dolldelivery

import scala.io.Source

class Extractor(validator: DataValidator) extends DataExtractor(validator) {
  def extractEdge(line: String) = {
    val EdgeRegex = validator.syntax("edge").r
    val EdgeRegex(startLocation, endLocation, distance) = line
    Map("startLocation" -> startLocation,
      "endLocation" -> endLocation,
      "distance" -> (distance toInt))
  }

  def getEdges(lines: Seq[String]) = lines filter validator.isEdge map extractEdge

  def getVertex(lines: Seq[String], vertexType: String) = {
    val vertexLine = lines filter {line => validator.isSpecialDeclaration(line, vertexType)}
    val VertexRegex = validator.syntax(vertexType).r
    val VertexRegex(vertex) = vertexLine(0)
    vertex
  }
}

class FileLoader(extractor: DataExtractor) extends DataLoader {
  def load(filename: String) = {
    val file = Source.fromFile(filename).mkString
    val lines = file.split("\\r*\\n") map { _.trim }

    val edges = extractor.getEdges(lines)
    val start = extractor.getVertex(lines, "start")
    val end = extractor.getVertex(lines, "end")
    (start, end, edges)
  }
}
