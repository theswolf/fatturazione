package webapp.transformer

import LocalDateSerializer._
import scala.collection.JavaConversions._
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonSerializationContext
import org.joda.time.format.ISODateTimeFormat
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonDeserializer
import org.joda.time.LocalDate
import com.google.gson.JsonSerializer
import java.lang.reflect.Type


object LocalDateSerializer {

  private val DATE_FORMAT = ISODateTimeFormat.date()
}

class LocalDateSerializer extends JsonDeserializer[LocalDate] with JsonSerializer[LocalDate] {

  override def deserialize(je: JsonElement, `type`: Type, jdc: JsonDeserializationContext): LocalDate = {
    val dateAsString = je.getAsString
    if (dateAsString.length == 0) {
      null
    } else {
      DATE_FORMAT.parseLocalDate(dateAsString)
    }
  }

  override def serialize(src: LocalDate, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    var retVal: String = null
    retVal = if (src == null) "" else DATE_FORMAT.print(src)
    new JsonPrimitive(retVal)
  }
}