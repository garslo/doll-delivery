package com.garslo.dolldelivery

class Edge {
  var visited: Boolean
  var distance: Int
  val name: String
}

class DijkstraAlgorithm(graph: Seq[Map[String, Any]]) extends ShortestPathAlgorithm {
  val distances = collection.mutable.Map.empty[String, Int]
  type MutableSet = collection.mutable.Set[String]
  val neighbors = collection.mutable.Map.empty[String, MutableSet]
  // TODO: Keeps track of the path stuff?? see "previous" on wikipedia
  val previous = collection.mutable.Map.empty[String, String]

  // Set up the distances and neighbors
  for (edge <- graph) {
    val start = edge("startLocation").toString
    val end = edge("endLocation").toString
    distances(start) = Int.MaxValue
    distances(end) = Int.MaxValue

    previous(edge("startLocation").toString) = ""

    // TODO: Clean this up...duplication
    if (neighbors contains start) {
      neighbors(start) += end
    } else {
      neighbors(start) = collection.mutable.Set(end)
    }

    if (neighbors contains end) {
      neighbors(end) += start
    } else {
      neighbors(end) = collection.mutable.Set(start)
    }
  }

  def setTentativeDistances(current: String) = {
    println("Called setTentativeDistances with " + current.toString)
    unvisitedNeighbors(current) foreach { neighbor =>
      println("  foreach, " + neighbor.toString)
      val tentativeDistance = distances(current) + distanceBetween(current, neighbor)

      if (tentativeDistance < distances(neighbor)) {
          distances(neighbor) = tentativeDistance
          previous(neighbor) = current
      }
    }
  }

  def findShortestPath(start: String, end: String) = {
    distances(start) = 0
    var current = start
    do {
      // REMOVEME
      println("in do loop " + current.toString)
      setTentativeDistances(current)
      // Find smallest tentative distance
      current = closestVertex(current)
    } while (!visited(end))
    // Work backward & find the shortest path
    val shortestPath = collection.mutable.MutableList.empty[String]
    current = end
    do {
      shortestPath += current
      current = previous(current)
    } while (previous(current) != "")

    shortestPath
  }

  def closestVertex(vertex: String) = {
    var theClosestVertex = vertex
    var distance = Int.MaxValue

    unvisitedNeighbors(vertex) foreach {n =>
      if (distances(n) < distance) {
        distance = distances(n)
        theClosestVertex = n
      }
    }
    theClosestVertex
  }

  def distanceBetween(start: String, end: String) = {
    // This is ugly, but I can't find a better way.
    val edge = graph.find(m => {
      m("startLocation") == start && m("endLocation") == end
    })
    edge match {
      case Some(thing) => thing("distance").toString().toInt
      case None => Int.MaxValue
    }
  }

  def unvisitedNeighbors(vertex: String) = {
    neighbors(vertex) filter { distances(_) == Int.MaxValue }
  }

  def visited(vertex: String) = distances(vertex) != Int.MaxValue
}
