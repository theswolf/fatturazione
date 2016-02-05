package model

import org.joda.time.DateTime
import javax.persistence.Entity

@Entity
case class DatiFatturazione(  
    dataFatt:DateTime,
    numFatt:Int,
    riferim:String,
    importo:Double,
    dataScad:String)  extends BaseORM{  
  
    var prestazioni:Seq[Prestazione]=Seq()
  
 
}

