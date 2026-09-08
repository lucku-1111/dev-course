import kotlin.properties.Delegates

// * 지연 초기화와 위임 - lateinit, by lazy, by

// 1.  왜 지연 초기화가 필요한가
// 코틀린의 프로퍼티는 선언할 때 반드시 값이 있어야 한다
/*
class Service {
    var name: String // 컴파일 에러 -> 초깃값이 없다.
}
 */

// 하지만 "지금은 값을 모르고, 나중에 정해지는" 경우가 있다.
// - 화면이 만들어진 뒤에야 값이 정해질 때
// - 만드는 데 시간이 오래 걸려서, 실제로 쓸 때까지는 미루고 싶을 때
// 억지로 null을 허용(String?)하면 쓸 때마다 ?. 나 !!를 붙여야 해서 불편하다.
// 이럴 때 쓰는 것이 '지연 초기화'다.

// 2. lateinit
// lateinit var name: String
// name = "홍길동"

// * 조건
// - var에만 붙일 수 있다. (val은 불가)
// - null을 허용하지 않는 자료형이어야 한다. (String? 불가)
// - Int, Double 같은 기본 자료형에는 쓸 수 없다.

// * 초기화가되었는지 확인하려면 ::프로퍼티이름.isInitialized 를 쓴다.

// 3. by lazy - 처음 쓸 때 딱 한 번 만들어 진다.
/*
val config: String by lazy { // 선언할 때가 아니라 '처음 읽을 때' 실행된다.
    println("만드는중...")
//    "설정값"
}
 */

// * 특징
// - val에만 쓸 수 있다.
// - 중괄호 안의 결과가 그 프로퍼티 값이 된다.
// - 처음 읽을 때 한 번만 실행되고, 그 뒤로는 계산해 둔 값을 그대로 돌려준다.
// - 만드는 데 비용이 큰 것(파일 읽기, 계산이 오래 걸리는 것)을 미룰 때 쓴다.

// 4. lateinit vs by lazy
//                    lateinit var            by lazy
//   var / val        var 만                  val 만
//   값을 넣는 주체     밖에서 직접 넣는다        스스로 만든다
//   초기화 시점        내가 넣는 그때           처음 읽는 그때
//   기본 자료형        불가 (Int 등)           가능
//   용도              나중에 주입받을 때        비싼 값을 미룰 때

// 5. 위임(delegate)
// by 키워드 - 다른쪽에 일을 맡긴다.

// * 밑줄(_) -> 이 자리는 쓰지 않겠다.
// 값이 오긴 하지만 쓸 일이 없을 때, _ 로 자리만 채운다.


// 1. lateinit
class Service {

    lateinit var serverName: String

    fun connect() {

        if ( ::serverName.isInitialized ) {
            println("$serverName connected")
        } else {
            println("serverName not connected")
        }

    }

}

// 2. by lazy : 처음 읽을 때 딱 한 번 만들어진다.
class AppConfig {

    val settings: String by lazy {
        println("[lazy] 설정 파일을 읽는 중...")
        "테마=다크, 언어=한국어"
    }

    // 객체를 만들 때 초기화가된다.
    val appName: String = "코틀린 강의"

}

// 3. Delegates.observable - 값이 바뀔 때마다 알림 받기
class Player(val name: String) {

    var hp: Int by Delegates.observable(100) {
            _, old, new -> println("[변경] HP $old -> $new")
    }

    // 조건에 맞을 때만 값 변경을 허용한다.
    var level: Int by Delegates.vetoable(1) { _, old, new -> old < new }

}

// 4. 클래스 위임 - 인터페이스 구현을 다른 객체에 맡긴다.
interface Speaker {
    fun speak(message: String)
    fun introduce()
}

class KoreanSpeaker(val speakerName: String) : Speaker {
    override fun speak(message: String) {
        println("$speakerName : $message")
    }

    override fun introduce() {
        println("$speakerName 입니다.")
    }

}

// Speaker를 구현해야 하지만, 실제 일은 speaker에게 맡긴다.
// speak() 와 introduce() 를 직접 만들지 않아도 된다.
class Robot(speaker: Speaker) : Speaker by speaker {

    override fun introduce() {
        println("저는 로봇입니다.")
    }

}


// ------------------------------------------------------------
// 예제 1. lateinit
// ------------------------------------------------------------
fun i_exam1() {
    val service = Service()

    // 값을 넣기 전에 쓰면 예외가 난다.
    service.connect()                   // 서버 이름이 아직 정해지지 않았습니다.
    // println(service.serverName)      // 실행 중 UninitializedPropertyAccessException!

    // 나중에 값을 넣는다.
    service.serverName = "메인 서버"
    service.connect()                   // 메인 서버 에 접속합니다.
    println(service.serverName)         // 이제는 ?. 나 !! 없이 그냥 쓸 수 있다

    // lateinit 을 안 쓰고 null 허용으로 만들었다면 이렇게 써야 했다.
    //   var serverName: String? = null
    //   println(serverName?.length)     // 쓸 때마다 ?. 가 붙는다
}

// ------------------------------------------------------------
// 예제 2. by lazy
// ------------------------------------------------------------
fun i_exam2() {
    println("AppConfig 객체를 만듭니다.")
    val config = AppConfig()
    println("만들었습니다. 아직 settings 는 읽지 않았습니다.")

    // appName 은 객체를 만들 때 이미 초기화되어 있다.
    println(config.appName)

    // settings 를 '처음 읽는 순간' 중괄호 안이 실행된다.
    println("첫 번째 읽기:")
    println(config.settings)

    // 두 번째부터는 만들어 둔 값을 그대로 돌려준다. lazy 블록이 다시 실행되지 않는다.
    println("두 번째 읽기:")
    println(config.settings)
}

// ------------------------------------------------------------
// 예제 3. observable - 값이 바뀔 때마다 알림 받기
// ------------------------------------------------------------
fun i_exam3() {
    val player = Player("홍길동")

    println("초기 HP: ${player.hp}")

    // 값을 바꿀 때마다 등록해 둔 블록이 실행된다.
    player.hp = 80                      //   [변경] HP 100 -> 80
    player.hp = 50                      //   [변경] HP 80 -> 50
    player.hp = 100                     //   [변경] HP 50 -> 100

    println("최종 HP: ${player.hp}")
}

// ------------------------------------------------------------
// 예제 4. vetoable - 조건에 맞을 때만 값 바꾸기
// ------------------------------------------------------------
fun i_exam4() {
    val player = Player("홍길동")

    println("초기 레벨: ${player.level}")   // 1

    player.level = 5
    println("5 로 변경 시도 -> ${player.level}")     // 5   (오르는 것이므로 허용)

    player.level = 3
    println("3 으로 변경 시도 -> ${player.level}")   // 5   (내려가는 것이라 거부됨!)

    player.level = 10
    println("10 으로 변경 시도 -> ${player.level}")  // 10  (허용)

    // H_encapsulation 의 커스텀 setter 로도 비슷한 일을 할 수 있지만,
    // 위임을 쓰면 그 규칙을 여러 프로퍼티에 재사용할 수 있다.
}

// ------------------------------------------------------------
// 예제 5. 클래스 위임
// ------------------------------------------------------------
fun i_exam5() {
    val korean = KoreanSpeaker("홍길동")
    korean.speak("안녕하세요")           // 홍길동: 안녕하세요
    korean.introduce()                  // 홍길동 입니다.

    println("---")

    // Robot 은 Speaker 를 구현했지만 speak() 를 직접 만들지 않았다.
    val robot = Robot(korean)
    robot.speak("삐빅")                  // 홍길동: 삐빅   <- korean 이 대신 처리했다
    robot.introduce()                   // 저는 로봇입니다. <- 재정의한 것은 자기 것이 실행된다

    println("=========")

    // Robot 도 Speaker 타입이므로 다형성이 그대로 적용된다.
    val speakers: List<Speaker> = listOf(korean, robot)
    for (s in speakers) {
        s.introduce()
    }
}

fun main() {
    i_exam1()
    i_exam2()
    i_exam3()
    i_exam4()
    i_exam5()
}
