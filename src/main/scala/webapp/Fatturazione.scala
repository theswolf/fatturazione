package webapp


import spark.Request
import spark.Response
import spark.Route
import spark.Spark._


object Fatturazione {

  def routed(ref:AnyRef)(implicit r:Request):Route = {
    new Route {
      override def handle(request: Request, response: Response): AnyRef = ref
    }
  }

  val route = (ref:AnyRef) => new Route {
    override def handle(request: Request, response: Response): AnyRef = ref
  }

 
  def main(args: Array[String]) {
     
      port(5555)
      get("/",route("hello"))
      
     
  }
  
  


}


class Fatturazione {
  
}


