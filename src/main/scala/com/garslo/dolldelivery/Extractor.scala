package com.garslo.dolldelivery

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
    if (vertexLine.length == 0) {
      throw new BadSyntaxException
    }
    val VertexRegex(vertex) = vertexLine(0)
    vertex
  }
}
