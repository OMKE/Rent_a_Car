package server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Korisnik;
import model.Rezervacija;
import utils.MappingUtil;
import utils.URIUtil;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

public class RezervacijaHandler implements HttpHandler {
    private List<Rezervacija> procitaneRezervacije;
    private ObjectMapper mapper = new ObjectMapper();


    public RezervacijaHandler(List<Rezervacija> procitaneRezervacije) {
        this.procitaneRezervacije = procitaneRezervacije;
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
                    getKorisnici(exchange, os, parameters);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case "POST":
                System.out.println("POST zathjev");
                try {
                    postKorisnik(exchange, os, parameters, bodyString);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Neodovarajuci metod zahtjeva");
        }
        os.close();


    }
    private void getKorisnici(HttpExchange exchange, Writer os, HashMap<String, String> parameters) throws JsonProcessingException, IOException {
        exchange.sendResponseHeaders(200,mapper.writeValueAsString(procitaneRezervacije).getBytes().length);
        os.write(mapper.writeValueAsString(procitaneRezervacije));
    }
    private void postKorisnik(HttpExchange exchange, Writer os, HashMap<String, String> parameters, String bodyString) throws JsonParseException, JsonMappingException, IOException {
        Rezervacija reqRezervacija = mapper.readValue(bodyString, Rezervacija.class);
        boolean duplikat = false;
        for (Rezervacija rezervacija: procitaneRezervacije){
            if (rezervacija.getPocetakRezervacije().equals(reqRezervacija.getPocetakRezervacije()) && rezervacija.getRezervisanoVozilo().getRegistracioniBroj().equals(reqRezervacija.getRezervisanoVozilo().getRegistracioniBroj())){
                duplikat = true;
                exchange.sendResponseHeaders(406,"Duplikat".getBytes().length);
                break;
            }
        }
        if (!duplikat){
            procitaneRezervacije.add(reqRezervacija);
            MappingUtil.toJson(new File("rezervacije.json"),procitaneRezervacije);
            exchange.sendResponseHeaders(200, "Server je primio podatke i spremio ih je u listu korisnici.json".getBytes().length);

            os.write("Server je primio podatke i spremio ih je u listu korisnici.json");
        } else {
            System.out.println("Vozilo s unesenim datumom vec postoji u listi rezervacija");
            exchange.sendResponseHeaders(200,"Server: postoji vozilo s primljenim podacima".getBytes().length);
            os.write("Server - duplikat");
        }
        os.close();
    }

}
