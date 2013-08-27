package com.garslo.dolldelivery

import org.specs2._

object DeliverySolverSpec extends mutable.Specification {
  "DeliverySolver" should {
    val dataFiles = Seq(
      "src/test/scala/com/garslo/dolldelivery/resources/test_data_1.txt",
      "src/test/scala/com/garslo/dolldelivery/resources/test_data_2.csv",
      "src/test/scala/com/garslo/dolldelivery/resources/test_data_3.csv"
    )

    "solve test data 1" in {
      val (distance, path) = DeliverySolver.dijkstra(dataFiles(0), MapSyntax)
      distance must be equalTo(31)
      path.mkString(" => ") must be equalTo("Kruthika's abode => Brian's apartment => Wesley's condo => Bryce's den => Craig's haunt")
    }

    "solve test data 2" in {
      val (distance, path) = DeliverySolver.dijkstra(dataFiles(1), CsvSyntax)
      distance must be equalTo(3)
      path.mkString(" => ") must be equalTo("A => C => F => E")
    }
  }
}
