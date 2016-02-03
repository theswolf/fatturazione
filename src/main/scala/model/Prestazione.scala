package model

import org.joda.time.DateTime

object Prestazione{
  def apply(
      data:DateTime,
      rif:String,             
      prestazione:String,     
      gg:Int,                   
      euroUnit:Int         
  ) = {
    
    val p = new Prestazione
    p.data=data
    p.rif=rif
    p.prestazione=prestazione
    p.gg=gg
    p.euroUnit=euroUnit
    p
  }
}

class Prestazione {
  
   var  data:DateTime=DateTime.now()
    var rif:String=""
    var prestazione:String=""
    var gg:Int=0
    var euroUnit:Int=0
  
  def euro:Double = euroUnit*gg
  
}