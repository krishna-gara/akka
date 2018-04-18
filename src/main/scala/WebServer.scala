import akka.http.scaladsl.server.Directives._
import services.{UserRegistryActor, Users}
import scala.concurrent.Future
import serializers.JsonSupport
import services.UserRegistryActor._
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.StatusCode

object WebServer extends App with JsonSupport {

  //#set up ActorSystem and other dependencies here
  //#main-class
  //#server-bootstrapping
  implicit val system: ActorSystem = ActorSystem("AkkaHttpServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  //#server-bootstrapping
  import akka.pattern.ask
  import akka.util.Timeout
  import scala.concurrent.duration._
  val userRegistryActor = system.actorOf(UserRegistryActor.props, "userRegistryActor")

  implicit lazy val timeout = Timeout(5.seconds)
  // format: OFF
  val route: Route =
  get {
    pathSingleSlash {
      val users: Future[Users] =(userRegistryActor ? GetUsers).mapTo[Users]
      complete(users)
    }
  }
  // format: ON
  val bindingFuture = Http().bindAndHandle(route, "localhost", 8081)
  println(s"Server online at http://localhost:8081/\nPress RETURN to stop...")
}