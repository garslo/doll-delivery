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
  "DijkstraAlgorithmSpec" should {
    val edges = Seq(
      Edge("1", "2", 5)
    )
    val dijkstra = new DijkstraAlgorithm(edges)

    "find shortest between two vertices" in {
      val result = dijkstra.findShortestPath("1", "2")
      result.mkString(" => ") must be equalTo("1 => 2")
    }
  }
}
