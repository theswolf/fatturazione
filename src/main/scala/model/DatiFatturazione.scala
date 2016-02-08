package model

import java.util.Collection
import scala.collection.JavaConversions.asJavaCollection
import org.joda.time.DateTime
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.MappedSuperclass
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.FetchType


object DatiFatturazione{
  def apply() = new DatiFatturazione(DateTime.now,0,"",0l,"")
}

@Entity
//@Table(name="datiFatturazione")
case class DatiFatturazione(  
    dataFatt:DateTime,
    numFatt:Int,
    riferim:String,
    importo:Double,
    dataScad:String)  extends BaseORM{  
  
   /* @Transient  
    private var innerPrestazioni:Seq[Prestazione] = Seq()*/
  
    //@Column
    //@ElementCollection(targetClass=classOf[Prestazione])
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "datiFattura")
    //var prestazioni:Seq[Prestazione]=Seq()
    var prestazioni:Collection[Prestazione] = asJavaCollection(Seq())
  
    def insertPrestazioni(s:Seq[Prestazione]):Unit = {
      prestazioni = asJavaCollection(s)
    }
}

