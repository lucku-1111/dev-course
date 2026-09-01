
// 조건문(Conditional Statement)은 조건이 참인지 거짓인지에 따라
// 실행할 코드를 골라 주는 문법이다.

// * 기본 문법
//   if (조건) {
//       조건이 true 일 때 실행할 코드
//   } else {
//       조건이 false 일 때 실행할 코드
//   }
//   조건 자리에는 반드시 Boolean(true / false)이 오는 값이 들어가야 한다.
//   비교 연산자(> < >= <= == !=)와 논리 연산자(&& || !)로 만든 결과가 그대로 조건이 된다.

// * 조건이 여러 개일 때 - else if
//   if (조건1) { ... } else if (조건2) { ... } else { ... }
//   위에서부터 차례로 검사하다가 처음으로 true 가 되는 곳만 실행하고 빠져나온다.
//   그래서 조건을 쓰는 '순서'가 결과를 좌우한다.

// * 코틀린의 if 는 '표현식(Expression)'이다  <- 자바와 가장 큰 차이!
//   자바의 if 는 실행만 하는 문장(Statement)이라 값을 만들지 못한다.
//   그래서 자바에는 값을 고르기 위한 삼항 연산자(조건 ? A : B)가 따로 있다.
//   코틀린의 if 는 그 자체가 값을 만들어 내므로 삼항 연산자가 아예 없다.
//       val max = if (a > b) a else b
//   단, 값으로 쓸 때는 else 를 반드시 써야 한다. (값을 못 만드는 경우가 생기면 안 되므로)

// * 스마트 캐스트(Smart Cast)
//   if (값 is 자료형) 으로 검사하고 나면, 그 안에서는 코틀린이 알아서 그 자료형으로 취급해 준다.

// 두 수 중 큰 값을 돌려주는 함수 - if 가 값을 만들어 내므로 = 로 바로 연결할 수 있다.
fun maxOfTwo(a: Int, b: Int): Int = if (a > b) a else b

// 점수를 학점으로 바꾸는 함수 - else if 로 여러 갈래를 만든다.
fun getGrade(score: Int): String {
    if (score >= 90) {
        return "A"
    } else if (score >= 80) {
        return "B"
    } else if (score >= 70) {
        return "C"
    } else {
        return "F"
    }
}

// 위 함수를 if 표현식으로 다시 쓰면 return 을 한 번만 써도 된다.
fun getGrade2(score: Int): String = if (score >= 90) "A"
                                    else if (score >= 80) "B"
                                    else if (score >= 70) "C"
                                    else "F"

// ------------------------------------------------------------
// 예제 1. 가장 기본이 되는 if 와 if ~ else
// ------------------------------------------------------------
fun f_exam1() {
    val age = 25

    // 조건이 true 일 때만 중괄호 안이 실행된다.
    if (age >= 19) {
        println("성인입니다.")
    }

    // 조건이 false 이면 아무 일도 일어나지 않는다.
    if (age < 19) {
        println("미성년자입니다.")      // 실행되지 않는다
    }

    // else 를 붙이면 false 일 때 실행할 코드를 정할 수 있다. 둘 중 하나는 반드시 실행된다.
    if (age >= 19) {
        println("입장할 수 있습니다.")
    } else {
        println("입장할 수 없습니다.")
    }

    // 실행할 코드가 한 줄뿐이면 중괄호를 생략할 수 있다.
    if (age >= 19) println("중괄호를 생략한 if")

    // 다만 중괄호를 생략하면 어디까지가 if 인지 헷갈리기 쉬우니 되도록 중괄호를 쓰자.

    // 조건 자리에는 Boolean 이 오는 무엇이든 들어갈 수 있다.
    val isMember = true
    if (isMember) {
        println("회원입니다.")          // 변수 자체가 Boolean 이므로 그대로 조건이 된다
    }

    if (!isMember) {
        println("비회원입니다.")        // ! 로 뒤집어서 검사
    }
}

// ------------------------------------------------------------
// 예제 2. else if - 조건이 여러 개일 때
// ------------------------------------------------------------
fun f_exam2() {
    val score = 85

    if (score >= 90) {
        println("A 학점")
    } else if (score >= 80) {
        println("B 학점")              // 85 는 여기서 걸린다
    } else if (score >= 70) {
        println("C 학점")
    } else {
        println("F 학점")
    }

    // 중요! 위에서부터 검사하다가 '처음으로 true 인 곳'만 실행하고 끝난다.
    // 85 는 score >= 80 도 참이고 score >= 70 도 참이지만, 먼저 만난 80 쪽만 실행된다.

    // 그래서 순서를 잘못 쓰면 결과가 엉뚱해진다.
    val score2 = 95
    if (score2 >= 70) {
        println("잘못된 순서: C 학점")   // 95 인데 C 가 나와 버린다!
    } else if (score2 >= 90) {
        println("이 줄은 영원히 실행되지 않는다")
    }
    // 범위를 좁은 것(큰 값)부터 넓은 것(작은 값) 순서로 써야 한다.

    // 준비해 둔 함수로 확인해 보기
    println(getGrade(95))       // A
    println(getGrade(85))       // B
    println(getGrade(75))       // C
    println(getGrade(50))       // F
}

// ------------------------------------------------------------
// 예제 3. if 는 표현식이다 - 값을 만들어 낸다
// ------------------------------------------------------------
fun f_exam3() {
    val a = 10
    val b = 20

    // if 의 결과를 그대로 변수에 담을 수 있다.
    val max = if (a > b) a else b
    println("더 큰 값: $max")            // 20

    val min = if (a < b) a else b
    println("더 작은 값: $min")           // 10

    // 자바였다면 삼항 연산자를 써야 했다.  int max = (a > b) ? a : b;
    // 코틀린에는 삼항 연산자가 없다. if 가 그 역할을 대신하기 때문이다.

    // 값으로 쓸 때는 else 가 반드시 있어야 한다.
    // val bad = if (a > b) a          // 컴파일 에러! 'if' must have both main and 'else' branches
    // a > b 가 false 일 때 bad 에 넣을 값이 없어지기 때문이다.

    // 문자열 템플릿 안에서도 쓸 수 있다.
    val age = 25
    println("구분: ${if (age >= 19) "성인" else "미성년자"}")

    // 함수의 반환값으로 바로 쓰면 아주 간결해진다. (준비 구역의 maxOfTwo)
    println(maxOfTwo(3, 7))             // 7
    println(maxOfTwo(100, 50))          // 100
    println(getGrade2(85))              // B
}

// ------------------------------------------------------------
// 예제 4. 블록으로 된 if 표현식 - 마지막 줄이 곧 값이 된다
// ------------------------------------------------------------
fun f_exam4() {
    val score = 85

    // 중괄호를 쓰더라도, 각 블록의 '마지막 줄'이 그 블록의 값이 된다. (람다식과 같은 규칙)
    val message = if (score >= 60) {
        println("  합격 처리 중...")     // 중간 줄은 그냥 실행만 된다
        "합격입니다"                     // 마지막 줄 -> 이 값이 message 에 들어간다
    } else {
        println("  불합격 처리 중...")
        "불합격입니다"
    }
    println(message)                    // 합격입니다

    // 여러 줄에 걸쳐 계산한 결과를 값으로 돌려줄 때 유용하다.
    val bonus = if (score >= 90) {
        val base = 100
        base * 2                        // 200
    } else {
        val base = 100
        base / 2                        // 50
    }
    println("보너스: $bonus")            // 50
}

// ------------------------------------------------------------
// 예제 5. 조건 조합하기 - 논리 연산자, 범위 검사
// ------------------------------------------------------------
fun f_exam5() {
    val age = 25
    val hasTicket = true

    // && : 둘 다 참이어야 한다
    if (age >= 19 && hasTicket) {
        println("입장 완료")
    }

    // || : 하나라도 참이면 된다
    if (age < 8 || age >= 65) {
        println("할인 대상")
    } else {
        println("할인 대상이 아님")
    }

    // 조건을 중첩해서 쓸 수도 있다 (if 안의 if)
    if (age >= 19) {
        if (hasTicket) {
            println("성인 + 표 있음 -> 입장")
        } else {
            println("성인이지만 표가 없음")
        }
    }
    // 다만 중첩이 깊어지면 읽기 어려우니 && 로 묶는 편이 낫다.

    // in 을 쓰면 '범위 안에 있는지'를 간단히 검사할 수 있다.
    if (age in 20..29) {
        println("20대입니다")            // age >= 20 && age <= 29 와 같은 뜻
    }

    if (age !in 1..18) {
        println("미성년 범위가 아닙니다")
    }
}

// ------------------------------------------------------------
// 예제 6. 스마트 캐스트 - B_basic_type_2 에서 미뤄 둔 내용
// ------------------------------------------------------------
fun f_exam6() {
    val obj: Any = "나는 문자열이다"

    // is 로 검사하고 나면, 그 블록 안에서는 코틀린이 알아서 String 으로 취급해 준다.
    // 그래서 별도의 형 변환(as) 없이 String 의 기능을 바로 쓸 수 있다.
    if (obj is String) {
        println("문자열이고 길이는 ${obj.length}")     // obj 를 String 으로 자동 인식
        println("대문자로: ${obj.uppercase()}")
    }

    val num: Any = 100
    if (num is Int) {
        println("정수이고 1을 더하면 ${num + 1}")       // Int 로 자동 인식
    }

    // 스마트 캐스트가 없다면 이렇게 일일이 변환해야 했을 것이다.
    if (obj is String) {
        val text = obj as String        // 코틀린에서는 이 줄이 불필요하다 (경고가 뜬다)
        println(text.length)
    }
}

// ------------------------------------------------------------
// 예제 7. null 검사와 if
// ------------------------------------------------------------
fun f_exam7() {
    val name: String? = "홍길동"
    val nickname: String? = null

    // != null 로 검사하면 그 안에서는 null 이 아닌 것으로 확정되어 그냥 쓸 수 있다.
    // 이것도 스마트 캐스트다. (String? -> String)
    if (name != null) {
        println("이름 길이: ${name.length}")   // 세이프 콜(?.) 없이 바로 접근
    }

    if (nickname != null) {
        println("별명 길이: ${nickname.length}")
    } else {
        println("별명이 없습니다")
    }

    // B_basic_type_2 에서 배운 엘비스 연산자와 비교해 보자. 같은 일을 훨씬 짧게 쓴 것이다.
    val length1 = if (nickname != null) nickname.length else 0
    val length2 = nickname?.length ?: 0
    println("$length1 / $length2")          // 0 / 0  (결과가 같다)
}

fun main() {
    f_exam1()     // 기본 if 와 if ~ else
    f_exam2()     // else if - 조건이 여러 개일 때
    f_exam3()     // if 는 표현식이다 (값을 만든다)
    f_exam4()     // 블록으로 된 if 표현식
    f_exam5()     // 조건 조합하기 (&&, ||, in)
    f_exam6()     // 스마트 캐스트
    f_exam7()     // null 검사와 if
}

