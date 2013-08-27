package com.garslo.dolldelivery

class DijkstraAlgorithm(graph: Seq[Map[String, Any]]) extends ShortestPathAlgorithm {
  val vertexDistances = collection.mutable.Map.empty[String, Int]
  type MutableSet = collection.mutable.Set[String]
  val vertexNeighbors = collection.mutable.Map.empty[String, MutableSet]

  // Set up the vertexDistances and vertexNeighbors
  for (edge <- graph) {
    val start = edge("startLocation").toString
    val end = edge("endLocation").toString
    vertexDistances(start) = Int.MaxValue
    vertexDistances(end) = Int.MaxValue

    // TODO: Clean this up...duplication
    if (vertexNeighbors contains start) {
      vertexNeighbors(start) += end
    } else {
      vertexNeighbors(start) = collection.mutable.Set(end)
    }

    if (vertexNeighbors contains end) {
      vertexNeighbors(end) += start
    } else {
      vertexNeighbors(end) = collection.mutable.Set(start)
    }
  }

  def findShortestPath(start: String, end: String) = {
    vertexDistances(start) = 0
    var current = start
    unvisitedNeighbors(current) foreach {
      val tentativeDistance = vertexDistances(current) + distanceBetween(current, _)
      if tentativeDistance <
    }
    Seq("")
  }

  def distanceBetween(start: String, end: String) = {
    val edge = graph find ((k, v)) => k == start && v == end
    edge("distance")
  }

  def unvisitedNeighbors(vertex: String) = vertexNeighbors(vertex) filter { vertexDistances(_) == -1 }
  }
}
