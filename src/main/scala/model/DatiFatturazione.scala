package model

import org.joda.time.DateTime

class DatiFatturazione(
    var dataFatt:DateTime,
    var numFatt:Int,
    var riferim:String,
    var importo:Double,
    var dataScad:String
  ) {
  

}

/*class DatiFatturazione {
  var dataFatt:DateTime=DateTime.now
  var numFatt:Int=0
  var riferim:String=""
  var importo:Double=0.0
  var dataScad:String=""

}*/