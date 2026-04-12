package ferit.zadace.zadaca1

fun main(){
    val steps = listOf(4900, 13500, 6020, 15000, 3150, 11500, 9800)

    var total = 0

    for (dailySteps in steps){
        total += dailySteps
    }
    println("Ukupni zbroj koraka za tjedan je $total")

    var index = 0

    while (index < steps.size){
        if(steps[index] > 10000){
            println("Prvi dan kada je korisnik premasio 10 000 koraka je ${index+1} dan, a broj koraka za taj dan iznosi ${steps[index]}.")
            break
        }
        index++
    }
}