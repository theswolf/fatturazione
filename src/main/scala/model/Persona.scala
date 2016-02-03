package model

object Persona {
  def apply(
    sigla:String,
    denominazione:String,
    via:String,
    indirizzo:String,
    cap:String,
    pIva:String,
    codFiscale:String) = {
    
    val p = new Persona
      p.sigla=sigla
      p.denominazione=denominazione
      p.via=via
      p.indirizzo=indirizzo
      p.cap=cap
      p.pIva=pIva
      p.codFiscale=codFiscale
      p
    
    }
  
   def apply(
    sigla:String,
    denominazione:String,
    via:String,
    indirizzo:String,
    cap:String,
    pIva:String) = {
    
    val p = new Persona
      p.sigla=sigla
      p.denominazione=denominazione
      p.via=via
      p.indirizzo=indirizzo
      p.cap=cap
      p.pIva=pIva
      p
    
    }
  
}

class Persona() {
   var sigla:String=""
    var denominazione:String=""
    var via:String=""
    var indirizzo:String=""
    var cap:String=""
    var pIva:String=""
    var codFiscale:String=""
}