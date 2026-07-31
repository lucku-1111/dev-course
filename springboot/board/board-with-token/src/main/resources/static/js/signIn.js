
$(document).ready(() => {

    $('#signin').click(() => {

        let userId = $('#user_id').val();
        let password = $('#password').val();

        let formData = {
           username : userId,
           password : password
        }


        $.ajax({
            type: 'POST',
            url: '/api/members/login', // 서버의 엔드포인트 URL
            data: JSON.stringify(formData), // 데이터를 JSON 형식으로 변환
            contentType: 'application/json; charset=utf-8', // 전송 데이터의 타입
            dataType: 'json', // 서버에서 받을 데이터의 타입
            success: (response) => {
                if (response.loggedIn) {
                    setAccessToken(response.accessToken);
                    window.location.href = response.url;
                    return;
                }
                alert(response.message);
            },
            error: (error) => {
                // 실패 시 실행될 콜백 함수
                console.error('오류 발생:', error);
                const message = error.responseJSON && error.responseJSON.message
                    ? error.responseJSON.message
                    : '로그인 중 오류가 발생했습니다.';
                alert(message);
            }
        });

    });



});