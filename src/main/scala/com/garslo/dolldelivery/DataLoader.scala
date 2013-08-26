package com.garslo.dolldelivery

import scala.io.Source

class Extractor(syntax: DataValidator) extends DataExtractor {
  def extractEdge(line: String) = {
    val EdgeRegex = syntax.edgeRegex.r
    val EdgeRegex(startLocation, endLocation, distance) = line
    Map("startLocation" -> startLocation,
      "endLocation" -> endLocation,
      "distance" -> (distance toInt))
  }

  def getEdges(lines: Seq[String]) = lines filter syntax.isEdge map extractEdge

  // TODO: Refactor these two. They're much too similar
  def getStartVertex(lines: Seq[String]) = {
    val startVertexLine = lines filter syntax.isStartVertexDelcaration
    val StartVertexRegex = syntax.startVertexRegex.r
    // TODO: reconsider the explicit (0)
    val StartVertexRegex(startVertex) = startVertexLine(0)
    startVertex
  }

  def getEndVertex(lines: Seq[String]) = {
    val endVertexLine = lines filter syntax.isEndVertexDelcaration
    val EndVertexRegex = syntax.endVertexRegex.r
    // TODO: reconsider the explicit (0)
    val EndVertexRegex(endVertex) = endVertexLine(0)
    endVertex
  }
}

//class LoaderForMapSyntax extends DataLoader {
//  def load(filename: String) = {
    // TODO: Is there a better way to check existence?
//    try {
//      val file = Source.fromFile(filename).mkString
//      val lines = file.split("\\r*\\n")
//    } catch {
//      case e: java.io.FileNotFoundException =>
//        return None
//    }
//    Some("")
//  }
//}
