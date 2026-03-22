# Domaća zadaća 1 - Android akademija 2026
## 1. Kotlin i OOP

---

**Zadatak 1** – Zamislite da obrađujete korisnički unos iz registracijskog obrasca gdje su neka polja obavezna, a druga opcionalna.

1. Deklarirajte vrijednosti za ime i prezime.
2. Deklarirajte nulabilnu varijablu za *email* i postavite je na `null`.
3. Deklarirajte nulabilnu varijablu za *age* i dodijelite joj vrijednost.
4. Ispišite duljinu email adrese (pripazite na pravilno rukovanje null vrijednostima).
5. Ažurirajte varijablu email i ponovno ispišite da biste vidjeli razliku.

---

**Zadatak 2** – Napišite logiku za automat pića koji toči piće na temelju šifre proizvoda i provjerava je li korisnik ubacio dovoljno novca.

1. Izradite varijablu za kod proizvoda i dodijelite joj broj između 1 i 4.
2. Izradite varijable za cijenu proizvoda i primljeni novac.
3. Pomoću naredbe `when` dodijelite niz znakova novoj varijabli koja će imati vrijednost imena pića na temelju koda proizvoda (npr. 1 = "Voda", 2 = "Cola", 3 = "Sok", 4 = "Kava").
4. Pomoću `if-else` bloka provjerite je li primljena količina novca veća ili jednaka cijeni željenog pića. Ako jest, ispišite poruku u kojoj navodite koje se piće toči i koliki je ostatak. Ako nije, ispišite poruku o tome koliko nedostaje od predviđenog iznosa kako bi automat natočio piće.

---

**Zadatak 3** – Imate podatke o broju koraka za tjedan dana i trebate izračunati tjedne metrike korisnika.

1. Kreirajte listu podataka koja sadrži sedam brojeva. Svaki broj predstavlja dnevni broj koraka (npr. `listOf(4500, 12000, 8000, 15000, 3000, 11000, 9500)`).
2. Koristite `for` petlju za iteraciju kroz listu i izračunajte ukupni zbroj koraka za vaš tjedan. Ispišite ukupan iznos.
3. Koristite `while` petlju s varijablom za pronalaženje i ispis prvog dana (index + 1) kada je korisnik premašio 10 000 koraka. Nakon što ga pronađete, upotrijebite ključnu riječ `break` za izlaz iz petlje.

---

**Zadatak 4** – Kotlin ima nevjerojatno bogatu standardnu biblioteku za stringove. Napišite funkcije za obradu i analizu korisničkog imena koje je korisnik unio s tipkovnice (`readln()`).

**Ponašanje programa** 

Korisnik unosi korisničko ime poput: `" John_doe123 "`

Vaša funkcija za pripremu korisničkog imena treba pretvoriti primljeno korisnićko ime u `"john_doe123"`, a druga funkcija treba provjeriti valjanost prethodno pripremljenog (obrađenog) korisničkog imena te vratiti `Boolean` vrijednost ovisno o rezultatu.

U primjeru bi prva funkcija vratila obrađenu vrijednost `john_doe123`, a druga bi vratila `true` jer su zadovoljeni uvjeti valjanosti koji su definirani u nastavku zadatka.

**Vaš program treba (funkcije):**

1. Ukloniti razmake na početku i kraju te pretvoriti korisničko ime u mala slova.
2. Provjeriti je li korisničko ime valjano.

**Korisničko ime je valjano ako:**
1. Ima između 5 i 15 znakova. 
2. Počinje slovom. 
3. Sadrži samo slova, brojeve ili znak `_`.
4. Ne sadrži razmake.

Unutar funkcija koristite Kotlinove ugrađene String funkcije:
- Uklonite sve slučajne početne ili završne razmake pomoću `.trim()`.
- Provjerite je li predano korisničko ime prazno pomoću `.isBlank()`. Ako jest, vratite `false`.
- Provjerite sadrži li dozvoljene znakove pomoću `.all { it.isLetterOrDigit() || it == '_' }`.
- Za provjeru razmaka poslužit će vam metoda `contains`.

Pozovite ove funkcije s nekoliko različitih testnih nizova i ispišite rezultate.

---

**Zadatak 5** – Trebate modelirati sustav bankovnih računa koji štiti stanja korisnika i prati koliko računa postoji globalno.

1. Izradite `object` pod nazivom `TransactionLogger`. Izradite funkciju `log` koja ispisuje poruku na konzoli.
2. Izradite klasu bankovnog računa s primarnim konstruktorom koji prima broj računa.
3. Unutar klase stvorite varijablu `balance` inicijaliziranu na `0.0`.
4. Izradite metode za uplatu i isplatu primljenog iznosa. Ove metode trebaju sigurno modificirati `balance` (npr. spriječiti podizanje više od stanja) i pozvati `log` metodu `TransactionLoggera` za praćenje radnje.
5. Dodajte `companion object` unutar bankovnog računa s brojem ukupno kreiranih računa. Dodajte `init` blok klasi bankovnog računa koji povećava broj računa svaki put kada se stvori nova instanca.
6. U `main` funkciji stvorite nekoliko računa, izvršite nekoliko uplata/isplata, ispišite njihova stanja i ispišite koliko je ukupno kreiranih računa.

