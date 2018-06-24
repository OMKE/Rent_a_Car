
let prikaziVozilaBtn = document.querySelector('.prikaziVozila').addEventListener('click', prikaziVozila);
const vozilaIzlaz = document.querySelector('.vozila');
const jeSluzbenikProvjera = JSON.parse(localStorage.getItem('korisnik'));
const ulogovanKorisnik = document.querySelector('#ulogovan').innerHTML = `Ulogovani ste kao: <b>${jeSluzbenikProvjera.imeIPrezime}</b> <br><br><button class="button-primary odjava">Odjavi se</button>`;
const odjavaBtn = document.querySelector('.odjava').addEventListener('click', odjava);
let rezervacijeBtn = document.querySelector('.rezervacijeBtn').addEventListener('click', rezervacijeContainer);
const rezervisiVoziloCont = document.querySelector('.rezervacije');
let rezervacijeIzlazOption = document.querySelector('.rezervacije');
let vozila = '';



function rezervacijeContainer(){
    vozilaIzlaz.innerHTML = '';
    
    const xhr = new XMLHttpRequest();
    
    xhr.open('GET', 'http://localhost:5000/vozila', true);
    xhr.onload = function(e){
        if (this.status == 200) {
            vozila = JSON.parse(this.responseText);
            
            let izlazOption = '';
            
            vozila.vozila.forEach(vozilo => {
                izlazOption += `
                <option value="${vozilo.imeVozila}">${vozilo.imeVozila}</option>
                `;
                izlazSelect = `<select id="selectVozilo">${izlazOption}</select>`;
                
            });
            rezervacijeIzlazOption.innerHTML = `<br><p>Izaberite vozilo koje zelite rezervisati:  
            
            <br></p> ${izlazSelect} <button class="button-primary potvrdiVozilo">Potvrdi vozilo</button>
            <p id="uspjesnoIzabrano"></p>
            
            
            <p>Unesite datum pocetka rezervacije:</p>
            <input type="date" id="unosPocetka"><br>
            <p>Unesite datum kraja rezervacije:</p>
            <input type="date" id="unosKraja">
            <br>
            <button class="button-primary rezervisiVozilo">Rezervisi vozilo</button>
            `;
            
            let rezervisiVoziloBtn = document.querySelector('.rezervisiVozilo').addEventListener('click', rezervisiVozilo);
            let selectVozilo = document.getElementById('selectVozilo').addEventListener('change', biranjeVozila);
            
            prikaziRezervisana();
            
        }
    }
    xhr.send();
}

function prikaziRezervisana(){
    const rezervisanaVozilaContainer = document.querySelector('.rezervisanaVozila');
    let logovaniKorisnik = JSON.parse(localStorage.getItem('korisnik'));
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:5000/rezervacije', true);
    xhr.onload = function(e){
        if(this.status == 200){
            const rezervisanaVozila = JSON.parse(this.responseText);
            
            
            let output = '';
            

            for(let i = 0; i < rezervisanaVozila.length; i++){
                if (rezervisanaVozila[i].korisnik.korisnickoIme == logovaniKorisnik.korisnickoIme){
                    output += `
                    
                    <br>
                    <h4>Rezervisano vozilo:</h4>
                    <ul>
                    
                    <li>Ime vozila: <b>${rezervisanaVozila[i].rezervisanoVozilo.imeVozila}</b></li>
                    <li>Registracioni broj: <b>${rezervisanaVozila[i].rezervisanoVozilo.registracioniBroj}</b></li>
                    <li>Tip goriva: <b>${rezervisanaVozila[i].rezervisanoVozilo.gorivo.tipGoriva}</b></li>
                    <li>Cijena iznajmljivanja: <b>${rezervisanaVozila[i].rezervisanoVozilo.cijenaIznajmljivanja}</b></li>
                    <li>Km do servisa: <b>${rezervisanaVozila[i].rezervisanoVozilo.kmDoServisa}</b></li>
                    <li>Broj sjedista: <b>${rezervisanaVozila[i].rezervisanoVozilo.brojSjedista}</b></li>
                    <li>Pocetak rezervacije: <b>${rezervisanaVozila[i].pocetakRezervacije}</b></li>
                    <li>Pocetak rezervacije: <b>${rezervisanaVozila[i].krajRezervacije}</b></li>
                    </ul>
                    `;
                    rezervisanaVozilaContainer.innerHTML = output;
                    
                    
                }
                else {
                    continue;
                    output = `<p> Trenutno nemate rezervisanih vozila</p>`;
                    rezervisanaVozilaContainer.innerHTML = output;
                    
                }
            }
            
            
        }
    }
    
    xhr.send();
    
}
function biranjeVozila(e){
    izabranoVozilo = e.target.value;
    vozila.vozila.forEach(vozilo => {
        if (izabranoVozilo == vozilo.imeVozila) {
            
            let potvrdiVozilo = document.querySelector('.potvrdiVozilo').addEventListener('click', () => {
                let uspjesnoIzabrano = document.getElementById('uspjesnoIzabrano').innerHTML = `Uspjesno ste izabrali vozilo: <b>${vozilo.imeVozila}</b>`;
                localStorage.setItem("vozilo", JSON.stringify(vozilo));
                
            
                
            })
            
            
        }
    });
    
}

function rezervisiVozilo(){
    let izabranoVozilo = JSON.parse(localStorage.getItem('vozilo'));
    let korisnik = JSON.parse(localStorage.getItem('korisnik'));
    let pocetak = document.getElementById('unosPocetka').value;
    let kraj = document.getElementById('unosKraja').value;
    let novaRezervacija = {};
    let xhr = new XMLHttpRequest();
    if (izabranoVozilo.visina == null && izabranoVozilo.gorivo.cijenaGoriva != 0) {
        novaRezervacija = {
            "korisnik" : {
              "type" : "iznajmljivac",
              "korisnickoIme" : korisnik.korisnickoIme,
              "lozinka" : korisnik.lozinka,
              "jmbg" : korisnik.jmbg,
              "imeIPrezime" : korisnik.imeIPrezime,
              "jeSluzbenik" : false,
              "brojTelefona" : korisnik.brojTelefona,
              "drzavljanstvo" : korisnik.drzavljanstvo
            },
            "pocetakRezervacije" : pocetak,
            "krajRezervacije" : kraj,
            "rezervisanoVozilo" : {
              "type" : "putnicko",
              "imeVozila" : izabranoVozilo.imeVozila,
              "registracioniBroj" : izabranoVozilo.registracioniBroj,
              "gorivo" : {
                "type" : "model.Gorivo",
                "tipGoriva" : izabranoVozilo.gorivo.tipGoriva,
                "cijenaGoriva" : izabranoVozilo.gorivo.cijenaGoriva
              },
              "cijenaServisa" : izabranoVozilo.cijenaServisa,
              "cijenaIznajmljivanja" : izabranoVozilo.cijenaServisa,
              "potrosnjaGoriva" : izabranoVozilo.potrosnjaGoriva,
              "predjenoKM" : izabranoVozilo.predjenoKM,
              "brojSjedista" : izabranoVozilo.brojSjedista,
              "brojVrata" : izabranoVozilo.brojVrata,
              "kmDoServisa" : izabranoVozilo.kmDoServisa
            }
          }
    } else if (izabranoVozilo.visina) {
        novaRezervacija = {
            "korisnik" : {
              "type" : "iznajmljivac",
              "korisnickoIme" : korisnik.korisnickoIme,
              "lozinka" : korisnik.lozinka,
              "jmbg" : korisnik.jmbg,
              "imeIPrezime" : korisnik.imeIPrezime,
              "jeSluzbenik" : false,
              "brojTelefona" : korisnik.brojTelefona,
              "drzavljanstvo" : korisnik.drzavljanstvo
            },
            "pocetakRezervacije" : pocetak,
            "krajRezervacije" : kraj,
            "rezervisanoVozilo" : {
              "type" : "teretno",
              "imeVozila" : izabranoVozilo.imeVozila,
              "registracioniBroj" : izabranoVozilo.registracioniBroj,
              "gorivo" : {
                "type" : "model.Gorivo",
                "tipGoriva" : izabranoVozilo.gorivo.tipGoriva,
                "cijenaGoriva" : izabranoVozilo.gorivo.cijenaGoriva
              },
              "cijenaServisa" : izabranoVozilo.cijenaServisa,
              "cijenaIznajmljivanja" : izabranoVozilo.cijenaServisa,
              "potrosnjaGoriva" : izabranoVozilo.potrosnjaGoriva,
              "predjenoKM" : izabranoVozilo.predjenoKM,
              "brojSjedista" : izabranoVozilo.brojSjedista,
              "brojVrata" : izabranoVozilo.brojVrata,
              "kmDoServisa" : izabranoVozilo.kmDoServisa,
              "maksimalnaTezina": izabranoVozilo.maksimalnaTezina,
              "visina":izabranoVozilo.visina
            }
          }
    } else if (izabranoVozilo.gorivo.cijenaGoriva == 0) {
        console.log("ovdje");
        novaRezervacija = {
            "korisnik" : {
              "type" : "iznajmljivac",
              "korisnickoIme" : korisnik.korisnickoIme,
              "lozinka" : korisnik.lozinka,
              "jmbg" : korisnik.jmbg,
              "imeIPrezime" : korisnik.imeIPrezime,
              "jeSluzbenik" : false,
              "brojTelefona" : korisnik.brojTelefona,
              "drzavljanstvo" : korisnik.drzavljanstvo
            },
            "pocetakRezervacije" : pocetak,
            "krajRezervacije" : kraj,
            "rezervisanoVozilo" : {
              "type" : "bicikl",
              "imeVozila" : izabranoVozilo.imeVozila,
              "registracioniBroj" : izabranoVozilo.registracioniBroj,
              "gorivo" : {
                "type" : "model.Gorivo",
                "tipGoriva" : izabranoVozilo.gorivo.tipGoriva,
                "cijenaGoriva" : izabranoVozilo.gorivo.cijenaGoriva
              },
              "cijenaServisa" : izabranoVozilo.cijenaServisa,
              "cijenaIznajmljivanja" : izabranoVozilo.cijenaServisa,
              "potrosnjaGoriva" : izabranoVozilo.potrosnjaGoriva,
              "predjenoKM" : izabranoVozilo.predjenoKM,
              "brojSjedista" : izabranoVozilo.brojSjedista,
              "brojVrata" : izabranoVozilo.brojVrata,
              "kmDoServisa" : izabranoVozilo.kmDoServisa
            }
          }
    }

    
    

    xhr.open('POST', 'http://localhost:5000/rezervacije', true);
    xhr.onload = function(e){
        if (this.status == 200) {
            rezervacijeContainer.innerHTML = `<p>Uspjesno rezervisano vozilo <b>${izabranoVozilo.imeVozila}</b> <br> Pocetak rezervacije: <b>${pocetak}</b> <br> Kraj rezervacije: <b>${kraj}</b>`;
        } else {
            rezervacijeContainer.innerHTML = `<p>Vozilo ${izabranoVozilo.imeVozila} je zauzeto za uneseni datum</p>`;
        }
        
    }
    
    xhr.send(JSON.stringify(novaRezervacija));
    prikaziRezervisana();
    
}


function provjeraLogovanog(){
    if (localStorage.getItem('korisnik') == null) {
        window.location.href = 'prijava.html';
    }
}

function odjava(){
    
    localStorage.removeItem('korisnik');
    window.location.href = 'prijava.html';
}

function prikaziVozila(){
    
    rezervisiVoziloCont.innerHTML = '';
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:5000/vozila', true);
    
    xhr.onload = function(e){
        if (this.status == 200) {
            const vozila = JSON.parse(this.responseText);
            
            let output = '';

            vozila.vozila.forEach(vozilo => {
                if(jeSluzbenikProvjera.jeSluzbenik == false){

                if(vozilo.maksimalnaTezina == undefined && vozila.visina == undefined){
                    output += `
                <br>
                
                <ul>
                <li>Ime vozila: ${vozilo.imeVozila}</li>
                <li>Registracioni broj: ${vozilo.registracioniBroj}</li>
                <li>Gorivo: ${vozilo.gorivo.tipGoriva}</li>
                <li>Cijena servisa: ${vozilo.cijenaServisa}</li>
                <li>Cijena iznajmljivanja: ${vozilo.cijenaIznajmljivanja}</li>
                <li>Potrosnja goriva: ${vozilo.potrosnjaGoriva}</li>
                <li>Predjeno kilometara: ${vozilo.predjenoKM}</li>
                <li>Km do servisa: ${vozilo.kmDoServisa}</li>
                <li>Broj sjedista: ${vozilo.brojSjedista}</li>
                
                </ul>

                `;

                vozilaIzlaz.innerHTML = output;
                
                    
                } else {
                    output += `
                <br>
                
                <ul>
                <li>Ime vozila: ${vozilo.imeVozila}</li>
                <li>Registracioni broj: ${vozilo.registracioniBroj}</li>
                <li>Gorivo: ${vozilo.gorivo.tipGoriva}</li>
                <li>Cijena servisa: ${vozilo.cijenaServisa}</li>
                <li>Cijena iznajmljivanja: ${vozilo.cijenaIznajmljivanja}</li>
                <li>Potrosnja goriva: ${vozilo.potrosnjaGoriva}</li>
                <li>Predjeno kilometara: ${vozilo.predjenoKM}</li>
                <li>Km do servisa: ${vozilo.kmDoServisa}</li>
                <li>Broj sjedista: ${vozilo.brojSjedista}</li>
                <li>Maksimalna tezina: ${vozilo.maksimalnaTezina} t</li>
                <li>Visina: ${vozilo.visina} m</li>
                </ul>

                `;

                vozilaIzlaz.innerHTML = output;
                
                }
                    
                
                
                

            } else if (jeSluzbenikProvjera.jeSluzbenik == true) {
                output += `
                <br>
                
                <ul>
                <li>Ime vozila: ${vozilo.imeVozila}</li>
                <li>Gorivo: ${vozilo.gorivo.tipGoriva}</li>
                <li>Cijena iznajmljivanja: ${vozilo.cijenaIznajmljivanja}</li>
                <li>Potrosnja goriva: ${vozilo.potrosnjaGoriva}</li>
                <li>Predjeno kilometara: ${vozilo.predjenoKM}</li>
                <li>Broj sjedista: ${vozilo.brojSjedista}</li>
                <li>Broj vrata: ${vozilo.brojVrata}</li>
                </ul>
                `;
                vozilaIzlaz.innerHTML = output;
                
            }
        });
            
            
        }
    }
    
    xhr.send();
}



function prikaziSluzbenikOpcije(){
    
    let navigacijaDiv = document.getElementById('navigacija');
    if(jeSluzbenikProvjera.jeSluzbenik == true){
        let prikaziVozilaBtn = `<button class="button-primary prikaziVozila">Prikazi vozila</button>`;
        let dodajVoziloDugme = `<button class="button-primary dodajVozilo">Dodaj vozilo</button>`;
        navigacijaDiv.innerHTML = prikaziVozilaBtn + " " + dodajVoziloDugme;
        prikaziVozilaBtn = document.querySelector('.prikaziVozila').addEventListener('click', prikaziVozila);
        const dodajVozilo = document.querySelector('.dodajVozilo').addEventListener('click', dodajVoziloContainer);
        
    }
}
prikaziSluzbenikOpcije();

function dodajVoziloContainer(e){
    vozilaIzlaz.innerHTML = '';
    let kontejner = document.querySelector('.dodajVoziloKontejnerChild');
    kontejner.innerHTML = `
    <br>
    <h3>Dodaj vozilo</h3>
    <h5>Izaberite tip vozila</h5>
    <select id="tipVozila">
        <option value="putnicko">Putnicko vozilo</option>
        <option value="teretno">Teretno vozilo</option>
        <option value="bicikl">Bicikl</option>
    </select>`;

    let selectTip = document.getElementById('tipVozila').addEventListener('change', biranjeVozila2);
}



function biranjeVozila2(event){
    // Hvatanje liste goriva
    const xhr = new XMLHttpRequest();
    xhr.open("GET",'http://localhost:5000/gorivo', true);
    xhr.onload = function(e){
        if(this.status == 200){
            let goriva = JSON.parse(this.responseText);
            localStorage.setItem('gorivo', JSON.stringify(goriva));
            
        }
        
    }
    xhr.send();
    let listaGoriva = JSON.parse(localStorage.getItem('gorivo'));
    let kontejner = document.querySelector('.dodajVoziloFinal');
    let izabranTip = event.target.value;
    console.log(izabranTip);
    
    if (izabranTip == "putnicko") {
        let izlazGoriva = '';
        listaGoriva.forEach(gorivo => {
            izlazGoriva += `
            <option value="${gorivo.tipGoriva}">${gorivo.tipGoriva}</option>
            `;
            
        });
        let izlazCijenaGoriva = '';
        listaGoriva.forEach(cijena => {
            izlazCijenaGoriva += `
            <option value="${cijena.cijenaGoriva}">${cijena.cijenaGoriva}</option>
            `;
        })

        let izlaz = `
        <div class="voziloUnos">
                <input type="text" id="imeVozila" placeholder="Ime vozila">
                <input type="text" id="registracioniBroj" placeholder="Registracioni broj">
                <select id="tipGoriva">
                    
                </select>
                <select id="cijenaGoriva">
                    
                </select>
                <input type="text" id="cijenaServisa" placeholder="Cijena servisa">
                <input type="text" id="cijenaIznajmljivanja" placeholder="Cijena iznajmljivanja">
                <input type="text" id="potrosnjaGoriva" placeholder="Potrosnja goriva">
                <input type="text" id="predjenoKM" placeholder="Predjeno kilometara">
                <input type="text" id="kmDoServisa" placeholder="Kilometara do servisa">
                <input type="text" id="brojSjedista" placeholder="Broj sjedista">
                <input type="text" id="brojVrata" placeholder="Broj vrata">
                <button class="button-primary dodajVoziloBtn">Dodaj vozilo</button>
            </div>`;
            
        kontejner.innerHTML = izlaz;
        document.getElementById('tipGoriva').innerHTML = izlazGoriva;
        document.getElementById('cijenaGoriva').innerHTML = izlazCijenaGoriva;
        let dodajVoziloBtn = document.querySelector('.dodajVoziloBtn').addEventListener('click', () => {
            let unosImeVozila = document.getElementById('imeVozila').value;
            let unosRegistracioniBroj = document.getElementById('registracioniBroj').value;
            let selectTipGoriva = document.getElementById('tipGoriva').value;
            let selectCijenaGoriva = document.getElementById('cijenaGoriva').value;
            let unosCijenaServisa = document.getElementById('cijenaServisa').value;
            let unosCijenaIznajmljivanja = document.getElementById('cijenaIznajmljivanja').value;
            let unosPotrosnjaGoriva = document.getElementById('potrosnjaGoriva').value;
            let unosPredjenoKm = document.getElementById('predjenoKM').value;
            let unosKmDoServisa = document.getElementById('kmDoServisa').value;
            let unosBrojSjedista = document.getElementById('brojSjedista').value;
            let unosBrojVrata = document.getElementById('brojVrata').value;
            
            

            let novoVozilo = {
                "type":"putnicko",
                "imeVozila" : unosImeVozila,
                "registracioniBroj" : unosRegistracioniBroj,
                "gorivo" : {
                  "type" : "model.Gorivo",
                  "tipGoriva" : selectTipGoriva,
                  "cijenaGoriva" : selectCijenaGoriva
                },
                "cijenaServisa" : unosCijenaServisa,
                "cijenaIznajmljivanja" : unosCijenaIznajmljivanja,
                "potrosnjaGoriva" : unosPotrosnjaGoriva,
                "predjenoKM" : unosPredjenoKm,
                "brojSjedista" : unosBrojSjedista,
                "brojVrata" : unosBrojVrata,
                "kmDoServisa" : unosKmDoServisa
              }

            const xhr = new XMLHttpRequest();

            xhr.open("POST", "http://localhost:5000/vozila", true);

            xhr.onload = function(e){
                if(this.status == 200){
                    kontejner.innerHTML = `<p>Uspjesto ste dodali vozilo ${unosImeVozila}`;
                }
            }
            xhr.send(JSON.stringify(novoVozilo));

        })

        
        
    } 
    else if (izabranTip == "teretno") {
        let izlazGoriva = '';
        listaGoriva.forEach(gorivo => {
            izlazGoriva += `
            <option value="${gorivo.tipGoriva}">${gorivo.tipGoriva}</option>
            `;
            
        });
        let izlazCijenaGoriva = '';
        listaGoriva.forEach(cijena => {
            izlazCijenaGoriva += `
            <option value="${cijena.cijenaGoriva}">${cijena.cijenaGoriva}</option>
            `;
        })

        let izlaz = `
        <div class="voziloUnos">
                <input type="text" id="imeVozila" placeholder="Ime vozila">
                <input type="text" id="registracioniBroj" placeholder="Registracioni broj">
                <select id="tipGoriva">
                    
                </select>
                <select id="cijenaGoriva">
                    
                </select>
                <input type="text" id="cijenaServisa" placeholder="Cijena servisa">
                <input type="text" id="cijenaIznajmljivanja" placeholder="Cijena iznajmljivanja">
                <input type="text" id="potrosnjaGoriva" placeholder="Potrosnja goriva">
                <input type="text" id="predjenoKM" placeholder="Predjeno kilometara">
                <input type="text" id="kmDoServisa" placeholder="Kilometara do servisa">
                <input type="text" id="brojSjedista" placeholder="Broj sjedista">
                <input type="text" id="brojVrata" placeholder="Broj vrata">
                <input type="text" id="maksimalnaTezina" placeholder="Maksimalna tezina">
                <input type="text" id="visina" placeholder="Visina">
                <button class="button-primary dodajVoziloBtn">Dodaj vozilo</button>
            </div>`;
            
        kontejner.innerHTML = izlaz;
        document.getElementById('tipGoriva').innerHTML = izlazGoriva;
        document.getElementById('cijenaGoriva').innerHTML = izlazCijenaGoriva;
        let dodajVoziloBtn = document.querySelector('.dodajVoziloBtn').addEventListener('click', () => {
            let unosImeVozila = document.getElementById('imeVozila').value;
            let unosRegistracioniBroj = document.getElementById('registracioniBroj').value;
            let selectTipGoriva = document.getElementById('tipGoriva').value;
            let selectCijenaGoriva = document.getElementById('cijenaGoriva').value;
            let unosCijenaServisa = document.getElementById('cijenaServisa').value;
            let unosCijenaIznajmljivanja = document.getElementById('cijenaIznajmljivanja').value;
            let unosPotrosnjaGoriva = document.getElementById('potrosnjaGoriva').value;
            let unosPredjenoKm = document.getElementById('predjenoKM').value;
            let unosKmDoServisa = document.getElementById('kmDoServisa').value;
            let unosBrojSjedista = document.getElementById('brojSjedista').value;
            let unosBrojVrata = document.getElementById('brojVrata').value;
            let unosMaksimalneTezine = document.getElementById('maksimalnaTezina').value;
            let unosVisina = document.getElementById('visina').value;
            

            let novoVozilo = {
                "type":"teretno",
                "imeVozila" : unosImeVozila,
                "registracioniBroj" : unosRegistracioniBroj,
                "gorivo" : {
                  "type" : "model.Gorivo",
                  "tipGoriva" : selectTipGoriva,
                  "cijenaGoriva" : selectCijenaGoriva
                },
                "cijenaServisa" : unosCijenaServisa,
                "cijenaIznajmljivanja" : unosCijenaIznajmljivanja,
                "potrosnjaGoriva" : unosPotrosnjaGoriva,
                "predjenoKM" : unosPredjenoKm,
                "brojSjedista" : unosBrojSjedista,
                "brojVrata" : unosBrojVrata,
                "kmDoServisa" : unosKmDoServisa,
                "maksimalnaTezina":unosMaksimalneTezine,
                "visina":unosVisina
              }

            const xhr = new XMLHttpRequest();

            xhr.open("POST", "http://localhost:5000/vozila", true);

            xhr.onload = function(e){
                if(this.status == 200){
                    kontejner.innerHTML = `<p>Uspjesto ste dodali vozilo ${unosImeVozila}`;
                }
            }
            xhr.send(JSON.stringify(novoVozilo));
            

        })

        
        
    }
    else if (izabranTip == "bicikl") {
        let izlazGoriva = '';
        listaGoriva.forEach(gorivo => {
            izlazGoriva += `
            <option value="${gorivo.tipGoriva}">${gorivo.tipGoriva}</option>
            `;
            
        });
        let izlazCijenaGoriva = '';
        listaGoriva.forEach(cijena => {
            izlazCijenaGoriva += `
            <option value="${cijena.cijenaGoriva}">${cijena.cijenaGoriva}</option>
            `;
        })

        let izlaz = `
        <div class="voziloUnos">
                <input type="text" id="imeVozila" placeholder="Ime vozila">
                <input type="text" id="registracioniBroj" placeholder="Registracioni broj">
                <input type="text" id="cijenaServisa" placeholder="Cijena servisa">
                <input type="text" id="cijenaIznajmljivanja" placeholder="Cijena iznajmljivanja">                
                <input type="text" id="predjenoKM" placeholder="Predjeno kilometara">
                <input type="text" id="kmDoServisa" placeholder="Kilometara do servisa">
                
                
                <button class="button-primary dodajVoziloBtn">Dodaj vozilo</button>
            </div>`;
            
        kontejner.innerHTML = izlaz;
        let dodajVoziloBtn = document.querySelector('.dodajVoziloBtn').addEventListener('click', () => {
            let unosImeVozila = document.getElementById('imeVozila').value;
            let unosRegistracioniBroj = document.getElementById('registracioniBroj').value;  
            let unosCijenaServisa = document.getElementById('cijenaServisa').value;
            let unosCijenaIznajmljivanja = document.getElementById('cijenaIznajmljivanja').value;
            let unosPredjenoKm = document.getElementById('predjenoKM').value;
            let unosKmDoServisa = document.getElementById('kmDoServisa').value;
            
        
            

            let novoVozilo = {
                "type":"bicikl",
                "imeVozila" : unosImeVozila,
                "registracioniBroj" : unosRegistracioniBroj,
                "gorivo" : {
                  "type" : "model.Gorivo",
                  "tipGoriva" : "Ne trosi gorivo",
                  "cijenaGoriva" : 0.0
                },
                "cijenaServisa" : unosCijenaServisa,
                "cijenaIznajmljivanja" : unosCijenaIznajmljivanja,
                "potrosnjaGoriva" : 0.0,
                "predjenoKM" : unosPredjenoKm,
                "brojSjedista" : 1,
                "brojVrata" : 0,
                "kmDoServisa" : unosKmDoServisa,
              }

            const xhr = new XMLHttpRequest();

            xhr.open("POST", "http://localhost:5000/vozila", true);

            xhr.onload = function(e){
                if(this.status == 200){
                    kontejner.innerHTML = `<p>Uspjesto ste dodali vozilo ${unosImeVozila}`;
                }
            }
            xhr.send(JSON.stringify(novoVozilo));

        })

        
        
    }
}
