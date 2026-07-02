package com.example.spring.essentials.controller;

import org.springframework.web.bind.annotation.RestController;

// * 디스패처 서블릿(DispatcherServlet) - 요청 처리의 심장
// 스프링 MVC로 들어오는 '모든' HTTP 요청은 먼저 DispatcherServlet 하나를 거친다.
// 그래서 이 서블릿을 '프론트 컨트롤러'라고 부른다.(프론트 컨트롤러 패턴)
// 개발자가 만들지 않아도 스프링부트가 자동 구성으로 미리 등록해준다.

// 요청 하나가 들어와 응답이 나가기까지 흐름은 다음과 같다.
// 1. 브라우저 요청이 DispatcherServlet에 도착한다.
// 2. HandlerMapping에게 "이 URL(/login)을 처리할 컨트롤러가 누구지?"라고 묻는다.
// 3. HandlerAdapter를 통해 실제 컨트롤러 메서드를 호출한다.
// 이때 파라미터(@RequestParam, HttpSession 등)를 알맞게 만들어 넣어준다.
// 4. 컨트롤러가 값을 반환하면, 그 반환값을 어떻게 처리할지 갈린다.
//      - @Controller + 뷰 이름   -> ViewResolver 가 templates/이름.html 을 찾는다.
//      - @RestController/@ResponseBody -> HttpMessageConverter 가 데이터(문자열/JSON)로 변환한다.
// 5. 최종 결과(HTML 또는 데이터)를 응답으로 만들어 브라우저에 돌려준다.
// 핵심은, 우리가 @GetMapping 메서드만 작성하면 그 앞뒤의 '분배와 변환'은
// DispatcherServlet 이 정해진 순서대로 대신 처리해 준다는 점이다.
// 아래의 @Controller / @RestController 차이도, 결국 4번 단계에서
// DispatcherServlet 이 반환값을 뷰로 볼지 데이터로 볼지를 가르는 이야기다.

// * @Controller 와 @RestController의 차이
// 둘 다 웹 요청을 받아 처리하는 컨트롤러지만, 메서드가 반환하는 String을 '어떻게 해석하느냐'가 다르다.
// - @Controller : 반환하는 String을 '뷰(view)의 이름'으로 본다.
// 그래서 SessionController, CookieController 처럼 HTML 페이지를 보여 줄 때 사용한다.
// - @RestController : 반환하는 String(또는 객체)을 '데이터 그 자체'로 본다.
// return "Hello World!"는 뷰를 찾지 않고 그 글자를 그대로 응답 본문에 보여 줄 때 사용한다.
// 객체를 반환하면 JSON으로 변환해준다. 그래서 REST API를 만들 때 쓴다.
// - 사실 @RestController 는 @Controller + @ResponseBody 를 합친 것이다.
// @Controller 에서도 메서드에 @ResponseBody 를 붙이면 데이터를 그대로 반환할 수 있다.
// 즉 @RestController 는 "이 클래스의 모든 메서드는 데이터를 반환한다"는 선언인 셈이다.

//   - 정리 : 화면(HTML)을 보여 주려면 @Controller,
//     데이터(JSON/문자열)를 내려 주려면 @RestController 를 쓴다.

// * 필터
@RestController
public class FilterController {
}
