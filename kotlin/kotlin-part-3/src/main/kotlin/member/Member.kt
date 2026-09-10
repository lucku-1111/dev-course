package member

data class Member(val name: String, val email: String, val phone: String) {

    // 여기서 toString을 override 해 버리면 자동 생성된 것이 사라진다.
    // 디버깅용 출력은 남겨 두는 편이 낫기 때문에, 화면용은 따로 프로퍼티로 만든다.
    val display: String
        get() = "[이름] $name, [이메일] $email, [연락처] $phone"

}