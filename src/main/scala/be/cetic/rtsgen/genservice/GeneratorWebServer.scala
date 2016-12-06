package be.cetic.rtsgen.genservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.util.ByteString
import be.cetic.rtsgen.Utils

import scala.io.StdIn
import be.cetic.rtsgen.config.{Configuration}
import spray.json._
import org.joda.time.format.DateTimeFormat
import com.github.nscala_time.time.Imports._


/**
  * /generator => All the values of a call to the generator with a configuration document provided in the POST parameter
  * /generator/date => The values of all data for the greatest date before or equal to the specified one. format: yyyy-MM-dd'T'HH:mm:ss.SSS
  * /generator/d1/d2 => The values for the dates between d1 (excluded) and d2 (included)
  */
object GeneratorWebServer {

   private val dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")

   def main(args: Array[String]) : Unit =
   {

      val parser = new scopt.OptionParser[Config]("ts-gen-service") {
         head("ts-gen-service", "0.0.1")

         opt[Int]('p', "port").action( (x, c) =>
            c.copy(port = x) )
                .text("The port the service must listen to.")

         opt[String]('h', "host")
            .action( (x, c) => c.copy(host = x) )
            .text("The host on which the service is running.")
      }

      if(parser.parse(args, Config()).isEmpty) System.exit(1)
      val config = parser.parse(args, Config()).get

      implicit val system = ActorSystem("tsgen-system")
      implicit val materializer = ActorMaterializer()
      implicit val executionContext = system.dispatcher

      val fullRoute = path("generator")
      {
         post
         {
            decodeRequest
            {
               entity(as[String])
               { document =>

                  val config = Configuration(document.parseJson)

                  val results = Utils.generate(Utils.config2Results(config))

                  val answer = Source(results.map(x => dtf.print(x._1) + ";" + x._2 + ";" + x._3))

                  complete(
                     HttpEntity(
                        ContentTypes.`text/csv(UTF-8)`,
                        answer.map(a => ByteString(s"$a\n"))
                     )
                  )
               }
            }
         }
      }

      val lastRoute = path("generator" / PathMatchers.Segments)
      {
         segments =>
         post
         {
            decodeRequest
            {
               entity(as[String])
               { document =>

                  val config = Configuration(document.parseJson)

                  val answer = segments match {

                     case List(limit) => {
                        // We are looking for the values corresponding to a particular date
                        val reference = LocalDateTime.parse(limit, dtf)
                        val last = scala.collection.mutable.Map[String, (LocalDateTime, String)]()
                        val results = Utils.eval(config, reference)

                        Source(results.map(entry => dtf.print(reference) + ";" + entry._1 + ";" + entry._2.getOrElse("NA").toString))
                     }

                     case List(start, stop) => {

                        val results = Utils.generate(Utils.config2Results(config))

                        val startDate = LocalDateTime.parse(start, dtf)
                        val endDate = LocalDateTime.parse(stop, dtf)

                        val validValues = results.dropWhile(entry => entry._1 <= startDate)
                                                 .takeWhile(entry => entry._1 <= endDate)
                                                 .map(x => dtf.print(x._1) + ";" + x._2 + ";" + x._3.toString)
                        Source(validValues)
                     }

                     case _ => Source(List("invalid segments: " + segments.mkString("/")))
                  }

                  complete(
                     HttpEntity(
                        ContentTypes.`text/csv(UTF-8)`,
                        answer.map(a => ByteString(s"$a\n"))
                     )
                  )
               }
            }
         }
      }

      val route =  lastRoute ~ fullRoute

      val bindingFuture = Http().bindAndHandle(route, config.host, config.port)

      println(s"Server online at http://${config.host}:${config.port}/\nPress RETURN to stop...")
      StdIn.readLine() // let it run until user presses return
      bindingFuture
         .flatMap(_.unbind()) // trigger unbinding from the port
         .onComplete(_ => system.terminate()) // and shutdown when done
   }
}