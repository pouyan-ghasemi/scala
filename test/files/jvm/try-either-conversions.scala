import scala.util.{ Try, Success, Failure }
import scala.util.Either.TryConversions._

// tests that implicit conversion from Try to Either behaves as expected
trait TryImplicitConversionTry2Either {

	def testTry2RightMap(): Unit = {
		val t = Success(1)
		val n = t.right.map(x => x * 100)
		assert(n == Right(100))
	}

	def testTry2LeftMap(): Unit = {
		val e = new Exception("foo")
		val t = Failure(e)
		val n = t.left.map(x => x)
		assert(n == Left(e))
	}

	def testTry2FoldSuccess(): Unit = {
		val t = Success(1)
		val n = t.fold(x => assert(false), y => y * 200)
		assert(n == 200)
	}

	def testTry2FoldFailure(): Unit = {
		val e = new Exception("foo")
		val t = Failure(e)
		val n = t.fold(x => x, y => assert(false))
		assert(n == e)
	}

	def testTry2SwapSuccess(): Unit = {
		val t = Success(1)
		val n = t.swap
		assert(n == Left(1))
	}

	def testTry2SwapFailure(): Unit = {
		val e = new Exception("foo")
		val t = Failure(e)
		val n = t.swap
		assert(n == Right(e))
	}

	testTry2RightMap()
	testTry2LeftMap()
	testTry2FoldSuccess()
	testTry2FoldFailure()
	testTry2SwapSuccess()
	testTry2SwapFailure()
}

// tests that implicit conversion from Either to Try behaves as expected
trait TryImplicitConversionEither2Try {

  def testRight2FilterSuccessTrue(): Unit = {
    def expectsTry[U <% Try[Int]](rght: U): Try[Int] = {
      val n = rght.filter(x => x > 0) // this should be converted to a Try
      n
    }
    val r = Right(1)
    val n = expectsTry(r)
    assert(n == Success(1))
  }

  def testRight2FilterSuccessFalse(): Unit = {
    def expectsTry[U <% Try[Int]](rght: U): Try[Int] = {
      val n = rght.filter(x => x < 0) // this should be converted to a Try
      n
    }
    val r = Right(1)
    val n = expectsTry(r)
    n match {
      case Failure(e: NoSuchElementException) => assert(true)
      case _ => assert(false)
    }
  }
  
  def testLeft2FilterFailure(): Unit = {
    def expectsTry[U <% Try[Int]](rght: U): Try[Int] = {
      val n = rght.filter(x => x > 0) // this should be converted to a Try
      n
    }
    val r = Left(new Exception("foo"))
    val n = expectsTry(r)
    n match {
      case Failure(e: Exception) => assert(true)
      case _ => assert(false)
    }
  }
  
  def testRight2GetSuccess(): Unit = {
    def expectsTry[U <% Try[Int]](rght: U): Int = {
      val n = rght.get // this should be converted to a Try
      n
    }
    val r = Right(1)
    val n = expectsTry(r)
    assert(n == 1)
  }
  
  testRight2FilterSuccessTrue()
  testRight2FilterSuccessFalse()
  testLeft2FilterFailure()
  testRight2GetSuccess()
}

object Test
extends App
with TryImplicitConversionTry2Either
with TryImplicitConversionEither2Try {
  System.exit(0)
}
