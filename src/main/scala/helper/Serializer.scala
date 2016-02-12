package helper

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import model.Prestazione
import com.fatboyindustrial.gsonjodatime.Converters

trait Serializer {
  def gson:Gson = Converters.registerDateTime(new GsonBuilder())
  .setExclusionStrategies(new ExclusionStrategy() {
    def shouldSkipField(f:FieldAttributes):Boolean = {
      f.getName.equalsIgnoreCase("session") ||
      f.getDeclaringClass == classOf[Prestazione] && f.getName.equalsIgnoreCase("datiFatturazione")
    }
    
    def shouldSkipClass(c:Class[_]):Boolean = {
      return false
    }
  })
  .create();
}