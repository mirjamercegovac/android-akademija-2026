package ferit.zadace.zadaca1

fun main(){
    val kodProizvoda = 2
    val cijenaProizvoda = 2.65
    val primljeniNovac = 4.00

    val imePica = when(kodProizvoda){
        1 -> "Voda"
        2 -> "Cola"
        3 -> "Sok"
        4 -> "Kava"
        else -> "Nepoznato pice"
    }

    if(primljeniNovac >= cijenaProizvoda){
        val ostatak = primljeniNovac - cijenaProizvoda
        println("Pice koje se toci je $imePica, a ostatak iznosi: ${"%.2f".format(ostatak)} EUR")
    }else{
        val nedostaje = cijenaProizvoda - primljeniNovac
        println("Nedostaje ${"%.2f".format(nedostaje)} EUR za pice $imePica.")
    }
}