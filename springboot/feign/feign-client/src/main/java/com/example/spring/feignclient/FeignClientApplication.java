package com.example.spring.feignclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// * Feign Client
// Feign은 "다른 서버의 API를 호출하는 코드"를 인터페이스 선언만으로 자동 생성해주는 HTTP 클라이언트이다.
// 원래는 RestTemplate/WebClient로 URL/헤더/파라미터를 직접 조립해야 하지만,
// Feign은 "어디로 어떤 요청을 보낼지"만 인터페이스에 선언하면 구현은 Spring이 대신 만들어준다.
// 요약: HTTP호출을 그냥 자바 메서드 호출처럼 쓰게 해주는 도구

// * 외부(다른 서버)와 통신하는 기술들
// - RestTemplate : 동기 HTTP 클라이언트. 가장 오래된 방식이며 현재는 유지보수 모드(레거시).
// - WebClient    : 비동기/논블로킹 HTTP 클라이언트. 리액티브(WebFlux) 스택용.
// - RestClient   : Spring 6.1+ 신규 동기 HTTP 클라이언트. RestTemplate의 현대적 대체제.
// - Feign        : 인터페이스 선언만으로 호출하는 선언형 HTTP 클라이언트 (← 이 프로젝트에서 사용).
// - gRPC         : HTTP/2 + Protobuf(바이너리) 기반 RPC. 주로 MSA 내부 서비스 간 고속 통신에 사용.
// - 메시지 큐(Kafka, RabbitMQ) : 요청-응답이 아닌 비동기 메시징(이벤트 발행/구독) 방식.
// 위쪽 4개는 REST(HTTP+JSON) 계열이고, gRPC와 메시지 큐는 통신 방식 자체가 다르다.

// * @EnableFeignClients
// @FeignClient 인터페이스들을 찾아 구현 객체(Bean)로 만들라고 지시하는 "스위치" -> 없으면 Feign이 아예 동작하지 않는다.
@EnableFeignClients
@SpringBootApplication
public class FeignClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignClientApplication.class, args);
    }

}
