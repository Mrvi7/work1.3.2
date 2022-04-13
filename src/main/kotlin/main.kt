const val cardVisa = "Visa"
const val cardMaestro = "Maestro"
const val cardMaster = "Mastercard"
const val cardMir = "Mir"
const val vkPay = "VK Pay"
const val account = 500_000
const val amount = 76_800
const val minTax = 35
const val tax = 0.75
const val tax2 = 0.6

fun calculateTax(): Double {
    val result = amount * tax / 100
    return if (result < minTax) minTax.toDouble() else result
}

fun calculateTax2(): Double {
    return if (amount in 0..75000) 0.0 else (amount * tax2 / 100) + 20
}

fun transfer(amount: Int, type: String = vkPay) {
    if (account > (amount + calculateTax())) calculateTotal(amount, type)
    else println(
        "Средств не хватает\nБаланс " +
                "$account ${rub(account.toDouble())}"
    )
}

fun calculateTotal(x: Int, type: String = vkPay) {
    val result = account - x - calculateTax()
    val result2 = (account - x - (x * tax)) % 100
    val result3 = account - x - calculateTax2()
    val result4 = (account - x - (x * tax2)) % 100
    when (type) {
        cardVisa, cardMir ->
            println(
                "Сумма перевода $x ${rub(x.toDouble())}" +
                        "\nКомиссия за перевод ${calculateTax().toInt()} ${rub(calculateTax())}" +
                        " ${((amount * tax) % 100).toInt()} копеек" +
                        "\nБаланс ${result.toInt()} ${rub(result)} ${result2.toInt()} копеек"
            )
        cardMaster, cardMaestro ->
            println(
                "Сумма перевода $x ${rub(x.toDouble())}" +
                        "\nКомиссия за перевод ${calculateTax2().toInt()} ${rub(calculateTax2())}" +
                        " ${((amount * tax2) % 100).toInt()} копеек" +
                        "\nБаланс ${result3.toInt()} ${rub(result3)} ${result4.toInt()} копеек"
            )
        else -> println(
            "Сумма перевода $x ${rub(x.toDouble())}" +
                    "\nБез комиссии" +
                    "\nБаланс ${account - amount} ${rub(account.toDouble() - amount.toDouble())}"
        )
    }
}

fun main() {
    transfer(amount, "Mastercard")
}

fun rub(a: Double): String {

    val y = when {
        a.toInt() % 10 == 1 -> "рубль"
        a.toInt() % 100 in 5..20 -> "рублей"
        a.toInt() % 10 in 2..4 -> "рубля"
        else -> "рублей"
    }
    return y
}