import akka.actor.Actor;          // Importing actor trait
import akka.actor.ActorSystem;
import akka.actor.Props;
class HelloAkka extends Actor{    // Extending actor trait
  def receive = {                 //  Receiving message
    case msg:String => {
      var i = 0
      i = i+1
      println(self.path.name+"--"+i)
    }
    case _ =>println("Unknown message")      // Default case
  }
}

object Main{
  def main(args:Array[String]){
    var actorSystem = ActorSystem("ActorSystem");                       // Creating ActorSystem
    //println(actorSystem.settings)
    var actor = actorSystem.actorOf(Props[HelloAkka],"HelloAkka")        //Creating actor
    var actor1 = actorSystem.actorOf(Props[HelloAkka],"HelloAkka1")        //Creating actor
    actor1.tell("hello",null)
    actor ! "Akka"                                                // Sending messages by using !
    actor1 ! "new Ref"
    println(actor1.toString())
    actor1 ! "new Ref1"
  }
}