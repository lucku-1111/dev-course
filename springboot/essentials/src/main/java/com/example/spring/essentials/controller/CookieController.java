package com.example.spring.essentials.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// * 쿠키란?
// 쿠키는 '브라우저 쪽'에 저장되는 작은 이름-값 데이터 조각이다.
// HTTP는 무상태라 각각의 요청 서로 기억하지 못하는데,
// 쿠키를 쓰면 브라우저가 정보를 들고 다니며 서버에 매번 알려줄 수 있다.
// - 서버가 응답할 때 "이 값을 저장해 둬"하고 쿠키를 내려주면(Set-Cookie),
// 브라우저는 그 쿠키를 저장해 둔다.
// - 이후 브라우저는 같은 사이트로 요청할 때마다 저장된 쿠키를 '자동으로' 함께 보낸다.

// [세션과 쿠키의 관계 - 헷갈리기 쉬운 부분]
//   - 쿠키 : 실제 '데이터 자체'가 브라우저에 저장된다. (예: username=hong)
//   - 세션 : 실제 데이터는 '서버'에 있고, 브라우저에는 그 보관함을 여는
//     '열쇠(세션 ID)'만 쿠키로 저장된다.
//   - 즉 세션도 그 열쇠를 전달하기 위해 내부적으로 쿠키를 사용한다.
//     둘은 대립 관계가 아니라, 세션이 쿠키를 활용하는 관계다.
//   - 쿠키는 브라우저에 그대로 노출되므로, 비밀번호 같은 민감 정보는 담으면 안 된다.

// [참고 : 웹 스토리지 - Local Storage / Session Storage]
// 개발자 도구(Application 탭)를 보면 Cookie 옆에 Local Storage, Session Storage 가 있다.
// 이 둘은 이름만 비슷할 뿐, 쿠키나 서버 세션과는 전혀 다른 '브라우저 전용' 저장소다.
//   - 서버로 자동 전송되지 않는다. 오직 자바스크립트로만 읽고 쓸 수 있어,
//     우리가 위에서 한 request.getCookies() 같은 방식으로는 서버가 접근할 수 없다.
//     (쿠키는 매 요청마다 서버로 자동으로 실려 가지만, 웹 스토리지는 안 간다.)
//   - Local Storage   : 브라우저를 껐다 켜도 값이 계속 남는다. (직접 지울 때까지 유지)
//   - Session Storage : 탭을 닫으면 값이 사라진다. 탭마다 별개로 관리된다.
//     (서버의 HttpSession 과는 완전히 다른 것이니 이름에 속으면 안 된다.)
//   - 자바스크립트 사용 예 : localStorage.setItem("키", "값") / localStorage.getItem("키")
//   - 정리하면, 서버가 사용자를 기억해야 하면 '쿠키/세션'을,
//     브라우저에서 JS 로만 임시 데이터를 다루면 '웹 스토리지'를 쓴다.

@Controller
public class CookieController {

    @GetMapping("/set-cookie")
    public String setCookie() {
        return "set-cookie";
    }

    @PostMapping("/set-cookie")
    public String setCookieExc(
            @RequestParam String username,
            HttpServletResponse response,
            Model model
    ) {
        // 쿠키 생성
        Cookie cookie = new Cookie("username", username);

        // 유효기간
        cookie.setMaxAge(7 * 24 * 60 * 60); // 일주일

        // HttpOnly: 자바스크립트(document.cookie)로는 못 읽게 막는다. XSS 공격 방어
        cookie.setHttpOnly(true);

        // Path : 브라우저가 이 쿠키를 어떤 경로 요청에 보낼지 정한다.
        //        지정한 경로와 그 하위 경로 요청에만 쿠키가 실려 간다.
        //        "/" 는 모든 경로가 "/"로 시작하므로, 사이트 전체 요청에 항상 보낸다는 뜻이다.
        cookie.setPath("/");

        response.addCookie(cookie);
        model.addAttribute("message", "쿠키가 설정되었습니다.");

        return "result-cookie";
    }

    @GetMapping("/get-cookie")
    public String getCookie(
            HttpServletRequest request,
            Model model
    ) {
        Cookie[] cookies = request.getCookies();
        String username = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        if (username != null) {
            model.addAttribute("username", username);
            model.addAttribute("message", "쿠키에서 사용자 정보를 읽었습니다.");
        } else {
            model.addAttribute("message", "쿠키가 존재합니다.");
        }

        return "result-cookie";
    }

    // 쿠키 읽기 (@CookieValue 버전) : 위 getCookie() 와 결과는 같지만 훨씬 간결하다.
    // - @CookieValue("username") 을 쓰면, 스프링이 그 이름의 쿠키를 알아서 찾아
    //   매개변수 username 에 값을 넣어 준다. (DispatcherServlet 이 파라미터를 만들어 주는 단계다.)
    // - required = false : 해당 쿠키가 없어도 에러 없이 진행한다. 없으면 null 이 들어온다.
    //   (기본값은 true 라, 쿠키가 없으면 400 에러가 나므로 조회용에서는 false 로 두는 게 안전하다.)
    // - 없을 때 기본값을 주고 싶으면 defaultValue = "guest" 처럼 지정할 수도 있다.
    @GetMapping("/get-cookie2")
    public String getCookieByAnnotation(
            @CookieValue(value = "username", required = false) String username,
            Model model
    ) {
        if (username != null) {
            model.addAttribute("username", username);
            model.addAttribute("message", "쿠키에서 사용자 정보를 읽었습니다.");
        } else {
            model.addAttribute("message", "쿠키가 존재합니다.");
        }

        return "result-cookie";
    }

    @GetMapping("/delete-cookie")
    public String deleteCookie(
            HttpServletResponse response,
            Model model
    ) {
        Cookie cookie = new Cookie("username", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("message", "쿠키가 삭제되었습니다.");
        return "result-cookie";
    }
}
