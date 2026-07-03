package com.example.spring.feignclient.client;

// * Feign Client 선언부
// - interface 라서 우리가 직접 구현하지 않는다.
// 즉 "다른 서버로 HTTP 요청을 보내는 코드"를 인터페이스 선언만으로 대신 만들어주는 것.

import org.springframework.cloud.openfeign.FeignClient;

// * @FeignClient
// - name : 이 클라이언트의 고유 이름(Bean 이름), 필수값
// - url : 호출할 대상 서버 주소
@FeignClient(name = "exampleClient", url = "${feign-api.url}")
public interface ExampleClient {
}
