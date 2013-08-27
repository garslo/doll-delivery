package com.garslo.dolldelivery

class Validator(syntax: DataSyntax) extends DataValidator(syntax) {
  def isEdge(line: String) = line matches syntax.edgeRegex
  def isStartVertexDelcaration(line: String) = line matches syntax.startVertexRegex
  def isEndVertexDelcaration(line: String) = line matches syntax.endVertexRegex

  def hasStartEndDeclarations(lines: Seq[String]) = {
    val declarations = lines filter ((line) =>
      isStartVertexDelcaration(line) || isEndVertexDelcaration(line))
    declarations.length >= 2
  }
}
