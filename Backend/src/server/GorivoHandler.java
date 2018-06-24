package server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import model.Gorivo;
import utils.MappingUtil;
import utils.URIUtil;

public class GorivoHandler implements HttpHandler {
    private List<Gorivo> procitanaGoriva;
    private ObjectMapper mapper = new ObjectMapper();

    public GorivoHandler(List<Gorivo> procitanaGoriva) {
        this.procitanaGoriva = procitanaGoriva;
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
                    getGorivo(exchange, os, parameters);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "POST":
                System.out.println("POST zathjev");
                try {
                    postGorivo(exchange, os, parameters, bodyString);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Neodovarajuci metod zahtjeva");
        }
        os.close();


    }
    private void getGorivo(HttpExchange exchange, Writer os, HashMap<String, String> parameters) throws JsonProcessingException, IOException {
        exchange.sendResponseHeaders(200,mapper.writeValueAsString(procitanaGoriva).getBytes().length);
        os.write(mapper.writeValueAsString(procitanaGoriva));
    }
    private void postGorivo(HttpExchange exchange, Writer os, HashMap<String, String> parameters, String bodyString) throws JsonParseException, JsonMappingException, IOException {
        Gorivo reqGorivo = mapper.readValue(bodyString, Gorivo.class);
        boolean duplikat = false;
        for (Gorivo gorivo: procitanaGoriva){
            if (gorivo.getTipGoriva().equals(reqGorivo.getTipGoriva())){
                duplikat = true;
                exchange.sendResponseHeaders(406,"Duplikat".getBytes().length);
                break;
            }
        }
        if (!duplikat){
            procitanaGoriva.add(reqGorivo);
            MappingUtil.toJson(new File("gorivo.json"),procitanaGoriva);
            exchange.sendResponseHeaders(200, "Server je primio podatke i spremio ih je u listu gorivo.json".getBytes().length);

            os.write("Server je primio podatke i spremio ih je u listu gorivo.json");
        } else {
            System.out.println("Gorivo s unesenim imenom vec postoji u listi goriva");
            exchange.sendResponseHeaders(200,"Server: postoji gorivo s primljenim podacima".getBytes().length);
            os.write("Server - duplikat");
        }
        os.close();
    }
}
