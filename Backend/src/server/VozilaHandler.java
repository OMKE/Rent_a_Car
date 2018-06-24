package server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Vozilo;
import model.VoziloWrapper;
import utils.MappingUtil;
import utils.URIUtil;

import java.io.*;
import java.net.URI;
import java.util.HashMap;

public class VozilaHandler implements HttpHandler {
    private VoziloWrapper procitanaVozila;
    private ObjectMapper mapper = new ObjectMapper();

    public VozilaHandler(VoziloWrapper procitanaVozila) {
        this.procitanaVozila = procitanaVozila;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();

        Headers responseHeader = exchange.getResponseHeaders();
        responseHeader.set("Content-Type", "application/json; charset=UTF-8");
        responseHeader.set("Access-Control-Allow-Origin", "*");
        responseHeader.set("Access-Control-Allow-Methods", "GET,POST, PUT, DELETE");


        URI requestURI = exchange.getRequestURI();
        HashMap<String, String> parameters = URIUtil.queryToMap(requestURI);
        Writer os = new OutputStreamWriter(exchange.getResponseBody());
        InputStream in = exchange.getRequestBody();
        byte[] body = in.readAllBytes();
        String bodyString = new String(body, "UTF-8");
        switch (requestMethod){
            case "GET":
                System.out.println("GET zahtjev");
                try {
                    getVozila(exchange, os, parameters);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "POST":
                System.out.println("POST zathjev");
                try {
                    postPutnickoVozilo(exchange, os, parameters, bodyString);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Neodovarajuci metod zahtjeva");
        }
        os.close();


    }
    private void getVozila(HttpExchange exchange, Writer os, HashMap<String, String> parameters) throws JsonProcessingException, IOException {
        exchange.sendResponseHeaders(200,mapper.writeValueAsString(procitanaVozila).getBytes().length);
        os.write(mapper.writeValueAsString(procitanaVozila));
    }
    private void postPutnickoVozilo(HttpExchange exchange, Writer os, HashMap<String, String> parameters, String bodyString) throws JsonParseException, JsonMappingException, IOException {
        Vozilo reqVozilo = mapper.readValue(bodyString, Vozilo.class);
        boolean duplikat = false;
        for (Vozilo vozilo: procitanaVozila.getVozila()){
            if (vozilo.getImeVozila().equals(reqVozilo.getImeVozila())){
                duplikat = true;
                break;
            }
        }
        if (!duplikat){
            procitanaVozila.add(reqVozilo);
            MappingUtil.toJson(new File("vozila.json"),procitanaVozila);
            exchange.sendResponseHeaders(200, "Server je primio podatke i spreio ih je u listu vozila.json".getBytes().length);
            os.write("Server je primio podatke i spreio ih je u listu vozila.json");
        } else {
            System.out.println("Vozilo s unesenim imenom vec postoji u listi vozila");
            exchange.sendResponseHeaders(200,"Server: postoji vozilo s primljenim podacima".getBytes().length);
            os.write("Server: postoji vozilo s primljenim podacima");
        }
        os.close();
    }
}
