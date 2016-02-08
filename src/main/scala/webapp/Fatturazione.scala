package webapp


import spark.Request
import spark.Response
import spark.Route
import spark.Spark._
import spark.ExceptionHandler
import webapp.controller.RESTController


object Fatturazione {


  
  
 def setRoute( func:(Request,Response) => AnyRef):Route = new Route {
    
    override def handle(request: Request, response: Response): AnyRef = {
      func(request,response)
    }
  }


  def break = {
    halt()
  }
 
  def main(args: Array[String]) {
     
      port(5555)
      get("/", setRoute { (req:Request,res:Response) => "hello"})
      
      get("/test", setRoute { (req:Request,res:Response) => res.body("this is a test") 
        res.body()
        })
        
      RESTController mapModelToURL
        
     
      exception(classOf[Exception], new ExceptionHandler {
         override def handle(e:Exception, req:Request, res:Response):Unit = {
           res.body(e.toString())
           res.status(500)
         }
      })
    
      awaitInitialization
  }
  
  


}



