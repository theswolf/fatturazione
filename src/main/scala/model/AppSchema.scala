package model

import org.squeryl.Schema

/**
  * Created by Christian on 04/02/2016.
  */
object AppSchema extends Schema{

  val datiFatturazione = table[DatiFatturazione]
  val datiFattura = table[DatiFattura]
  val persona = table[Persona]
  val prestazione = table[Prestazione]
}




