package com.garslo.dolldelivery

trait DataLoader {
  // Return type is (startVertex, endVertex, edges)
  def load(filename: String): (String, String, Seq[Map[String, Any]])
}

trait DataSyntax {
  val edgeRegex: String
  val startVertexRegex: String
  val endVertexRegex: String
  def apply(regex: String) = {
    regex match {
      case "edge" => edgeRegex
      case "start" => startVertexRegex
      case "end" => endVertexRegex
      case _ => ""
    }
  }
}

abstract class DataValidator(val syntax: DataSyntax) {
  def isEdge(line: String): Boolean
  def isSpecialDeclaration(line: String, vertexType: String): Boolean
  def hasStartEndDeclarations(lines: Seq[String]): Boolean
}

abstract class DataExtractor(validator: DataValidator) {
  def getEdges(lines: Seq[String]): Seq[Map[String, Any]]
  def getVertex(lines: Seq[String], vertexType: String): String
}
