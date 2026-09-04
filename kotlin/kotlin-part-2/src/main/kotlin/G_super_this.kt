
// * this/super

// this  : 자기 자신(이 객체)을 가리킨다.
// super : 부모 클래스를 가리킨다.
// 둘 다 "누구의 것인지"가 헷갈릴 때 콕 집어 주는 역할을 한다.

// 1. this - 프로퍼티와 매개변수 이름이 같을 때
//   class User(name: String) {
//       val name: String
//       init {
//           this.name = name     // this.name 은 프로퍼티, 그냥 name 은 매개변수
//       }
//   }

// 2. this - 자기 자신을 돌려주기 (메서드 체이닝)
//   fun add(topping: String): Pizza {
//       ...
//       return this          // 자기 자신을 돌려준다
//   }
//   pizza.add("치즈").add("페퍼로니").add("올리브")     <- 점을 이어서 쓸 수 있다

// 3. this - 생성자에서 다른 생성자 부르기
//   constructor(title: String) : this(title, 10000)

// 4. super - 부모의 메서드/프로퍼티 부르기
//   override fun work() {
//       super.work()         // 부모의 work() 를 먼저 실행하고
//       println("추가 동작")   // 내 동작을 덧붙인다
//   }

// 5. super - 부모 생성자 부르기
// * 주 생성자가 있을 때 : 클래스 선언부에서 부모 생성자를 호출한다.
//   class Manager(name: String) : Employee(name)
//
// * 주 생성자가 없을 때 : 부 생성자에서 super(...) 로 호출한다.
//   class Intern : Employee {
//       constructor(name: String) : super(name)
//   }

// 6. super<타입> - 어느 부모인지 지정하기
// 인터페이스는 여러 개를 구현할 수 있어서, 같은 이름의 기본 구현이 겹칠 수 있다.
// 이때는 꺾쇠로 어느 쪽 것인지 지정해야 한다. 지정하지 않으면 컴파일 에러다.
//   super<Walkable>.move()
//   super<Swimmable>.move()

// 1) 프로퍼티와 매개변수 이름이 같은 경우
class User(name: String, age: Int) {
    val name: String
    val age: Int

    init {
        this.name = name        // 왼쪽은 프로퍼티, 오른쪽은 매개변수
        this.age = age
    }

    fun printInfo() {
        println("$name / ${age}살")
    }
}

// 2) 자기 자신을 돌려주는 메서드 (메서드 체이닝)
class Pizza {
    var toppings: String = ""

    fun add(topping: String): Pizza {
        toppings += "$topping "
        return this             // 자기 자신을 돌려준다
    }

    fun print() {
        println("토핑: $toppings")
    }
}

// 3) super 로 부모의 것 부르기
open class Employee(val name: String) {

    open val role: String = "사원"

    open fun work() {
        println("$name 이(가) 일을 합니다.")
    }
}

class Manager(name: String) : Employee(name) {

    override val role: String = "관리자"

    override fun work() {
        super.work()            // 부모의 work() 를 먼저 실행
        println("$name 이(가) 팀을 관리합니다.")
        println("  내 역할: $role / 부모의 역할: ${super.role}")
    }
}

// 4) 주 생성자 없이 부 생성자에서 super(...) 부르기
class Intern : Employee {

    constructor(name: String) : super(name) {
        println("  [생성] 인턴 $name 등록")
    }

    override fun work() {
        println("$name 이(가) 일을 배웁니다.")
    }
}

// 5) 같은 이름의 기본 구현이 겹치는 인터페이스
interface Walkable {
    fun move() {
        println("걸어서 이동합니다.")
    }
}

interface Swimmable {
    fun move() {
        println("헤엄쳐서 이동합니다.")
    }
}

class Duck : Walkable, Swimmable {
    // move() 가 양쪽에 다 있으므로 반드시 재정의해야 한다. (안 하면 컴파일 에러)
    override fun move() {
        super<Walkable>.move()      // 어느 쪽 것인지 지정
        super<Swimmable>.move()
        println("오리는 둘 다 할 수 있습니다.")
    }
}

// ------------------------------------------------------------
// 예제 1. this - 이름이 겹칠 때
// ------------------------------------------------------------
fun g_exam1() {
    val u = User("홍길동", 20)
    u.printInfo()                   // 홍길동 / 20살

    // 만약 init 에서 this 를 빼고 name = name 이라고 썼다면
    // 매개변수에 매개변수를 넣는 꼴이라 프로퍼티는 초기화되지 않는다.

    // 참고) 코틀린에서는 보통 이렇게 쓴다. this 를 쓸 일 자체가 사라진다.
    //   class User(val name: String, val age: Int)
}

// ------------------------------------------------------------
// 예제 2. this - 메서드 체이닝
// ------------------------------------------------------------
fun g_exam2() {
    val p = Pizza()

    // add() 가 자기 자신을 돌려주므로 점을 이어서 쓸 수 있다.
    p.add("치즈").add("페퍼로니").add("올리브")
    p.print()                       // 토핑: 치즈 페퍼로니 올리브

    // 한 줄로도 쓸 수 있다.
    Pizza().add("불고기").add("고구마").print()
}

// ------------------------------------------------------------
// 예제 3. super - 부모의 메서드와 프로퍼티
// ------------------------------------------------------------
fun g_exam3() {
    val e = Employee("김사원")
    e.work()                        // 김사원 이(가) 일을 합니다.

    val m = Manager("박팀장")
    m.work()
    // 박팀장 이(가) 일을 합니다.        <- super.work() 로 부른 부모의 동작
    // 박팀장 이(가) 팀을 관리합니다.
    //   내 역할: 관리자 / 부모의 역할: 사원

    // 재정의해서 덮어썼어도, super 를 쓰면 부모의 원래 값과 동작을 그대로 볼 수 있다.
    println(m.role)                 // 관리자  (밖에서는 재정의된 값만 보인다)
}

// ------------------------------------------------------------
// 예제 4. super - 부모 생성자 부르기
// ------------------------------------------------------------
fun g_exam4() {
    // Manager 는 주 생성자에서 부모 생성자를 부른다.  : Employee(name)
    val m = Manager("박팀장")
    println(m.name)                 // 박팀장  (부모가 만든 프로퍼티)

    // Intern 은 주 생성자가 없어서 부 생성자에서 super(name) 으로 부른다.
    val i = Intern("이인턴")
    println(i.name)                 // 이인턴
    i.work()

    // 정리
    //   this(...)  -> 같은 클래스의 다른 생성자
    //   super(...) -> 부모 클래스의 생성자
}

// ------------------------------------------------------------
// 예제 5. super<타입> - 어느 부모인지 지정하기
// ------------------------------------------------------------
fun g_exam5() {
    val duck = Duck()
    duck.move()
    // 걸어서 이동합니다.
    // 헤엄쳐서 이동합니다.
    // 오리는 둘 다 할 수 있습니다.

    // Walkable 과 Swimmable 에 똑같이 move() 가 있으므로
    // Duck 이 재정의하지 않으면 "어느 것을 쓸지 모르겠다"며 컴파일 에러가 난다.
    // super<Walkable>.move() 처럼 꺾쇠로 지정해 주어야 한다.
}

fun main() {
    g_exam1()     // this - 이름이 겹칠 때
    g_exam2()     // this - 메서드 체이닝
    g_exam3()     // super - 부모의 메서드와 프로퍼티
    g_exam4()     // super - 부모 생성자 부르기
    g_exam5()     // super<타입> - 어느 부모인지 지정하기
}
