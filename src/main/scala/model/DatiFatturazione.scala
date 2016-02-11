package model

import java.util.Collection
import java.util.Date
import scala.collection.JavaConversions.asJavaCollection
import scala.collection.JavaConversions.collectionAsScalaIterable
import org.joda.time.DateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Temporal
import javax.persistence.FetchType
import javax.persistence.TemporalType
import javax.persistence.CascadeType
import org.hibernate.Session




object DatiFatturazione{
  def apply() = new DatiFatturazione(DateTime.now.toDate(),0,"",0l,"")
}

@Entity
//@Table(name="datiFatturazione")
case class DatiFatturazione(  

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    val dataFatt:Date,
    numFatt:Int,
    riferim:String,
    importo:Double,
    dataScad:String)  extends BaseORM{  
  
  
    private def this() = this(DateTime.now.toDate(),0,"",0l,"")
  
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "datiFattura")
    //var prestazioni:Seq[Prestazione]=Seq()
    var prestazioni:Collection[Prestazione] = asJavaCollection(Seq())
  
    def insertPrestazioni(s:Seq[Prestazione]):Unit = {
      prestazioni = asJavaCollection(s)
    }
    
    
    def save(implicit session:Session):DatiFatturazione = {
        session.save(this)
        for(prestazione <- collectionAsScalaIterable(prestazioni)) {
          prestazione.datiFattura = DatiFatturazione()
          prestazione.datiFattura.id = this.id
          session.save(prestazione)
        }
        this
      
    }
    
    
    
    
}

