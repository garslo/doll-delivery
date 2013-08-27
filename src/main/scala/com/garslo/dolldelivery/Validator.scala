package com.garslo.dolldelivery

class Validator(syntax: DataSyntax) extends DataValidator(syntax) {
  def isEdge(line: String) = line matches syntax.edgeRegex
  def isSpecialDeclaration(line: String, vertexType: String) = line matches syntax(vertexType)

  def hasStartEndDeclarations(lines: Seq[String]) = {
    val declarations = lines filter ((line) =>
      isSpecialDeclaration(line, "start") || isSpecialDeclaration(line, "end"))
    declarations.length >= 2
  }
}
