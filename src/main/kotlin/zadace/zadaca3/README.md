# Domaća zadaća 3 - Android akademija 2026
## 3. Uvod u Android i Jetpack Compose

**Zadatak 1**

Riješite sve zadatke iz prezentacije:
- Zadatak 1: Izrada vlastitih komponenti
  - `TitleText`
  - `DescriptionText`
  - `CustomButton`
- Zadatak 2: Kartica (`ItemCard`)
- Zadatak 3: Lista (`LazyColumn`)
- Zadatak 4: State
- Zadatak 5: Navigacija između `ListScreen` i `DetailScreen`
- bonus: `SearchBar` za filtriranje elemenata

---

**Zadatak 2**

Potrebno je napraviti aplikaciju s dva zaslona (screena) prikazana na slikama na kraju dokumenta.

Aplikacija predstavlja jednostavan sustav za bilješke gdje korisnik može pregledavati, dodavati i uređivati bilješke.

**a) Zaslon 1 (lista bilješki)** 

Zaslon 1 treba sadržavati:
- State (stanje) koji čuva listu bilješki 
- Bilješke trebaju biti definirane pomoću data klase 
- Svaka bilješka mora imati: 
  - id 
  - naslov 
  - opis 
- Po želji možete dodati dodatne podatke (npr. datum, sliku…) 
- Prikaz liste bilješki 
- Gumb za dodavanje nove bilješke (npr. “+”) 
  - Klikom se otvara Zaslon 2 za unos nove bilješke 
- Klik na pojedinu bilješku
  - Otvara Zaslon 2 
    - U tom slučaju podaci (naslov i opis) trebaju biti već popunjeni

**b) Zaslon 2 (unos / uređivanje bilješke)**

Zaslon 2 treba sadržavati:

- state za naslov i opis (koristi se za upravljanje unosom u `TextFieldovima`)
- gumb za povratak
    - vraća korisnika na Zaslon 1
- polja za unos (`TextField`)
    - unos naslova
    - unos opisa
- gumb za spremanje (npr. `Spremi` / `Done`)
    - sprema novu bilješku ili promjene postojeće

- **Važno:**
  - ovaj zaslon se koristi za:
      - dodavanje nove bilješke (polja su prazna)
      - uređivanje postojeće bilješke (polja su popunjena)

**HINT:** Za navigaciju je potrebno dodati library koji možete pronaći na sljedećem linku: https://developer.android.com/develop/ui/compose/navigation