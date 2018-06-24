// Prijava
const loginBtn = document.querySelector('#loginBtn');
loginBtn.addEventListener('click', prijava);
const loginIzlaz = document.querySelector('.izlaz');
function prijava(){
        const xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:5000/korisnici', true);

        xhr.onload = function(e){
            if(this.status == 200){
                const korisnici = JSON.parse(this.responseText);
                let unosKorisnickogImena = document.getElementById('korisnickoIme').value;
                let unosLozinke = document.getElementById('lozinka').value;
                
                
            

                let uspjesanLogin = false;
                for (let i = 0; i < korisnici.ks.length; i++) {
                    
                    if (korisnici.ks[i].korisnickoIme == unosKorisnickogImena && korisnici.ks[i].lozinka == unosLozinke) {
                        loginBtn.style.display = 'none';
                        let izlaz = `
                        <p>Uspjesno ste ulogovani kao ${korisnici.ks[i].imeIPrezime}, slijedi redirekcija na pocetnu stranu</p>
                        `;
                        
                        loginIzlaz.innerHTML = izlaz;
                        uspjesanLogin = true;
                        localStorage.setItem("korisnik", JSON.stringify(korisnici.ks[i]));
                        break;
                    } 
                     else {
                        let izlaz = `
                        <p>Pogresno korisnicko ime ili lozinka, pokusajte ponovo</p>
                        `;
                        
                        loginIzlaz.innerHTML = izlaz;
                        uspjesanLogin = false;
                        
                    }

                    
                }
                if (uspjesanLogin) {
                    setTimeout(() => {
                        window.location.href = 'index.html';
                    }, 3000);
                }
                
                
                
                    
            }

        }
        xhr.send();
}


// Registracija
const registracijaPolje = document.querySelector('.registracija');
document.querySelector("#registracija").addEventListener('click', prikaziRegistraciju);

function prikaziRegistraciju(){
    

    let izlazReg = `
            <br>
            <p>Registracija</p>
            <input type="text" id="regKorisnickoIme" placeholder="Korisnicko ime">
            <input type="text" id="regLozinka" placeholder="Lozinka">
            <input type="text" id="regImePrezime" placeholder="Ime i prezime">
            <input type="text" id="regJmbg" placeholder="JMBG">
            <input type="text" id="regBrojTelefona" placeholder="Broj telefona">
            <input type="text" id="regDrzavljanstvo" placeholder="Drzavljanstvo">
            <br>
            <button id="potvrdiReg" class="button-primary">Potvrdi</button>
            `;
        
    registracijaPolje.innerHTML = izlazReg;
    
    const potvrdiReg = document.querySelector('#potvrdiReg').addEventListener('click', registracija);
}

function registracija(){
    let korisnickoIme = document.getElementById('regKorisnickoIme').value;
    let lozinka = document.getElementById('regLozinka').value;
    let imeIPrezime = document.getElementById('regImePrezime').value;
    let jmbg = document.getElementById('regJmbg').value;
    let brojTelefona = document.getElementById('regBrojTelefona').value;
    let drzavljanstvo = document.getElementById('regDrzavljanstvo').value;
    
    let noviKorisnik =  
                          {
                            "type":"iznajmljivac",
                            "korisnickoIme":korisnickoIme,
                            "lozinka":lozinka,
                            "jmbg":jmbg,
                            "imeIPrezime":imeIPrezime,
                            "jeSluzbenik":false,
                            "brojTelefona":brojTelefona,
                            "drzavljanstvo":drzavljanstvo
                        }
                    
                    
                    if (korisnickoIme == '' && lozinka == '') {
                        alert("Sva polja moraju biti popunjena")
                        window.location.reload();
                    } else{
                        let xhr = new XMLHttpRequest();
                        
                        xhr.open('POST', 'http://localhost:5000/korisnici', true);
                        xhr.onload = function(e){
                            
                            if(this.status == 200){
                                let regUspjesan = `
                                <p>Uspjesno izradjen nalog, korisnicko ime je <b>${korisnickoIme}</b>, lozinka je <b>${lozinka}</b></p>
                            `
                            registracijaPolje.innerHTML = regUspjesan;
                            xhr.setRequestHeader("Content-type", "text/plain");
                            
                            
                            
                            } else{
                                console.log("error");
                            }
                            
                        }
                        xhr.send(JSON.stringify(noviKorisnik));
                        
                    }
                    
    
}






