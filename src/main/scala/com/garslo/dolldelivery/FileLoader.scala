package com.garslo.dolldelivery

import scala.io.Source

class FileLoader(extractor: DataExtractor) extends DataLoader {
  def load(filename: String) = {
    val file = Source.fromFile(filename)
    val lines = file.mkString.split("\\r*\\n") map { _.trim }
    file.close()

    val edges = extractor.getEdges(lines)
    val start = extractor.getVertex(lines, "start")
    val end = extractor.getVertex(lines, "end")
    (start, end, edges)
  }
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
