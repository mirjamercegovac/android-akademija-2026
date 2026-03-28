package ferit.zadace.zadaca1

object TransactionLogger{
    fun log(message: String) {
        println("[LOG] $message")
    }
}

class BankAccount(val accountNumber: String){
    var balance: Double = 0.0
        private set

    fun deposit(amount : Double){
        if(amount <= 0){
            TransactionLogger.log("Neuspjesna uplata na racun $accountNumber: iznos mora biti pozitivan.")
            return
        }
        balance += amount
        TransactionLogger.log("Uplata $amount  EUR na racun $accountNumber. Novo stanje: $balance EUR")
    }

    fun withdraw(amount : Double) {
        if (amount <= 0) {
            TransactionLogger.log("Neuspjesna isplata s racuna $accountNumber: iznos mora biti pozitivan.")
            return
        }
        if (amount > balance) {
            TransactionLogger.log("Neuspjesna isplata s racuna: nedovoljno sredstava na racunu $accountNumber." +
                    "Stanje: ${balance} EUR, trazeno: ${amount} EUR")
            return
        }
        balance -= amount
        TransactionLogger.log("Isplata $amount  EUR s racuna $accountNumber. Novo stanje: $balance EUR")
    }

    companion object {
        var totalAccounts: Int = 0
            private set
    }

    init {
        BankAccount.totalAccounts++
        TransactionLogger.log("Otvoren novi racun: $accountNumber")
    }
}

fun main(){
    val account1 = BankAccount("HR00100123")
    val account2 = BankAccount("HR09876543")
    val account3 = BankAccount("HR12345678")
    val account4 = BankAccount("HR11223344")

    println("\nTransakcije:")
    account1.deposit(500.0)
    account1.deposit(200.0)
    account1.withdraw(100.0)
    account1.withdraw(1000.0)

    account2.deposit(1000.0)
    account2.withdraw(250.0)

    account3.deposit(50.0)

    account4.withdraw(175.0)

    println("\nStanja racuna:")
    println("Racun ${account1.accountNumber}: ${account1.balance} EUR")
    println("Racun ${account2.accountNumber}: ${account2.balance} EUR")
    println("Racun ${account3.accountNumber}: ${account3.balance} EUR")
    println("Racun ${account4.accountNumber}: ${account4.balance} EUR")
    println("Ukupno kreiranih racuna: ${BankAccount.totalAccounts}")
}