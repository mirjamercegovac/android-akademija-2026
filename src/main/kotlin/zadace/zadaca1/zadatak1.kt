package ferit.zadace.zadaca1

fun main(){
    val firstName: String
    val lastName: String
    var email: String? = null
    var age: Int? = 22

    println("Duljina email adrese: ${email?.length}")

    email = "ime.prezime@example.com"

    println("Duljina azurirane email adrese: ${email?.length}")
}