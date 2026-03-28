package ferit.zadace.zadaca1

fun main(){
    val productCode = 2
    val productPrice = 2.65
    val receivedMoney = 4.00

    val drinkName  = when(productCode){
        1 -> "Voda"
        2 -> "Cola"
        3 -> "Sok"
        4 -> "Kava"
        else -> "Nepoznato pice"
    }

    if(receivedMoney  >= productPrice){
        val change = receivedMoney - productPrice
        println("Pice koje se toci je $drinkName, a ostatak iznosi: ${"%.2f".format(change )} EUR")
    }else{
        val missingAmount  = productPrice - receivedMoney
        println("Nedostaje ${"%.2f".format(missingAmount )} EUR za pice $drinkName.")
    }
}