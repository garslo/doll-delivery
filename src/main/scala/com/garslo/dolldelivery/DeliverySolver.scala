package com.garslo.dolldelivery

object DeliverySolver {
  def main(args: Array[String]) = {
    args foreach { arg =>
      try {
        println(shortestPath(arg))
      } catch {
        case badSyntax: BadSyntaxException => println("Could not parse one of your files")
        case e: Exception => println("We had a problem.")
      }
    }
  }

  def shortestPath(file: String): Map[String, String]= {
    // We default to the Map-type syntax, but csv works as well
    var syntax: DataSyntax = MapSyntax
    val extension = file.split("""\.""").last
    if (extension == "csv") {
      syntax = CsvSyntax
    }

    val (distance, shortestPath) = dijkstra(file, syntax)
    Map("distance" -> distance.toString, "path" -> shortestPath.mkString(" => "))
  }

  def dijkstra(file: String, syntax: DataSyntax) = {
    val (start, end, edges) = loadFile(file, syntax)
    val d = new DijkstraAlgorithm(edges)
    d.findShortestPath(start, end)
  }
}
