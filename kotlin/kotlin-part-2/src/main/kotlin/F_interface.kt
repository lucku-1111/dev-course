
// * 인터페이스

// 1. 사용법
// 상속과 구현 모두 콜론(:) 하나로 쓴다. 즉 extends/implements가 없다.
// 부모 클래스와 인어페이스를 함께 쓸 때는 부모 클래스를 먼저 적는다.
// class A : Parent(), Printable, Loggable

// 2. 여러 개를 동시에 구현할 수 있다.

// 3. 자바 인터페이스와 다른 점
// * 프로퍼티를 선언할 수 있다. (자바는 상수만 가능)
//   interface Loggable {
//       val logTag: String            // 값 없이 선언만. 구현하는 쪽이 채운다.
//   }
//
// * 본문이 있는 메서드를 그냥 쓸 수 있다. (자바 8 의 default 메서드에 해당하지만 키워드가 없다)
//   interface Printable {
//       fun printInfo()
//       fun printTwice() {            // 기본 구현. 필요하면 구현하는 쪽에서 재정의한다.
//           printInfo(); printInfo()
//       }
//   }
// * 단, 상태(값)를 저장할 수는 없다. 프로퍼티에 초깃값을 줄 수 없고 getter 만 만들 수 있다.

// 4. 추상 클래스와 인터페이스 - 무엇을 쓸까?
//                      추상 클래스              인터페이스
//   개수               하나만 상속               여러 개 구현 가능
//   생성자             있다                      없다
//   상태(값) 저장       가능 (프로퍼티에 값 저장)   불가 (getter 만)
//   의미               "~는 ~이다" (is-a)        "~는 ~을 할 수 있다" (can-do)
//   예                 Car 는 Vehicle 이다       Report 는 출력할 수 있다
//
// * 상태나 공통 구현을 물려주고 싶다  -> 추상 클래스
// * 기능 규약만 강제하고, 여러 개를 동시에 갖추게 하고 싶다 -> 인터페이스

interface Printable {
    fun printInfo()

    // 기본 구현이 있는 메서드. 구현하는 쪽에서 그대로 써도 되고 재정의해도 된다.
    fun printTwice() {
        printInfo()
        printInfo()
    }
}

interface Loggable {

    val logTag: String // 프로퍼티 선언 가능

    fun log(message: String) {
        println("[$logTag] $message")
    }

}

class Report(val title: String) : Printable, Loggable {

    override fun printInfo() {
        println("보고서 : $title")
    }

    override val logTag: String = "REPORTD"

}

class Notice(val content: String) : Printable {

    override fun printInfo() {
        println("공지: $content")
    }

    // 기본 구현을 재정의할 수도 있다.
    override fun printTwice() {
        println("공지는 한 번만 출력합니다.")
        printInfo()
    }
}

// 부모 클래스 상속 + 인터페이스 구현을 함께 하기
open class Document(val docName: String) {
    fun open() {
        println("$docName 을(를) 엽니다.")
    }
}

class Contract(docName: String) : Document(docName), Printable, Loggable {

    override fun printInfo() {
        println("계약서: $docName")
    }

    override val logTag: String = "CONTRACT"
}

// ------------------------------------------------------------
// 예제 1. 인터페이스 구현하기
// ------------------------------------------------------------
fun f_exam1() {
    val report = Report("월간 보고서")
    report.printInfo()                  // 보고서: 월간 보고서

    // 인터페이스에 기본 구현이 있는 메서드는 따로 만들지 않아도 쓸 수 있다.
    report.printTwice()                 // 두 번 출력된다

    // 재정의한 쪽은 다르게 동작한다.
    val notice = Notice("휴무 안내")
    notice.printTwice()                 // 공지는 한 번만 출력합니다. / 공지: 휴무 안내
}

// ------------------------------------------------------------
// 예제 2. 프로퍼티를 가진 인터페이스
// ------------------------------------------------------------
fun f_exam2() {
    val report = Report("월간 보고서")

    // logTag 는 인터페이스가 선언만 하고, Report 가 "REPORT" 로 채웠다.
    println(report.logTag)              // REPORT

    // log() 는 인터페이스에 이미 구현되어 있다. 그 안에서 logTag 를 쓴다.
    report.log("작성 완료")              // [REPORT] 작성 완료

    val contract = Contract("임대차 계약서")
    contract.log("서명 대기")            // [CONTRACT] 서명 대기
}

// ------------------------------------------------------------
// 예제 3. 다중 구현 - 인터페이스만의 장점
// ------------------------------------------------------------
fun f_exam3() {
    val report = Report("월간 보고서")

    // 하나의 객체를 여러 타입으로 다룰 수 있다.
    val p: Printable = report
    p.printInfo()

    val l: Loggable = report
    l.log("전송함")

    println(report is Printable)        // true
    println(report is Loggable)         // true

    // Contract 는 클래스 상속 + 인터페이스 2개를 한꺼번에 갖췄다.
    val contract = Contract("임대차 계약서")
    contract.open()                     // Document 에서 물려받은 것
    contract.printInfo()                // Printable 규약
    contract.log("보관")                 // Loggable 규약
}

// ------------------------------------------------------------
// 예제 4. 인터페이스를 이용한 다형성
// ------------------------------------------------------------
fun f_exam4() {
    // 서로 상속 관계가 전혀 없는 클래스들을, 같은 규약을 갖췄다는 이유로 함께 다룬다.
    // 추상 클래스로는 할 수 없는 일이다. (Report, Notice, Contract 는 부모가 제각각이다)
    val items: List<Printable> = listOf(
        Report("월간 보고서"),
        Notice("휴무 안내"),
        Contract("임대차 계약서")
    )

    for (item in items) {
        item.printInfo()                // 각 객체에 맞는 구현이 실행된다
    }

    // Loggable 을 함께 갖춘 것만 골라 기록을 남기기
    for (item in items) {
        if (item is Loggable) {         // is 로 검사하면 스마트 캐스트가 된다
            item.log("처리 완료")
        }
    }
}

fun main() {
    f_exam1()     // 인터페이스 구현하기
    f_exam2()     // 프로퍼티를 가진 인터페이스
    f_exam3()     // 다중 구현
    f_exam4()     // 인터페이스를 이용한 다형성
}
