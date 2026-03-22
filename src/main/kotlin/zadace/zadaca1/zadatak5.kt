package ferit.zadace.zadaca1

object TransactionLogger{
    fun log(poruka: String) {
        println("[LOG] $poruka")
    }
}

class BankovniRacun(val brojRacuna: String){
    var balance: Double = 0.0
        private set

    fun uplata(iznos: Double){
        if(iznos <= 0){
            TransactionLogger.log("Neuspjesna uplata na racun $brojRacuna: iznos mora biti pozitivan.")
            return
        }
        balance += iznos
        TransactionLogger.log("Uplata $iznos EUR na racun $brojRacuna. Novo stanje: $balance EUR")
    }

    fun isplata(iznos: Double) {
        if (iznos <= 0) {
            TransactionLogger.log("Neuspjesna isplata s racuna $brojRacuna: iznos mora biti pozitivan.")
            return
        }
        if (iznos > balance) {
            TransactionLogger.log("Neuspjesna isplata s racuna: nedovoljno sredstava na racunu $brojRacuna." +
                    "Stanje: ${balance} EUR, trazeno: ${iznos} EUR")
            return
        }
        balance -= iznos
        TransactionLogger.log("Isplata $iznos EUR s racuna $brojRacuna. Novo stanje: $balance EUR")
    }

    companion object {
        var ukupnoRacuna: Int = 0
            private set
    }

    init {
        BankovniRacun.ukupnoRacuna++
        TransactionLogger.log("Otvoren novi racun: $brojRacuna")
    }
}

fun main(){
    val racun1 = BankovniRacun("HR00100123")
    val racun2 = BankovniRacun("HR09876543")
    val racun3 = BankovniRacun("HR12345678")
    val racun4 = BankovniRacun("HR11223344")

    println("\nTransakcije:")
    racun1.uplata(500.0)
    racun1.uplata(200.0)
    racun1.isplata(100.0)
    racun1.isplata(1000.0)

    racun2.uplata(1000.0)
    racun2.isplata(250.0)

    racun3.uplata(50.0)

    racun4.isplata(175.0)

    println("\nStanja racuna:")
    println("Racun ${racun1.brojRacuna}: ${racun1.balance} EUR")
    println("Racun ${racun2.brojRacuna}: ${racun2.balance} EUR")
    println("Racun ${racun3.brojRacuna}: ${racun3.balance} EUR")
    println("Racun ${racun4.brojRacuna}: ${racun4.balance} EUR")
    println("Ukupno kreiranih racuna: ${BankovniRacun.ukupnoRacuna}")
}