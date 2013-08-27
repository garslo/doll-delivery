package com.garslo.dolldelivery

import scala.io.Source

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
