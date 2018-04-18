import java.util.Calendar

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, ReceiveTimeout}
import akka.event.Logging

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent._
import ExecutionContext.Implicits.global

class MyActor() extends Actor with ActorLogging{
  def receive = {
    case "test" ⇒ {
      log.info("received test")
      Future{        sender() ! "hello"      }
    }
    case _      ⇒ log.info("received unknown message")
    case ReceiveTimeout ⇒
      // To turn it off
      context.setReceiveTimeout(Duration.Undefined)
      throw new RuntimeException("Receive timed out")
  }
}

class ActorReplyExample extends Actor{
  def receive :Receive = {
    case message:String => println("Message recieved from "+sender.path.name+" massage: "+message)
      val child = context.actorOf(Props[ActorChildReplyExample],"ActorChild")
      child ! "Child"
  }
}

class ActorChildReplyExample extends Actor{
  def receive ={
    case message:String => println("Message recieved from "+sender.path.name+" massage: "+message)
      println("Replying to "+sender().path.name)
      sender()! "I got you message"
  }
}


object AkkaScala extends App {
    val actorSystem = ActorSystem("Hello")
    println(Calendar.getInstance().getTime() + "start actor1 here")
    val actor = actorSystem.actorOf(Props[MyActor],"hello")
    actor ! "test"
    println(Calendar.getInstance().getTime() + "end actor1 here")

    List(2,3)

    println(Calendar.getInstance().getTime()+ "start actor2 here")
    val actorparent = actorSystem.actorOf(Props[ActorReplyExample], "RootActor")
    actorparent ! "Hello"
    println(Calendar.getInstance().getTime()+ "end actor2 here")
}