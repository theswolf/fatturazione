package webapp

import model.{Persona, Prestazione, DatiFattura, AppSchema}

import org.joda.time.format.DateTimeFormat
import org.squeryl.adapters.H2Adapter
import org.squeryl.{Session, SessionFactory}
import org.squeryl.PrimitiveTypeMode._
import spark.Spark._
import spark.Route
import spark.Response
import spark.Request


object Fatturazione {

  def routed(ref:AnyRef)(implicit r:Request):Route = {
    new Route {
      override def handle(request: Request, response: Response): AnyRef = ref
    }
  }

  val route = (ref:AnyRef) => new Route {
    override def handle(request: Request, response: Response): AnyRef = ref
  }

  def initDb(): Unit = {
    Class.forName("org.h2.Driver");

    SessionFactory.concreteFactory = Some(()=>
      Session.create(
        java.sql.DriverManager.getConnection("jdbc:h2:./test", "sa", ""),
        new H2Adapter))

    transaction{
      AppSchema.drop
      AppSchema.create

      AppSchema.persona.insert(Persona(
        "Spettabile",
        "ULIXE TECHNOLOGIES MILANO SRL",
        "Corso Italia 7/Bis",
        "Busto Arsizio (Va)",
        "21052",
        "03359310129"

      ))

      val p = from(AppSchema.persona)(p => where(p.denominazione === "ULIXE TECHNOLOGIES MILANO SRL") select(p))
      println(p)


    }
  }

  def main(args: Array[String]) {
      initDb()
      get("/",route("hello"))
  }
  
  


}


class Fatturazione {
  
}


