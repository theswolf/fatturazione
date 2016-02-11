package helper

import spark.Route
import spark.Request
import spark.Response
import model.BaseORM
import model.Prestazione
import model.DatiFatturazione
import model.Persona
import org.eclipse.jetty.util.Fields
import java.lang.reflect.Field
import java.util.Collection
import scala.reflect.runtime.{universe => ru}

trait Reflect {
   def extractFieldNames[T<:Product:Manifest] = {
    implicitly[Manifest[T]].runtimeClass.getDeclaredFields.map(_.getName)
  }
   
  def extractFields[T<:Product:Manifest]:Array[Field] = {
    implicitly[Manifest[T]].runtimeClass.getDeclaredFields
  }
  
  def getType[T](clazz: Class[T]): ru.Type = {
    val runtimeMirror = ru.runtimeMirror(clazz.getClassLoader)
    runtimeMirror.classSymbol(clazz).toType
}
  
  def extractHTMLFields(a:Array[Field]):Array[String]={
   a.map( field => field.getType match {
     //case classOf[Collection].isInstanceOf(_)  => ""
     //case s:Class[Collection[Any]] => "coll of "+field.getName 
     case c if getType(c).<:<(getType(classOf[Collection[Any]]))  => "coll of "+field.getName 
     case _ => field.getName
   })
  }
   
   def getFields(dataModel:String):Array[String] = {
    dataModel  match {
      case "datiFatturazione" => extractHTMLFields(extractFields[DatiFatturazione])
      case "prestazione" => extractFieldNames[Prestazione]
      case "persona" => extractFieldNames[Persona]
    }
  }
  
}