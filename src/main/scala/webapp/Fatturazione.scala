package webapp


import spark.Request
import spark.Response
import spark.Route
import spark.Spark._
import spark.ExceptionHandler
import webapp.controller.RESTController
import helper.Router
import webapp.controller.UIController


object Fatturazione extends Router{


  def break = {
    halt()
  }
 
  def main(args: Array[String]) {
     
      staticFileLocation("/public")
      port(5555)
      //get("/", setRoute { (req:Request,res:Response) => "hello"})
      
      get("/test", setRoute { (req:Request,res:Response) => res.body("this is a test") 
        res.body()
        })
        
      RESTController mapModelToURL
      
      UIController mapModelToUI
        
     
      exception(classOf[Exception], new ExceptionHandler {
         override def handle(e:Exception, req:Request, res:Response):Unit = {
           res.body(e.toString())
           res.status(500)
         }
      })
    
     
      awaitInitialization
  }
  
  


}



