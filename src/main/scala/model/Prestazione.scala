package model

import org.joda.time.DateTime

class Prestazione (
    
    var data:DateTime,
    var rif:String,
    var prestazione:String,
    var gg:Int	,
    var euroUnit:Int

  
  ){
  
  def euro:Double = euroUnit*gg
  
}