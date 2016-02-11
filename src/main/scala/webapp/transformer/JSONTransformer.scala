package webapp.transformer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import spark.ResponseTransformer
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import helper.Serializer

class JSONTransformer extends ResponseTransformer with Serializer {

  override def render(model: AnyRef): String = gson.toJson(model)
 
}