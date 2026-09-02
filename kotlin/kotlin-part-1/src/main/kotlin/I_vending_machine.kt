
// 상수 선언.
const val COKE = 500
const val CIDER = 500
const val FANTA = 300
const val WATER = 200

fun printMenu(totalMoney: Int) {
    println("================== 자판기 ==================")
    println("[1]콜라 : $COKE, [2]사이다 : $CIDER, [3]환타 : $FANTA, [4]물 : $WATER, [5]돈 넣기, [6]종료")
    println("현재 금액 : $totalMoney")
    println("============================================")
}

fun getChoice(): Int {
    println("원하는 메뉴를 선택하시오.")

    // Scanner.nextInt()
    return readln().toInt()
}

fun getMoney(): Int {
    println("돈을 넣으시오.")
    return readln().toInt()
}

fun calcMoney(totalMoney: Int, price: Int): Int {
    return totalMoney - price
}

fun calcMoneyException() {
    println("잔돈이 부족합니다.")
}

fun main() {

    var totalMoney = 0

    while (true) {

        printMenu(totalMoney)

        val choice = getChoice()
        var result = -1

        when (choice) {
            1 -> {
                result = calcMoney(totalMoney, COKE);
                if ( result < 0 ) {
                    calcMoneyException()
                } else {
                    totalMoney = result
                    println("콜라가 나왔습니다.")
                }

            }
            2 -> {
                result = calcMoney(totalMoney, CIDER);
                if ( result < 0 ) {
                    calcMoneyException()
                } else {
                    totalMoney = result
                    println("사이다가 나왔습니다.")
                }

            }
            3 -> {
                result = calcMoney(totalMoney, FANTA);
                if ( result < 0 ) {
                    calcMoneyException()
                } else {
                    totalMoney = result
                    println("환타가 나왔습니다.")
                }

            }
            4 -> {
                result = calcMoney(totalMoney, WATER);
                if ( result < 0 ) {
                    calcMoneyException()
                } else {
                    totalMoney = result
                    println("물이 나왔습니다.")
                }

            }
            5 -> totalMoney += getMoney()
            6 -> {
                println("\n잔돈 ${totalMoney}원이 반환되었습니다.")
                return
            }
            else -> println("잘 못 입력하셨습니다. 다시 입력해주세요.")
        }

    }

}

















