package ferit.zadace.zadaca1

//1.funkcija priprema, vraca odredenu vrijednost (string)
fun cleanUsername(username: String): String{
    return username.trim().lowercase()
}
//2.funkcija provjera, vraca Boolean, true
fun isValidUsername(username: String): Boolean{
    if(username.length !in 5..15
        || !username.first().isLetter()
        || !username.all { it.isLetterOrDigit() || it == '_' }
        || username.contains(' ')
        || username.isBlank()
    ) return false
    return true
}

fun main(){
    val testInputs = listOf(
        " John_doe123 ",
        "john_doe_123",
        "abc",
        "valjano_ime_1",
        " Invalid Name ",
        "!invalid",
        "ime01050prezime"
    )
    for (name in testInputs){
        val prepared = cleanUsername(name)
        val isValid = isValidUsername(prepared)
        println("Unos korisnickog imena: \"$name\"")
        println("  Pripremljeno: \"$prepared\"")
        println("  Ispravno: $isValid")
        println()
    }
}