import akka.NotUsed
import akka.actor.{ActorLogging, ActorSystem}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

object Streams extends App {
  // Code here
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 10)
  source.runForeach(i => println(i))(materializer)
}
