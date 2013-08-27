package com.garslo.dolldelivery

class DijkstraAlgorithm(graph: Seq[Map[String,Any]]) extends ShortestPathAlgorithm {
  // Some imperative code here...
  type MutableSet = collection.mutable.Set[String]
  // For convenience; stores the neighbors for a given vertex
  val neighbors = collection.mutable.Map.empty[String, MutableSet]

  /// Needed for Dijkstra's algorithm
  // Stores the distances from the start vertex to a given vertex
  val distances = collection.mutable.Map.empty[String, Int]

  // Keeps track of visited/unvisited vertices
  val unvisitedVertices = collection.mutable.Set.empty[String]
  val visitedVertices = collection.mutable.Set.empty[String]

  // Stores each vertex's "previous" vertex. Used to build the final
  // path
  val previous = collection.mutable.Map.empty[String, String]

  def findShortestPath(start: String, end: String) = {
    initialize()
    distances(start) = 0
    markAsVisited(start)
    var current = start
    do {
      setTentativeDistances(current)
      markAsVisited(current)
      current = closestVertex(current)
    } while (!visited(end))

    (distances(end), start +: gatherShortestPathTo(end))
  }

  def initialize() = {
    // Iterate over the edges and extract vertex and neighbor
    // information. Also initialize the distances and
    // unvisitedVertices structures.
    graph foreach { edge =>
      val start = edge("startLocation").toString
      val end = edge("endLocation").toString

      // Int.MaxValue is close enough to infinity :)
      distances(start) = Int.MaxValue
      distances(end) = Int.MaxValue

      unvisitedVertices += start
      unvisitedVertices += end

      previous(start) = ""
      addAsNeighbors(start, end)
    }
  }

  def addAsNeighbors(start: String, end: String) = {
    // Double duty here: if the vertex isn't new, we tack onto the
    // set, else we create a new set.
    if (neighbors contains start) {
      neighbors(start) += end
    } else {
      neighbors(start) = collection.mutable.Set(end)
    }

    // Ditto
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

  def gatherShortestPathTo(endVertex: String) = {
    val shortestPath = collection.mutable.MutableList.empty[String]
    var current = endVertex

    // Traverses the "previous" map, starting at the endVertex and
    // hopping to the start
    do {
      shortestPath += current
      current = previous(current)
    } while (previous(current) != "")
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
      (m("startLocation") == start && m("endLocation") == end) ||
      (m("endLocation") == start && m("startLocation") == end)
    })
    edge match {
      // Not ideal: the map value is truly an integer, but typed as
      // Any to fit into the Map
      case Some(theEdge) => theEdge("distance").toString().toInt
      case None => Int.MaxValue
    }
  }

  def unvisitedNeighbors(vertex: String) = {
    neighbors(vertex) filter { !visited(_) }
  }

  def visited(vertex: String) = visitedVertices contains vertex
}
