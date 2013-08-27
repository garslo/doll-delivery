package com.garslo.dolldelivery

trait DataLoader {
  // Return type is (startVertex, endVertex, edges)
  def load(filename: String): (String, String, Seq[Map[String, Any]])
}

trait DataSyntax {
  val edgeRegex: String
  val startVertexRegex: String
  val endVertexRegex: String
}

abstract class DataValidator(val syntax: DataSyntax) {
  def isEdge(line: String): Boolean
  def isStartVertexDelcaration(line: String): Boolean
  def isEndVertexDelcaration(line: String): Boolean
  def hasStartEndDeclarations(lines: Seq[String]): Boolean
}

abstract class DataExtractor(validator: DataValidator) {
  def getEdges(lines: Seq[String]): Seq[Map[String, Any]]
  def getStartVertex(lines: Seq[String]): String
  def getEndVertex(lines: Seq[String]): String
}
