/**
 *
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class MappingUtil {

    private static ObjectMapper om = new ObjectMapper();
    

    /**
     * Pravljenje parametara URL-a na osnovu mape sa kljucevima i vrednostima
     * @param map - mapa koja sadrzi sve parametre koji trebaju da budu deo URL-a
     * @return String koji predstavlja parametre URL-a
     */
    public static String buildParams(Map<String, String> map) {
        Iterator<Entry<String, String>> it = map.entrySet().iterator();
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        while (it.hasNext()) {
            Entry<String, String> pair = (Entry<String, String>) it.next();
            if (first) {
                builder.append(pair.getKey() + "=" + pair.getValue());
                first = false;
            } else {
                builder.append("&" + pair.getKey() + "=" + pair.getValue());
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return builder.toString();
    }

    /**
     * Creates json as String from input Map<String, Object>
     * @param map key type String, value type Object (but it should be only string or number for now)
     * if param isn't String or number it won't be included in json
     * @return String witch represents json object
     */
    public static String buildJson(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Iterator<Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, Object> entry = iterator.next();
            if (entry.getValue().getClass() == String.class) {
                sb.append("\"");
                sb.append(entry.getKey());
                sb.append("\":");
                sb.append("\"");
                sb.append(entry.getValue());
                sb.append("\"");
            } else if ((entry.getValue().getClass() == Integer.class) ||
                    (entry.getValue().getClass() == Double.class)) {
                sb.append("\"");
                sb.append(entry.getKey());
                sb.append("\":");
                sb.append(entry.getValue());
            } else {
                System.out.println(String.format("Object stored in map with key %s is not String or number, and it won't be added to json!", entry.getKey()));
            }
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Pretvara mapu u json String
     * @param map
     * @return
     * @throws JsonProcessingException
     */
    public static String toJson(Map<String, Object> map) throws JsonProcessingException {
        return om.writeValueAsString(map);
    }

    /**
     * Pretvara bilo koji objekat u json string
     * @param object - objekat koji zelimo da pretvorimo u json stirng
     * @return
     * @throws JsonProcessingException
     */
    public static String toJson(Object object) throws JsonProcessingException {
        return om.writeValueAsString(object);
    }

    public static void toJson(File file, Object object){
        try {
        	om.enable(SerializationFeature.INDENT_OUTPUT);
            om.writeValue(file, object);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Pretvara json String u objekat klase clazz.
     * @param <T>
     * @param <T>
     * @param json
     * @param clazz
     * @return Objekat tipa clazz
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws JsonParseException,
            JsonMappingException, IOException {
        return om.readValue(json, clazz);
    }

    /**
     * Ucitava json iz datoteke i pretvara ga u objekat klase clazz
     * @param <T>
     * @param file
     * @param clazz
     * @return Objekat tipa clazz
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T fromJson(File file, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return  om.readValue(file, clazz);
    }

    /**
     * Ucitava json iz datoteke i pretvara ga u objekat klase tr
     * @param file
     * @param tr
     * @return Objekat tipa tr
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Object fromJson(File file, TypeReference<?> tr) throws JsonParseException, JsonMappingException, IOException {
        om.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return om.readValue(file, tr);
    }
}
