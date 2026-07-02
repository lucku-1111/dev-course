package com.example.spring.essentials.controller;

import org.springframework.web.bind.annotation.RestController;

// * 디스패처 서블릿(DispatcherServlet)
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

@RestController
public class FilterController {

}
