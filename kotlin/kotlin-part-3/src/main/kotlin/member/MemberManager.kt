package member

class MemberManager(planNo: Int) {

    val totalCnt = planNo * 10
    // val : 요소를 추가하고 삭제할 수 있다. 단 주소참조 변경은 불가능
    private val members = mutableListOf<Member>()
    // 커스텀 getter : 값을 저장해 두지 않고 읽을 때마다 계산한다
    val memberCnt: Int
        get() = members.size
    val isFull: Boolean
        get() = totalCnt <= members.size

    // 이메일 조회
//    fun findByEmail(email: String): Member? = members.find { member -> member.email == email }
    fun findByEmail(email: String): Member? = members.find { it.email == email }

    // 이름 조회
//    fun findByName(name: String): Member? = members.find { member -> member.name == name }
    fun findByName(name: String): Member? = members.find { it.name == name }

    // keyword 조회(일부 키워드만 가지고 조회)
    // 이름 keyword가 들어간 회원 '전부' 찾는다.
    // '김'
    fun searchByName(keyword: String): List<Member> = members.filter { it.name.contains(keyword) }

    // 전체조회
    fun getAll(): List<Member> = members.toList()

    // 추가
    fun addMember(member: Member): Boolean {

        // 정원 체크
        if ( isFull ) return false

        // 이메일 중복 체크
        if ( members.any { it.email == member.email } ) return false

        members.add(member)
        return true
    }

    // removeAll을 사용하면 코드가 간소해진다.
    fun deleteMember( email: String ): Boolean = members.removeAll { it.email == email }

    /*
        // removeAt
        fun deleteMember(email: String): Boolean {

            val idx = members.indexOfFirst { it.email == email }
            if (idx == -1) return false
            members.removeAt(idx)

            return true
        }

        // remove
        fun deleteMember2(email: String): Boolean {
            val member = members.find{ it.email == email }?: return false
            return members.remove(member)
        }
*/
    fun updateMember(email: String, newEmail: String, name: String, phone: String): Boolean {

        // indexOfFirst : 조건에 맞는 첫 번째 '위치'를 준다. 없으면 -1을 반환
        val idx = members.indexOfFirst { it.email == email }
        if (idx == -1) return false

        // 이메일을 바꾸는 경우에만 중복을 검사한다.
        // newEmail != email 을 빼면, 이름만 바꾸려 할 때 자기 이메일이 중복으로 걸려서 수정이 거부된다.
        if ( newEmail != email && members.any { it.email == newEmail } ) return false

        // 고치는 대신 새로 만들어서 갈아 끼운다.
        members[idx] = members[idx].copy(name = name, email = email, phone = phone)

        return true
    }

}