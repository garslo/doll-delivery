package com.garslo.dolldelivery

class DijkstraAlgorithm(graph: Seq[Map[String,Any]]) extends ShortestPathAlgorithm {
  val distances = collection.mutable.Map.empty[String, Int]
  type MutableSet = collection.mutable.Set[String]
  val neighbors = collection.mutable.Map.empty[String, MutableSet]
  val unvisitedVertices = collection.mutable.Set.empty[String]
  val visitedVertices = collection.mutable.Set.empty[String]
  val previous = collection.mutable.Map.empty[String, String]

  // Set up the distances and neighbors
  // TODO: Change to foreach
  for (edge <- graph) {
    val start = edge("startLocation").toString
    val end = edge("endLocation").toString
    distances(start) = Int.MaxValue
    distances(end) = Int.MaxValue
    // Clean this up?
    unvisitedVertices += start
    unvisitedVertices += end

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
    unvisitedNeighbors(current) foreach { neighbor =>
      val tentativeDistance = distances(current) + distanceBetween(current, neighbor)

      if (tentativeDistance < distances(neighbor)) {
        distances(neighbor) = tentativeDistance
        previous(neighbor) = current
      }
    }
  }

  def markAsVisited(vertex: String) = {
    unvisitedVertices -= vertex
    visitedVertices += vertex
  }

  def findShortestPath(start: String, end: String) = {
    distances(start) = 0
    markAsVisited(start)
    var current = start
    do {
      setTentativeDistances(current)
      markAsVisited(current)
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

    shortestPath += start
    shortestPath.reverse
  }

  def closestVertex(vertex: String) = {
    var theClosestVertex = vertex
    var distance = Int.MaxValue

    unvisitedVertices foreach {n =>
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
      // TODO: Ugly
      (m("startLocation") == start && m("endLocation") == end) || (m("endLocation") == start && m("startLocation") == end)
    })
    edge match {
      // TODO: change variable name
      case Some(thing) => thing("distance").toString().toInt
      case None => Int.MaxValue
    }
  }

  def unvisitedNeighbors(vertex: String) = {
    neighbors(vertex) filter { !visited(_) }
  }

  def visited(vertex: String) = visitedVertices contains vertex
}
