package com.garslo.dolldelivery

trait DataLoader {
  def load(filename: String): Seq[Map[String, Any]]
}

trait DataExtractor {
  def getEdges(lines: Seq[String]): Seq[Map[String, Any]]
  def getStartVertex(lines: Seq[String]): String
  def getEndVertex(lines: Seq[String]): String
}

trait DataValidator {
  val edgeRegex: String
  val startVertexRegex: String
  val endVertexRegex: String

  def isEdge(line: String): Boolean
  def isStartVertexDelcaration(line: String): Boolean
  def isEndVertexDelcaration(line: String): Boolean
  def hasStartEndDeclarations(lines: Seq[String]): Boolean
}
