package helper

import spark.Route
import spark.Request
import spark.Response
import model.BaseORM
import model.Prestazione
import model.DatiFatturazione
import model.Persona

trait Router {
   def setRoute( func:(Request,Response) => AnyRef):Route = new Route {
    
    override def handle(request: Request, response: Response): AnyRef = {
      func(request,response)
    }
  }
   
   def getMapping(dataModel:String):Class[_ <:BaseORM] = {
    dataModel  match {
      case "datiFatturazione" => classOf[DatiFatturazione]
      case "prestazione" => classOf[Prestazione]
      case "persona" => classOf[Persona]
      case _ => classOf[BaseORM]
    }
  }
  
}