package com.garslo.dolldelivery

object MapSyntax extends DataSyntax {
  // Map edge lines look like
  // Map("startLocation" -> "<alphanumeric>", "endLocation" -> "<alphanumeric", "destination" -> <digits>)
  val edgeRegex = """Map\("startLocation"\s*->\s*"(.*)"\s*,\s*"endLocation"\s*->\s*"(.*)"\s*,\s*"distance"\s*->\s*(\d+)\),?"""

  val startVertexRegex = """startingLocation:\s*"(.+)""""
  val endVertexRegex = """targetLocation:\s*"(.+)""""
}

object CsvSyntax extends DataSyntax {
  // We'll take csv in the form of v1, v2, distance
  val edgeRegex = """^s*(.*)\s*,\s*(.*)\s*,\s*(\d+)"""
  val startVertexRegex = """startingLocation:\s*"(.+)""""
  val endVertexRegex = """targetLocation:\s*"(.+)""""
}

// Convenience object for loading files
object loadFile {
  def apply(filename: String, syntax: DataSyntax) = {
    val validator = new Validator(syntax)
    val extractor = new Extractor(validator)
    val loader = new FileLoader(extractor)
    loader.load(filename)
  }
}
