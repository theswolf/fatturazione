package webapp.controller

import spark.Spark.get
import helper.Router
import spark.Request
import spark.Response
import reflect.runtime.{universe => ru}
import scala.reflect.runtime.universe._
import model.DatiFatturazione
import helper.Reflect

object UIController extends Router with Reflect{
  
  


   
    def mapModelToUI = {
      
      get("/ui/form/:model", setRoute { (req:Request,res:Response) => 
       val data = getFields{req.params(":model")}
       res.body(data.mkString("\n"))
       res.body()
      })
      
      
    }
}
   