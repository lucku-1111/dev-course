package member

private fun printPricePlan(): Int {
    println("[요금제를 선택하세요]")
    println("[1]Lite : 10명 [2]Basic : 20명 [3]Premium : 30명")
    print("> ")

    return readln().toInt()
}

fun main() {
    val planNo = printPricePlan()

    val app = MemberApp(MemberManager(planNo))
    app.start()
}