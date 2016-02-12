package helper

import spark.Request
import model.BaseORM
import org.hibernate.criterion.Property

trait RESTUtils extends DBSession with Router with Serializer{
  def withGet(dataModel:String):AnyRef = {
    
    implicit val sess = session()
    inSesssion(sess.createCriteria(getMapping {dataModel}).list())
    
  }
  
  def withGet(dataModel:String,id:String):AnyRef = {
    implicit val sess = session()
    inSesssion(sess.get(getMapping {dataModel}, id toLong))
  }
  
  def withGet(dataModel:String,id:String,dataParent:String):AnyRef = {
    implicit val sess = session()
    inSesssion{
      sess.createCriteria(getMapping {dataModel})
      .add(Property.forName(dataParent+".id").eq(id toLong))
      .list()
    }
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