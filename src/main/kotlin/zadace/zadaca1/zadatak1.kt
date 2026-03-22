package ferit.zadace.zadaca1

fun main(){
    val ime: String
    val prezime: String
    var email: String? = null
    var age: Int? = 22

    println("Duljina email adrese: ${email?.length}")

    email = "ime.prezime@example.com"

    println("Duljina azurirane email adrese: ${email?.length}")
}