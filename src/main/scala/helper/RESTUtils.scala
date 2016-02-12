package helper

import spark.Request
import model.BaseORM

trait RESTUtils extends DBSession with Router with Serializer{
  def withGet(dataModel:String):AnyRef = {
    
    implicit val sess = session()
    inSesssion(sess.createCriteria(getMapping {dataModel}).list())
    
  }
  
  def withGet(dataModel:String,id:String):AnyRef = {
    implicit val sess = session()
    inSesssion(sess.get(getMapping {dataModel}, id toLong))
  }
  
  def withPut(dataModel:String,req:Request):AnyRef = {
    implicit val sess = session()
    var data = gson.fromJson(req.body,getMapping {dataModel})
    inSesssion{sess.save {data} 
     data}
  }
  
  def withPost(dataModel:String,req:Request):AnyRef = {
    println("Received -> "+req.body)
    implicit val sess = session()
    var data = gson.fromJson(req.body,getMapping {dataModel})
     inSesssion{
      data.save
    data}
  }
  
  def withDelete(dataModel:String,req:Request):AnyRef = {
    implicit val sess = session()
    var data = gson.fromJson(req.body,getMapping {dataModel})
    inSesssion{sess.delete {data} 
    data}
  
  }
}