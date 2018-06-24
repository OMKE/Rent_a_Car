package utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class URIUtil {
    /**
     * @param uri
     * @return mapa sa parametrima
     * @throws UnsupportedEncodingException
     */
    public static HashMap<String, String> queryToMap(URI uri) throws UnsupportedEncodingException {
        HashMap<String, String> result = new HashMap<String, String>();
        String query = uri.getRawQuery();
        if (query != null && !query.isEmpty()) {
            // Lista podeljenih parametara po "&"
            ArrayList<String> splited = new ArrayList<String>(Arrays.asList(query.split("&")));
            // Za svaki element liste potrebno je pretvoriti ga u element mape jer je element tipa
            // kljuc=vrednost pa zbog toga moramo sve elemente rastaviti po "="
            for (String elem : splited) {
                elem = URLDecoder.decode(elem, "UTF-8");
                String[] pair = elem.split("=", 2);
                result.put(pair[0], pair[1]);
            }
        }
        return result;
    }
}