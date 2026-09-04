
// * 정보 은닉과 캡슐화 - private, protected, internal, public

// 캡슐화는 데이터와 그것을 다루는 기능을 하나로 묶고,
// 바깥에서 함부로 건드리면 안 되는 것은 감추는 것이다.
// 감추는 쪽에 초점을 맞춘 표현이 정보 은닉이다.

// 1. 왜 감추는가
// 프로퍼티를 그대로 열어 두면 아무나 아무 값이나 넣을 수 있다.
//   account.balance = -99999       // 잔액이 음수가 되어 버린다
//
// 프로퍼티를 감추고, 정해 둔 통로(메서드)로만 바꾸게 하면 규칙을 지킬 수 있다.
//   account.deposit(1000)          // 입금 규칙을 통과한 값만 반영된다

// 2. 가시성 제어자 4가지
//   public    : 어디서나 접근 가능. 아무것도 안 쓰면 이것이다.
//   private   : 선언된 곳 안에서만. (클래스 안 / 그 파일 안)
//   protected : 자기 자신과 자식 클래스에서만.
//   internal  : 같은 모듈(같은 프로젝트) 안에서만.

// * 자바와 다른 점
//   - 기본값이 public 이다. (자바는 package-private)
//   - protected 는 자기 + 자식만이다. 같은 패키지라고 접근할 수 없다. (자바보다 좁다)
//   - package-private 이 없고, 대신 모듈 단위의 internal 이 있다.
//
// * 최상위(파일 맨 바깥)에 선언한 것에 private 을 붙이면 '그 파일 안에서만'이 된다.

// 3. 감추고 통로만 열기
//   class BankAccount {
//       private var balance: Int = 0          // 밖에서 못 건드린다
//
//       fun deposit(money: Int) { ... }       // 정해진 통로만 열어 둔다
//       fun getBalance(): Int = balance
//   }

// 4. 커스텀 setter 와 field
// 아예 막는 대신, 값을 넣을 때 검사해서 걸러 낼 수도 있다.
//   var age: Int = 0
//       set(value) {
//           field = if (value < 0) 0 else value    // 음수면 0 으로
//       }
//
// * field 는 그 프로퍼티의 실제 저장 공간을 가리키는 이름이다.
//   setter 안에서 age = value 라고 쓰면 setter 가 자기 자신을 다시 불러 무한 반복이 된다.
//   반드시 field 에 넣어야 한다.

// 5. private set - 밖에서는 읽기만, 안에서는 쓰기
//   var score: Int = 0
//       private set          // getter 는 public, setter 만 private
//
// 가장 자주 쓰는 형태다. 값은 누구나 볼 수 있지만 바꾸는 것은 클래스 안에서만 가능하다.

// 1) private 으로 감추고 메서드로만 다루기
class BankAccount(val owner: String) {

    private var balance: Int = 0            // 밖에서 직접 접근할 수 없다

    fun deposit(money: Int) {
        if (money <= 0) {
            println("입금액은 0원보다 커야 합니다.")
            return
        }
        balance += money
        println("${money}원 입금. 잔액 ${balance}원")
    }

    fun withdraw(money: Int) {
        if (money > balance) {
            println("잔액이 부족합니다. (현재 ${balance}원)")
            return
        }
        balance -= money
        println("${money}원 출금. 잔액 ${balance}원")
    }

    fun getBalance(): Int = balance         // 읽기 전용 통로

    // 클래스 안에서만 쓰는 함수도 private 으로 감출 수 있다.
    private fun printLog() {
        println("[LOG] $owner / $balance")
    }

    fun report() {
        printLog()                          // 안에서는 부를 수 있다
    }
}

// 2) 커스텀 setter 로 값 검사하기
class Thermometer {

    var celsius: Double = 0.0
        set(value) {
            // 절대영도(-273.15)보다 낮은 값은 들어올 수 없다.
            field = if (value < -273.15) -273.15 else value
        }

    // 저장 공간 없이 계산만 하는 프로퍼티 (A_oop 의 커스텀 getter)
    val fahrenheit: Double
        get() = celsius * 9 / 5 + 32
}

// 3) private set - 밖에서는 읽기만
class GameScore(val playerName: String) {

    var score: Int = 0
        private set                         // 바꾸는 것은 이 클래스 안에서만

    var level: Int = 1
        private set

    fun addScore(point: Int) {
        score += point
        // 점수 100점마다 레벨이 오른다.
        level = score / 100 + 1
    }
}

// 4) protected - 자식에게만 열어 주기
open class Character(val name: String) {

    protected var hp: Int = 100             // 자기 자신과 자식만 접근 가능
    private var secretCode: Int = 1234      // 자기 자신만 접근 가능

    fun showHp() {
        println("$name HP: $hp")
    }
}

class Warrior(name: String) : Character(name) {

    fun attack() {
        hp -= 10                            // 부모의 protected 프로퍼티. 접근 가능!
        // secretCode                       // 컴파일 에러! private 은 자식도 못 본다
        println("$name 이(가) 공격했습니다. (HP 10 감소)")
    }
}

// 5) internal - 같은 모듈 안에서만
internal class InternalHelper {
    fun help() {
        println("같은 모듈 안에서만 쓸 수 있는 클래스입니다.")
    }
}

// ------------------------------------------------------------
// 예제 1. private - 감추고 통로만 열기
// ------------------------------------------------------------
fun h_exam1() {
    val account = BankAccount("홍길동")

    // account.balance = 999999         // 컴파일 에러! private 이라 밖에서 못 건드린다
    // account.printLog()               // 컴파일 에러! private 메서드

    // 정해진 통로로만 값을 바꾼다. 규칙 검사가 반드시 통과된다.
    account.deposit(10000)              // 10000원 입금. 잔액 10000원
    account.deposit(-500)               // 입금액은 0원보다 커야 합니다.
    account.withdraw(3000)              // 3000원 출금. 잔액 7000원
    account.withdraw(999999)            // 잔액이 부족합니다. (현재 7000원)

    println("최종 잔액: ${account.getBalance()}원")
    account.report()                    // 안에서는 private 메서드를 쓸 수 있다
}

// ------------------------------------------------------------
// 예제 2. 커스텀 setter 로 값 걸러 내기
// ------------------------------------------------------------
fun h_exam2() {
    val t = Thermometer()

    t.celsius = 25.0
    println("${t.celsius}도 = ${t.fahrenheit}화씨")       // 25.0 = 77.0

    // 말이 안 되는 값을 넣으면 setter 가 걸러 낸다.
    t.celsius = -500.0
    println("-500 을 넣었지만 실제 값: ${t.celsius}")      // -273.15

    // 완전히 막는 대신 "들어올 수 있는 값만 들어오게" 하는 방식이다.
}

// ------------------------------------------------------------
// 예제 3. private set - 읽기는 공개, 쓰기는 비공개
// ------------------------------------------------------------
fun h_exam3() {
    val g = GameScore("홍길동")

    println("${g.playerName} / 점수 ${g.score} / 레벨 ${g.level}")   // 읽기는 자유

    // g.score = 999999                 // 컴파일 에러! setter 가 private

    // 점수는 정해진 방법으로만 오른다.
    g.addScore(50)
    println("점수 ${g.score} / 레벨 ${g.level}")      // 50 / 1

    g.addScore(120)
    println("점수 ${g.score} / 레벨 ${g.level}")      // 170 / 2

    g.addScore(200)
    println("점수 ${g.score} / 레벨 ${g.level}")      // 370 / 4

    // 점수와 레벨의 관계가 항상 지켜진다. 밖에서 점수만 몰래 바꿀 수 없기 때문이다.
}

// ------------------------------------------------------------
// 예제 4. protected 와 internal
// ------------------------------------------------------------
fun h_exam4() {
    val warrior = Warrior("전사")
    warrior.showHp()                    // 전사 HP: 100

    warrior.attack()                    // 자식은 부모의 protected 에 접근할 수 있다
    warrior.attack()
    warrior.showHp()                    // 전사 HP: 80

    // warrior.hp                       // 컴파일 에러! protected 는 밖에서 못 본다

    // internal 은 같은 모듈(이 프로젝트) 안에서는 자유롭게 쓸 수 있다.
    val helper = InternalHelper()
    helper.help()

    // 정리
    //   public    : 어디서나
    //   internal  : 같은 모듈 안에서만
    //   protected : 자기 자신과 자식만
    //   private   : 자기 자신만
}

fun main() {
    h_exam1()     // private - 감추고 통로만 열기
    h_exam2()     // 커스텀 setter 로 값 걸러 내기
    h_exam3()     // private set
    h_exam4()     // protected 와 internal
}
