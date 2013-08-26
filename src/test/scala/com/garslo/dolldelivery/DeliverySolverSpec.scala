package com.garslo.dolldelivery

import org.specs2._

object DeliverySolverSpec extends mutable.Specification {
  "DeliverySolver" should {
    "fail on bad input file" in {
      val solver = new DeliverySolver("this-file-does-not-exist.txt")
      val result = solver.solve()
      result.length must_==(0)
    }
  }
}
