package webapp.transformer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import spark.ResponseTransformer
import org.joda.time.LocalDate
import org.joda.time.LocalTime

class JSONTransformer extends ResponseTransformer {

  private val gson: Gson = (new GsonBuilder())
       .registerTypeAdapter(classOf[LocalDate], new LocalDateSerializer())
       .registerTypeAdapter(classOf[LocalTime], new LocalTimeSerializer())
       .create()
    

  override def render(model: AnyRef): String = gson.toJson(model)
 
}