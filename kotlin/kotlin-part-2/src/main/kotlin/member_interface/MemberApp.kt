
package member_interface


class MemberApp(private val storage: MemberStorage) {

    fun start() {
        println("\n저장소: ${storage.storageName}")

        while (true) {
            when (printMenu()) {
                1 -> addMember()
                2 -> selectByEmail()
                3 -> selectByName()
                4 -> selectAll()
                5 -> updateMember()
                6 -> deleteMember()
                7 -> {
                    println("이용해주셔서 감사합니다.")
                    return
                }
                else -> println("올바른 번호를 입력하세요.")
            }
        }
    }

    private fun printMenu(): Int {
        println("\n[수행할 업무를 선택하세요 - 현재 회원수 : ${storage.memberCnt}명]")
        println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)")
        println("[4]회원전체조회 [5]회원정보 수정 [6]회원삭제")
        println("[7]프로그램 종료")
        print("> ")

        return readln().toIntOrNull() ?: -1
    }

    private fun addMember() {
        if (storage.isFull) {
            println("회원이 꽉 찼습니다.")
            return
        }

        println("이름을 입력하세요.")
        val name = readln()
        println("이메일을 입력하세요.")
        val email = readln()
        println("연락처를 입력하세요.")
        val phone = readln()

        if (storage.add(Member(name, email, phone))) {
            println("회원이 등록되었습니다.")
        } else {
            println("이미 존재하는 회원입니다.")
        }
    }

    private fun selectByEmail() {
        println("이메일을 입력하세요.")
        val email = readln()

        val member = storage.findByEmail(email)
        if (member == null) {
            println("찾으시는 정보가 없습니다.")
            return
        }

        println(member)
    }

    private fun selectByName() {
        println("이름을 입력하세요.")
        val name = readln()

        val member = storage.findByName(name)
        if (member == null) {
            println("찾으시는 정보가 없습니다.")
            return
        }

        println(member)
    }

    private fun selectAll() {
        if (storage.isEmpty()) {
            println("등록된 회원이 없습니다.")
            return
        }

        val all = storage.getAll()
        for (i in all.indices) {
            println("${i + 1}. ${all[i]}")
        }
    }

    private fun updateMember() {
        println("수정할 회원의 이메일을 입력하세요.")
        val email = readln()

        val member = storage.findByEmail(email)
        if (member == null) {
            println("찾으시는 회원이 없습니다.")
            return
        }

        println("현재 정보 → $member")

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

        if (storage.delete(email)) {
            println("삭제가 완료되었습니다.")
        } else {
            println("찾으시는 회원이 없습니다.")
        }
    }
}
