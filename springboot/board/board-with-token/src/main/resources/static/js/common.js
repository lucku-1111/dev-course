// 모든 페이지에서 공통으로 쓰는 인증 헬퍼
//   - Access Token 은 서버 세션이 아니라 브라우저의 localStorage 에 보관한다.
//   - 모든 ajax 요청에 Authorization 헤더를 자동으로 붙이고,
//     401(토큰 만료)을 받으면 refresh 를 한 번 시도한 뒤, 그래도 실패하면 로그인 페이지로 보낸다.

const ACCESS_TOKEN_KEY = 'accessToken';

let getAccessToken = () => localStorage.getItem(ACCESS_TOKEN_KEY);
let setAccessToken = (token) => localStorage.setItem(ACCESS_TOKEN_KEY, token);
let clearAccessToken = () => localStorage.removeItem(ACCESS_TOKEN_KEY);

let goToLogin = () => {
    clearAccessToken();
    window.location.href = '/members/login';
};

// 로그인/회원가입/토큰갱신 자체는 401이 나도 refresh 를 다시 시도할 대상이 아니다.
const SKIP_REFRESH_URLS = ['/api/members/login', '/api/members/join', '/api/tokens/refresh'];

// 모든 ajax 요청에 Authorization 헤더를 자동으로 붙인다.
$.ajaxSetup({
    beforeSend: (xhr) => {
        const token = getAccessToken();
        if (token) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        }
    }
});

// 401 응답을 전역에서 감지해 refresh 후 원래 요청을 한 번 재시도한다.
$(document).ajaxError((event, jqXHR, ajaxSettings) => {
    if (jqXHR.status !== 401) return;
    if (SKIP_REFRESH_URLS.some((url) => ajaxSettings.url.indexOf(url) !== -1)) return;
    if (ajaxSettings.__retriedAfterRefresh) {
        // 재시도까지 했는데도 401이면 refresh 도 이미 소용없다는 뜻
        goToLogin();
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/api/tokens/refresh',
        success: (response) => {
            setAccessToken(response.accessToken);
            ajaxSettings.__retriedAfterRefresh = true;
            $.ajax(ajaxSettings); // 원래 요청 재시도
        },
        error: () => goToLogin()
    });
});

// 로그인한 사용자 본인 정보를 조회해 콜백에 넘긴다. 토큰이 아예 없으면 바로 로그인 페이지로 보낸다.
let loadCurrentUser = (onSuccess) => {
    if (!getAccessToken()) {
        goToLogin();
        return;
    }

    $.ajax({
        type: 'GET',
        url: '/api/members/info',
        success: onSuccess,
        error: () => goToLogin()
    });
};

// 로그아웃 - 서버에 refresh 쿠키 삭제를 요청하고, 로컬 토큰도 지운 뒤 로그인 페이지로 이동한다.
let logout = () => {
    $.ajax({
        type: 'POST',
        url: '/api/members/logout',
        complete: () => {
            clearAccessToken();
            window.location.href = '/members/login';
        }
    });
};
