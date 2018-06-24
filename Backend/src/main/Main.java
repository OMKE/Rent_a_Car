package main;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.net.httpserver.HttpServer;
import model.*;
import server.GorivoHandler;
import server.KorisnikHandler;
import server.RezervacijaHandler;
import server.VozilaHandler;
import utils.MappingUtil;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main  {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Korisnici korisniciWrapper = new Korisnici(new ArrayList<>());
        VoziloWrapper voziloWrapper = new VoziloWrapper(new ArrayList<>());
        ArrayList<Gorivo> listaGoriva = new ArrayList<>();
        Gorivo benzin = new Gorivo("Benzin",2.15);
        Gorivo dizel = new Gorivo("Dizel",2.20);
        Gorivo bioDizel = new Gorivo("Bio Dizel",3.30);
        Gorivo elektricno = new Gorivo("Elektricni pogon", 1.15);

        listaGoriva.add(0, benzin);
        listaGoriva.add(1, dizel);
        listaGoriva.add(2, bioDizel);
        listaGoriva.add(3, elektricno);


        mapper.enable(SerializationFeature.INDENT_OUTPUT);



        Vozilo audiA7 = new PutnickoVozilo("Audi A7","321-321",dizel,250,150,7.2,128000,0,5,5);
        Vozilo lamborghiniAventador = new PutnickoVozilo("Lamborghini Aventador SV","231-942",benzin,1200,500,23.2,3200,0,2,3);
        Vozilo passat7 = new PutnickoVozilo("Passat 7","231-321",dizel,260,100,6.5,43000,0,5,5);
        Vozilo mountainBike = new Bicikl("Capriollo MB3","0",25,10,200,0,1);
        Vozilo man = new TeretnoVozilo("MAN-9321X","00-321-213",dizel,800,200,25.4,123210,0,3,2,5.2,3.5);
        voziloWrapper.add(audiA7);
        voziloWrapper.add(lamborghiniAventador);
        voziloWrapper.add(passat7);
        voziloWrapper.add(mountainBike);
        voziloWrapper.add(man);
//        mapper.writeValue(new File("vozila.json"), voziloWrapper);

//        mapper.writeValue(new File("gorivo.json"), listaGoriva);

        Korisnik omar = new Sluzbenik("omar","omar","2139421","Omar Iriskic");
        Korisnik mirza = new Iznajmljivac("mirza","mirza","291398241","Mirza Redzic","065/213-324","BiH");
        korisniciWrapper.add(omar);
        korisniciWrapper.add(mirza);
//        mapper.writeValue(new File("korisnici.json"), korisniciWrapper);



        ArrayList<Rezervacija> rezervacije = new ArrayList<>();



//        mapper.writeValue(new File("rezervacije.json"), rezervacije);





        List<Rezervacija> sveRezervacije = new ArrayList<>(Arrays.asList(MappingUtil.fromJson(new File("rezervacije.json"), Rezervacija[].class)));
        List<Gorivo> svaGoriva = new ArrayList<>(Arrays.asList(MappingUtil.fromJson(new File("gorivo.json"),Gorivo[].class)));
        Korisnici deserijalizovani = mapper.readValue(new File("korisnici.json"), Korisnici.class);
        VoziloWrapper deserijalizovanaVozila = mapper.readValue(new File("vozila.json"), VoziloWrapper.class);

        HttpServer server = HttpServer.create(new InetSocketAddress(5000),0);
        server.createContext("/vozila", new VozilaHandler(deserijalizovanaVozila));
        server.createContext("/korisnici", new KorisnikHandler(deserijalizovani));
        server.createContext("/rezervacije", new RezervacijaHandler(sveRezervacije));
        server.createContext("/gorivo", new GorivoHandler(listaGoriva));
        server.setExecutor(null);
        server.start();
        System.out.println("Server je podignut na portu 5000");



    }
}
