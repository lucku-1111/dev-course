import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

// 서버에서 상품 이름을 가져오는 셈 친다. 0.5초가 걸린다.
suspend fun fetchName(id: Int): String {
    delay(500)
    return "상품$id"
}

// 서버에서 가격을 가져오는 셈 친다.
suspend fun fetchPrice(id: Int): Int {
    delay(500)
    return id * 1000
}

fun ex2() = runBlocking {
    println("A")

    launch {
        delay(1000)
        println("B")
    }

    println("C")
}

fun ex3() = runBlocking {
    val time = measureTimeMillis {
        val a = fetchName(1)
        val b = fetchName(2)
        val c = fetchName(3)
        println("$a / $b / $c")
    }
    println("걸린 시간: ${time}ms")
}

fun ex4() = runBlocking {
    val time = measureTimeMillis {
        val a = async { fetchName(1) }
        val b = async { fetchName(2) }
        val c = async { fetchName(3) }
        println("${a.await()} / ${b.await()} / ${c.await()}")
    }
    println("걸린 시간: ${time}ms")
}

fun ex4Mistake() = runBlocking {
    val time = measureTimeMillis {
        val a = async { fetchName(1) }.await()
        val b = async { fetchName(2) }.await()
        val c = async { fetchName(3) }.await()
        println("$a / $b / $c")
    }
    println("걸린 시간(실수 버전): ${time}ms")
}

fun ex5() = runBlocking {
    val time = measureTimeMillis {
        val names = (1..10).map { id -> async { fetchName(id) } }
            .awaitAll()
        println(names)
    }
    println("걸린 시간: ${time}ms")
}

suspend fun fetchProduct(id: Int): String = coroutineScope {
    val name = async { fetchName(id) }
    val price = async { fetchPrice(id) }
    "${name.await()} (${price.await()}원)"
}

fun ex6() = runBlocking {
    val time = measureTimeMillis { println(fetchProduct(1)) }
    println("걸린 시간: ${time}ms")
}

suspend fun fetchSlow(): String {
    delay(3000)
    return "느린 응답"
}

fun ex7() = runBlocking {
    val job = launch {
        repeat(10) { i ->
            println("  작업 중 $i")
            delay(200)
        }
        println("  이 줄은 실행되지 않는다")
    }

    delay(500)
    job.cancelAndJoin()
    println("취소 완료")

    val result = withTimeoutOrNull(1000) { fetchSlow() }
    println("결과: $result")

    val ok = withTimeoutOrNull(1000) { fetchName(1) }
    println("여유 있을 때: $ok")
}

fun fetchNameBlocking(id: Int): String {
    Thread.sleep(500)
    return "상품$id"
}

fun ex8() = runBlocking {
    val time = measureTimeMillis {
        val names = (1..10).map { id -> async { fetchNameBlocking(id) } }.awaitAll()
        println(names)
    }
    println("걸린 시간: ${time}ms")
}

fun ex7Blocking() = runBlocking {
    val job = launch {
        repeat(10) { i ->
            println("  작업 중 $i")
            Thread.sleep(200)
        }
        println("  이 줄도 실행된다")
    }
    delay(500)
    job.cancelAndJoin()
    println("취소 완료")
}

fun ex9() = runBlocking {
    try {
        coroutineScope {
            launch {
                delay(1000)
                println("  느린 작업 완료")
            }
            launch {
                delay(100)
                throw RuntimeException("일부러 낸 오류")
            }
        }
    } catch (e: Exception) {
        println("예외를 받았다: ${e.message}")
    }
}

fun main() {
    // 1. Suspend function 'suspend fun delay(timeMillis: Long): Unit' can only be called from a coroutine or another suspend function.
    //    delay()는 kotlinx.coroutines의 suspend 함수라서, 일반 함수 안에서는 바로 호출할 수 없다. 코루틴 안이거나 다른 suspend 함수 안에서만 호출 가능.
    //    Thread.sleep()는 컴파일이 된다. suspend 함수가 아니기 때문. 대신 그동안 스레드를 통째로 붙잡는다.

    ex2()
    // 2. A -> C -> B
    //    launch는 코루틴 스코프 안에서만 호출이 가능함.
    //    C가 B보다 먼저 나오는 이유: launch는 띄우기만 하고 기다리지 않는다. delay(1000)에서 멈추고 바깥 코드가 먼저 실행됨.
    //    그래도 B가 나오는 이유: runBlocking이 자식 코루틴이 다 끝날 때까지 기다려 줌.
    //    runBlocking을 지우면 안 되는 이유: launch, delay 둘 다 코루틴 스코프 안에서만 쓸 수 있는데 그 스코프를 runBlocking이 만들어 줌.

    ex3()
    // 3. 약 1500ms (0.5초 x 3). suspend는 "멈췄다 갈 수 있다"는 표시일 뿐 동시 실행을 만들어 주지 않는다.
    //    fetchName을 그냥 순서대로 부르면 한 줄이 끝나야 다음 줄이 시작된다.

    ex4()
    // 4. 약 500ms. async는 결과 대신 Deferred(상자)를 즉시 돌려준다.
    //    상자 3개를 먼저 다 만들어 두고(async) 나중에 다 여는 것(await)이 핵심.
    ex4Mistake()
    //    async는 결과를 기다리지 않고 그대로 반환하여 await를 통해 언제 결과를 처리할 것인지(함수의 작동이 끝날 때까지 기다림)를 결정한다.
    //    그래서 async { }.await()와 같이 쓰면 비동기처럼 보이지만 동기 코드를 작성한 것과 마찬가지인 것이다.(이로 인해 약 1500ms 시간이 걸림)

    ex5()

    ex6()
    // 6. suspend fun 안에서 async를 바로 쓰면 에러
    //    async는 CoroutineScope의 확장 함수라 this가 CoroutineScope여야 하는데 일반 suspend fun 안엔 그게 없음.
    //    coroutineScope { }로 감싸면 코루틴 스코프가 생겨서 해결됨.
    //    여기서 runBlocking을 쓰면 안 됨
    //    -> runBlocking은 코루틴 스코프 밖에서 코루틴을 호출하기 위한 경우에만 사용한다. 그렇지 않으면 블로킹되어 코루틴의 의미가 사라짐.

    ex7()
    // 7. 작업 중 0, 1, 2까지만 찍히고 취소됨. withTimeoutOrNull은 시간이 지나면 null을 반환하면서 내부 코루틴을 실제로 취소함.
    //    취소는 협조적이다. delay 같은 suspend 함수가 취소 여부를 스스로 확인해 주기 때문에 멈추는 것.

    ex8()
    // 8. Thread.sleep()을 호출하면 delay와 달리 스레드의 제어권을 반납하지 않기 때문에 약 5000ms의 시간이 소모된다.
    ex7Blocking()
    //    Thread.sleep()은 강제로 밖에서 끊지 않는 한 제어권이 없다.

    ex9()
    // 9. "느린 작업 완료"는 안 찍히고 "예외를 받았다: 일부러 낸 오류"만 찍힘.
    //    형제 코루틴 하나가 예외를 던지면 coroutineScope가 나머지 형제까지 취소하고 예외를 위로 전파함.
    //    형제를 살려두고 싶으면 coroutineScope 대신 supervisorScope.
}