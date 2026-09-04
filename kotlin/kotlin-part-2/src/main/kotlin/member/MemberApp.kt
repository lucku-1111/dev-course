package member

// 메뉴와 입력, 출력을 담당하는 클래스

class MemberApp(private val manager: MemberManager) {

    fun start() {

        while (true) {
            when (printMenu()) {
                7 -> {
                    println("이용해주셔서 감사합니다.")
                    return
                }
                else -> println("올바른 번호를 입력해주세요.")
            }
        }

    }

    private fun printMenu(): Int {
        println("\n[수행할 업무를 선택하세요 - 현재 회원수 : ${manager.memberCnt}/${manager.totalCnt}]")
        println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)")
        println("[4]회원전체조회 [5]회원정보 수정 [6]회원삭제")
        println("[7]프로그램 종료")
        print("> ")

        return readln().toInt()
    }

    private fun addMember() {
        // 규칙 검사는 manager 가 하지만, 왜 실패했는지 알려 주려면 미리 물어봐야 한다.
        if (manager.isFull) {
            println("회원이 꽉 찼습니다.")
            return
        }

        println("이름을 입력하세요.")
        val name = readln()
        println("이메일을 입력하세요.")
        val email = readln()
        println("연락처를 입력하세요.")
        val phone = readln()

        // 입력받은 값으로 회원 객체를 만들어서 통째로 넘긴다.
        if (manager.addMember(Member(name, email, phone))) {
            println("회원이 등록되었습니다.")
        } else {
            println("이미 존재하는 회원입니다.")
        }
    }

    private fun selectByEmail() {
        println("이메일을 입력하세요.")
        val email = readln()

        // 돌려받은 값이 Member? 이므로 null 검사를 하지 않으면 컴파일이 되지 않는다.
        val member = manager.findByEmail(email)
        if (member == null) {
            println("찾으시는 정보가 없습니다.")
            return
        }

        // println(객체) 를 하면 toString() 이 호출된다.
        println(member)
    }

    private fun selectByName() {
        println("이름을 입력하세요.")
        val name = readln()

        val member = manager.findByName(name)
        if (member == null) {
            println("찾으시는 정보가 없습니다.")
            return
        }

        println(member)
    }

    private fun selectAll() {
        val all = manager.getAll()
        if (all.isEmpty()) {
            println("등록된 회원이 없습니다.")
            return
        }

        // 등록된 회원만 잘라서 받았으므로 빈 칸이 섞일 걱정이 없다.
        for (i in all.indices) {
            println("${i + 1}. ${all[i]}")
        }
    }

    private fun updateMember() {
        println("수정할 회원의 이메일을 입력하세요.")
        val email = readln()

        val member = manager.findByEmail(email)
        if (member == null) {
            println("찾으시는 회원이 없습니다.")
            return
        }

        println("현재 정보 → $member")

        // findByEmail 은 저장소 안에 들어 있는 '그 객체'를 돌려준다. 복사본이 아니다.
        // 그래서 여기서 값을 바꾸면 저장소의 내용이 그대로 바뀐다. 인덱스를 들고 다닐 필요가 없다.
        // (다만 이 방법은 manager 를 거치지 않으므로 이메일 중복 검사를 건너뛴다. Main.kt 의 [1] 참고)
        println("새 이름을 입력하세요.")
        member.name = readln()
        println("새 이메일을 입력하세요.")
        member.email = readln()
        println("새 연락처를 입력하세요.")
        member.phone = readln()

        println("수정이 완료되었습니다.")
    }

    private fun deleteMember() {
        println("삭제할 회원의 이메일을 입력하세요.")
        val email = readln()

        if (manager.delete(email)) {
            println("삭제가 완료되었습니다.")
        } else {
            println("찾으시는 회원이 없습니다.")
        }
    }

}