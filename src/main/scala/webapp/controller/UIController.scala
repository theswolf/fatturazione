package webapp.controller

import spark.Spark.get
import helper.Router
import spark.Request
import spark.Response
import reflect.runtime.{universe => ru}
import scala.reflect.runtime.universe._
import model.DatiFatturazione
import helper.Reflect
import ui.FormDecorator._
import helper.RESTUtils
import model.BaseORM

object UIController extends RESTUtils with Reflect{
   
    def mapModelToUI = {
       
      get("/ui/form/:model", setRoute { (req:Request,res:Response) => 
       val data = getFields{req.params(":model")}
       implicit val default:Option[AnyRef] = None
       res.body(data renderForm)
       res.body()
      })
      
      get("/ui/form/:model/:id", setRoute { (req:Request,res:Response) => 
       val data = getFields{req.params(":model")}
       val persistedData = withGet( req.params(":model"),req.params(":id"))
       res.body(data renderForm(Option(persistedData)) )
       res.body()
      })
      
      
    }
}
   