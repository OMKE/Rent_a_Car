package server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import model.Korisnici;
import model.Korisnik;
import utils.MappingUtil;
import utils.URIUtil;


public class KorisnikHandler implements HttpHandler {
    private Korisnici procitaniKorisnici;
    private ObjectMapper mapper = new ObjectMapper();


    public KorisnikHandler(Korisnici procitaniKorisnici) {
        this.procitaniKorisnici = procitaniKorisnici;
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
        exchange.sendResponseHeaders(200,mapper.writeValueAsString(procitaniKorisnici).getBytes().length);
        os.write(mapper.writeValueAsString(procitaniKorisnici));
    }
    private void postKorisnik(HttpExchange exchange, Writer os, HashMap<String, String> parameters, String bodyString) throws JsonParseException, JsonMappingException, IOException {
        Korisnik reqKorisnik = mapper.readValue(bodyString, Korisnik.class);
        System.out.println(reqKorisnik);
        boolean duplikat = false;
        for (Korisnik korisnik: procitaniKorisnici.getKs()){
            if (korisnik.getKorisnickoIme().equals(reqKorisnik.getKorisnickoIme())){
                duplikat = true;
                break;
            }
        }
        if (!duplikat){
            procitaniKorisnici.add(reqKorisnik);
            MappingUtil.toJson(new File("korisnici.json"),procitaniKorisnici);
            exchange.sendResponseHeaders(200, "Server je primio podatke i spremio ih je u listu korisnici.json".getBytes().length);

            os.write("Server je primio podatke i spremio ih je u listu korisnici.json");
        } else {
            System.out.println("Korisnik s unesenim imenom vec postoji u listi korisnika");
            exchange.sendResponseHeaders(200,"Server: postoji korisnik s primljenim podacima".getBytes().length);
            os.write("Server - duplikat");
        }
        os.close();
    }
}
