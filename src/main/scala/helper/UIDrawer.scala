package helper

import java.util.Collection
import java.lang.reflect.Field
import helper.Decorators._
import scala.reflect.runtime.{universe => ru}

object UIDrawer {
  
  val form = """<div class="row">
				          <div class="col-md-6">
					          <form role="form">
                      _theform_
                      <button type="submit" class="btn btn-default">
							          Submit
						          </button>
					        </form>
				        </div>
              </div>"""
  
  implicit class FieldDrawer(a:Array[Field]) {
    def draw:Array[String]={
        a.map( field => field.getType match {
         case c if getType(c).<:<(getType(classOf[Collection[Any]]))  => "coll of "+field.getName 
         case _ => { 
           field.getName draw ""
         }
       })
    }
    
    
    def renderForm:String = {
      val regex = "_theform_".r
       regex.replaceAllIn(form,  (a draw).mkString("\n"))
    }
    
   }
  
  
    
   def getType[T](clazz: Class[T]): ru.Type = {
      val runtimeMirror = ru.runtimeMirror(clazz.getClassLoader)
    runtimeMirror.classSymbol(clazz).toType
  }
}