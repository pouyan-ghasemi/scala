import scala.Spore._

object Test extends App {

  val v1 = 10

  val s: Spore[Int, Unit] = spore {
    val c1 = v1
    println("hi")
    (x: Int) => println(s"arg: $x, c1: $c1")
  }

  println(s(20))
}
