package ferit.zadace.zadaca1

//1.funkcija priprema, vraca odredenu vrijednost (string)
fun pripremaKorisnickogImena(korisnickoIme: String): String{
    return korisnickoIme.trim().lowercase()
}
//2.funkcija provjera, vraca Boolean, true
fun validacijaKorisnickogImena(korisnickoIme: String): Boolean{
    if(korisnickoIme.length !in 5..15
        || !korisnickoIme.first().isLetter()
        || !korisnickoIme.all { it.isLetterOrDigit() || it == '_' }
        || korisnickoIme.contains(' ')
        || korisnickoIme.isBlank()
    ) return false
    return true
}

fun main(){
    val testniNizovi = listOf(
        " John_doe123 ",
        "john_doe_123",
        "abc",
        "valjano_ime_1",
        " Invalid Name ",
        "!invalid",
        "ime01050prezime"
    )
    for (ime in testniNizovi){
        val pripremljeno = pripremaKorisnickogImena(ime)
        val ispravno = validacijaKorisnickogImena(pripremljeno)
        println("Unos korisnickog imena: \"$ime\"")
        println("  Pripremljeno: \"$pripremljeno\"")
        println("  Ispravno: $ispravno")
        println()
    }
}