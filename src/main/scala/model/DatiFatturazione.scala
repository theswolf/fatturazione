package model

import org.joda.time.DateTime

object DatiFatturazione {
  def apply(
    dataFatt:DateTime,
     numFatt:Int,
     riferim:String,
     importo:Double,
     dataScad:String
  ) = {
    val d = new DatiFatturazione
    d.dataFatt=dataFatt
    d.numFatt=numFatt
    d.riferim=riferim
    d.importo=importo
    d.dataScad=dataScad
    d
  }

}

class DatiFatturazione( ) {
  var dataFatt:DateTime = DateTime.now()
    var numFatt:Int=0
    var riferim:String=""
    var importo:Double=0.0
    var dataScad:String=""

}

/*class DatiFatturazione {
  var dataFatt:DateTime=DateTime.now
  var numFatt:Int=0
  var riferim:String=""
  var importo:Double=0.0
  var dataScad:String=""

}*/