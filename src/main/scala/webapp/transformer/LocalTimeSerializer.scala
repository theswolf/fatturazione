package webapp.transformer

import LocalTimeSerializer._
import scala.collection.JavaConversions._
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonSerializationContext
import org.joda.time.format.ISODateTimeFormat
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import org.joda.time.LocalTime
import java.lang.reflect.Type

object LocalTimeSerializer {

  private val TIME_FORMAT = ISODateTimeFormat.timeNoMillis()
}

class LocalTimeSerializer extends JsonDeserializer[LocalTime] with JsonSerializer[LocalTime] {

  override def deserialize(je: JsonElement, `type`: Type, jdc: JsonDeserializationContext): LocalTime = {
    val dateAsString = je.getAsString
    if (dateAsString.length == 0) {
      null
    } else {
      TIME_FORMAT.parseLocalTime(dateAsString)
    }
  }

  override def serialize(src: LocalTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    var retVal: String = null
    retVal = if (src == null) "" else TIME_FORMAT.print(src)
    new JsonPrimitive(retVal)
  }
}
