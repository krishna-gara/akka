import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.actor.Actor.Receive
import com.typesafe.config.ConfigFactory

object RemoteActor extends App {
  val config = ConfigFactory.load.getConfig("RemoteActor")
  val system = ActorSystem("RemoteActor",config)
  val worker =  system.actorOf(Props[Member],"remore-worker")
  println(s"Worker actor path is ${worker.path}")
  worker ! "New World"
}

class Member extends Actor with ActorLogging{
  def receive = {
    case message:String => println("println")
  }
}