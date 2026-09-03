var totalCnt = 0
var memberCnt = 0

fun printPricePlan(): Int {
    println("[요금제를 선택하세요]")
    println("[1]Lite : 10명 [2]Basic : 20명 [3]Premium : 30명")
    print("> ")
    return readln().toInt()
}

fun printMenu(): Int {
    println("\n[수행할 업무를 선택하세요 - 현재 회원수 : $memberCnt/$totalCnt]")
    println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)")
    println("[4]회원전체조회 [5]회원정보 수정 [6]회원삭제")
    println("[7]프로그램 종료")
    print("> ")
    return readln().toInt()
}

fun addMember(members: Array<Array<String>>) {
    if (memberCnt == totalCnt) {
        println("회원이 꽉 찼습니다.")
        return
    }

    println("이름을 입력하세요.")
    val name = readln()
    println("이메일을 입력하세요.")
    val email = readln()
    println("연락처를 입력하세요.")
    val phone = readln()

    for (i in 0 until memberCnt) {
        if (email == members[i][1]) {
            println("이미 존재하는 회원입니다.")
            return
        }
    }

    members[memberCnt][0] = name
    members[memberCnt][1] = email
    members[memberCnt][2] = phone
    memberCnt++
    println("회원이 등록되었습니다.")
}

fun printMember(member: Array<String>) {
    println("[이름] ${member[0]}, [이메일] ${member[1]}, [연락처] ${member[2]}")
}

fun findIndex(members: Array<Array<String>>, col: Int, value: String): Int {
    for (i in 0 until memberCnt) {
        if (value == members[i][col]) {
            return i
        }
    }
    return -1
}

fun selectEmail(members: Array<Array<String>>) {
    println("이메일을 입력하세요.")
    val email = readln()

    val idx = findIndex(members, 1, email)
    if (idx == -1) {
        println("찾으시는 정보가 없습니다.")
        return
    }
    printMember(members[idx])
}

fun selectName(members: Array<Array<String>>) {
    println("이름을 입력하세요.")
    val name = readln()

    val idx = findIndex(members, 0, name)
    if (idx == -1) {
        println("찾으시는 정보가 없습니다.")
        return
    }
    printMember(members[idx])
}

fun selectAll(members: Array<Array<String>>) {
    if (memberCnt == 0) {
        println("등록된 회원이 없습니다.")
        return
    }
    for (i in 0 until memberCnt) {
        print("${i + 1}. ")
        printMember(members[i])
    }
}

fun updateMember(members: Array<Array<String>>) {
    println("수정할 회원의 이메일을 입력하세요.")
    val email = readln()

    val idx = findIndex(members, 1, email)
    if (idx == -1) {
        println("찾으시는 회원이 없습니다.")
        return
    }

    println("현재 정보 → ")
    printMember(members[idx])

    println("새 이름을 입력하세요.")
    members[idx][0] = readln()
    println("새 이메일을 입력하세요.")
    members[idx][1] = readln()
    println("새 연락처를 입력하세요.")
    members[idx][2] = readln()

    println("수정이 완료되었습니다.")
}

fun deleteMember(members: Array<Array<String>>) {
    println("삭제할 회원의 이메일을 입력하세요.")
    val email = readln()

    val idx = findIndex(members, 1, email)
    if (idx == -1) {
        println("찾으시는 회원이 없습니다.")
        return
    }

    for (i in idx until memberCnt - 1) {
        members[i][0] = members[i + 1][0]
        members[i][1] = members[i + 1][1]
        members[i][2] = members[i + 1][2]
    }

    memberCnt--

    members[memberCnt][0] = ""
    members[memberCnt][1] = ""
    members[memberCnt][2] = ""

    println("삭제가 완료되었습니다.")
}

fun main() {
    var num = 0
    while (true) {
        num = printPricePlan()
        if (num in 1..< 4) {
            break
        }
        println("올바른 번호를 입력하세요.")
    }
    totalCnt = num * 10
    val members = Array(totalCnt) {Array(3) {""}}

    while (true) {
        when (printMenu()) {
            1 -> addMember(members)
            2 -> selectEmail(members)
            3 -> selectName(members)
            4 -> selectAll(members)
            5 -> updateMember(members)
            6 -> deleteMember(members)
            7 -> {
                println("이용해주셔서 감사합니다.")
                return
            }
            else -> println("올바른 번호를 입력하세요.")
        }
    }
}