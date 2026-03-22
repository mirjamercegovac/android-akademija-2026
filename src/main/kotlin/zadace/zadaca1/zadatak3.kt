package ferit.zadace.zadaca1

fun main(){
    val koraci = listOf(4900, 13500, 6020, 15000, 3150, 11500, 9800)

    var ukupno = 0

    for (dnevniBrojKoraka in koraci){
        ukupno += dnevniBrojKoraka
    }
    println("Ukupni zbroj koraka za tjedan je $ukupno")

    var index = 0

    while (index < koraci.size){
        if(koraci[index] > 10000){
            println("Prvi dan kada je korisnik premasio 10 000 koraka je ${index+1} dan, a broj koraka za taj dan iznosi ${koraci[index]}.")
            break
        }
        index++
    }
}