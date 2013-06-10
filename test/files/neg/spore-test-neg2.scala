import scala.Spore._

object Test extends App {

  val v1 = 10

  val f: Int => Unit = (x: Int) => println("bye")

  val s: Spore[Int, Unit] = spore {
    val c1 = v1
    f
  }

  println(s(20))
}
