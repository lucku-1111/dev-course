
// * abstract

// 1. 왜 필요한가
// D_polymorphism 의 Shape 를 떠올려 보자.
//
//   open class Shape(val shapeName: String) {
//       open fun area(): Double = 0.0      // 부모는 넓이를 구할 방법이 없다
//   }
//
// 문제가 두 가지 있다.
//   1) Shape("무언가") 처럼 실체 없는 도형 객체를 만들 수 있다.
//   2) 자식이 area() 재정의를 깜빡해도 컴파일이 되고, 넓이가 0.0 으로 나온다.
//
// abstract 를 쓰면 둘 다 막을 수 있다.

// 2. 사용법
/*
abstract class Shape(val name: String) {
    abstract fun area(): Double // 자식이 반드시 구현
    fun describe() { // 본문이 있는 메서드도 가질 수 있다.

    }
}
*/

// 3. 객체를 만들 수 없다.
// val v = Vehicle("무언가") // 컴파일에러!
// 실체를 만들 수 없다.

// 4. 추상 프로퍼티도 만들 수 있다.
/*
abstract class Vehicle {
    abstract val wheels: Int // 값 없이 선언만
}

class Car() : Vehicle() {
    override val wheels: Int = 4
}
 */

// 5. 정리 : 일반 클래스 / open 클래스 / 추상 클래스
//   class Vehicle          : 상속 불가. 객체 생성 가능.
//   open class Vehicle     : 상속 가능. 객체 생성 가능. 재정의는 선택.
//   abstract class Vehicle : 상속 가능. 객체 생성 불가. abstract 멤버는 재정의 필수.

abstract class Vehicle(val modelName: String) {

    abstract val wheels: Int            // 추상 프로퍼티. 값이 없다.

    abstract fun move()                 // 추상 메서드. 본문이 없다.

    // 본문이 있는 일반 메서드. 자식들이 그대로 물려받아 쓴다.
    fun info() {
        println("$modelName / 바퀴 ${wheels}개")
    }
}

class Car(modelName: String) : Vehicle(modelName) {
    override val wheels: Int = 4

    override fun move() {
        println("$modelName 이(가) 도로를 달립니다.")
    }
}

class Bicycle(modelName: String) : Vehicle(modelName) {
    override val wheels: Int = 2

    override fun move() {
        println("$modelName 이(가) 페달로 굴러갑니다.")
    }
}

class Motorcycle(modelName: String) : Vehicle(modelName) {
    override val wheels: Int = 2

    override fun move() {
        println("$modelName 이(가) 굉음을 내며 달립니다.")
    }

    // 자식만의 메서드도 얼마든지 추가할 수 있다.
    fun wheelie() {
        println("$modelName 이(가) 앞바퀴를 듭니다!")
    }
}

// ------------------------------------------------------------
// 예제 1. 추상 클래스 사용하기
// ------------------------------------------------------------
fun e_exam1() {
    // val v = Vehicle("무언가")        // 컴파일 에러! 추상 클래스는 객체를 만들 수 없다

    val car = Car("소나타")
    val bike = Bicycle("삼천리자전거")

    // 부모에 구현되어 있는 일반 메서드는 그대로 물려받는다.
    car.info()                          // 소나타 / 바퀴 4개
    bike.info()                         // 삼천리자전거 / 바퀴 2개

    // abstract 로 강제된 것은 자식마다 다르게 구현되어 있다.
    car.move()
    bike.move()
}

// ------------------------------------------------------------
// 예제 2. 강제의 효과 - 빠뜨리면 컴파일이 안 된다
// ------------------------------------------------------------
fun e_exam2() {
    // 아래처럼 abstract 멤버를 구현하지 않으면 그 자리에서 컴파일 에러가 난다.
    //
    //   class Truck(modelName: String) : Vehicle(modelName) {
    //       override val wheels: Int = 6
    //       // move() 를 구현하지 않았다!
    //   }
    //   -> Class 'Truck' is not abstract and does not implement abstract member 'move'
    //
    // open class 였다면 재정의를 깜빡해도 컴파일이 되고, 부모의 엉뚱한 기본 동작이 실행된다.
    // abstract 는 그 실수를 '실행 전'에 잡아 준다.

    val moto = Motorcycle("R1")
    moto.info()
    moto.move()
    moto.wheelie()                      // 자식만의 메서드
}

// ------------------------------------------------------------
// 예제 3. 추상 클래스와 다형성
// ------------------------------------------------------------
fun e_exam3() {
    // 부모 타입 하나로 여러 자식을 담는다. (D_polymorphism 에서 배운 것)
    val vehicles: List<Vehicle> = listOf(
        Car("소나타"),
        Bicycle("삼천리자전거"),
        Motorcycle("R1")
    )

    for (v in vehicles) {
        v.info()
        v.move()        // 실제 객체에 맞는 move() 가 실행된다
    }

    // 바퀴 개수 합계
    var totalWheels = 0
    for (v in vehicles) {
        totalWheels += v.wheels
    }
    println("바퀴 총 개수: $totalWheels")

    // 추상 클래스 덕분에 v.move() 가 반드시 제대로 구현되어 있음을 보장받는다.
}

fun main() {
    e_exam1()     // 추상 클래스 사용하기
    e_exam2()     // 강제의 효과
    e_exam3()     // 추상 클래스와 다형성
}











