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
    val (start, end, edges) = loadFile(dataFiles(0), MapSyntax)
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

    "give the proper path for test data" in {
      val result = dijkstra.findShortestPath(start, end)
      val desiredResult = "Kruthika's abode => Brian's apartment => Wesley's condo => Bryce's den => Craig's haunt"
      result.mkString(" => ") must be equalTo(desiredResult)
    }
  }
}
