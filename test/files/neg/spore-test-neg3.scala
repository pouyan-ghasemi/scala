import scala.Spore._

class C

object Test extends App {

  val v1 = 10
  val v2 = "hello"

  val c = new C {
    val v3 = "bad"
    
    val s: Spore[Int, Unit] = spore {
      val c1 = v1
      (x: Int) => {
        println(s"arg: $x, c1: $c1")
        println(s"here, two strings: $v2 and $v3")
      }
    }
  }

  println(c.s(20))
}
