package com.garslo.dolldelivery

import org.specs2._

// Convenience object for creating edges
object Edge {
  def apply(start: String, end: String, distance: Int) = {
    Map(
      "startLocation" -> start,
      "endLocation" -> end,
      "distance" -> distance
    )
  }
}

object DijkstraAlgorithmSpec extends mutable.Specification {
  val dataFiles = Seq(
    "src/test/scala/com/garslo/dolldelivery/resources/test_data_1.txt"
  )

  "DijkstraAlgorithm" should {
    val (start, end, edges) = loadMapSyntaxFile(dataFiles(0))
    val dijkstra = new DijkstraAlgorithm(edges)

    "recognize unvisited neighbors" in {
      val result = dijkstra.unvisitedNeighbors("Brian's apartment")
      result must have size(3)

      val result2 = dijkstra.unvisitedNeighbors("Mike's digs")
      result2 must have size(4)
    }

    "find the correct distance between connected vertices" in {
      val result = dijkstra.distanceBetween("Wesley's condo", "Bryce's den")
      result must be equalTo(6)

      val result2 = dijkstra.distanceBetween("Matt's pad", "Nathan's flat")
      result must be equalTo(6)
    }
  }
}
