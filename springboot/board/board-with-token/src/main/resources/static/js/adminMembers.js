$(document).ready(() => {
    loadCurrentUser((info) => {
        if (info.role !== 'ROLE_ADMIN') {
            alert('관리자만 접근할 수 있습니다.');
            window.location.href = '/';
            return;
        }
        loadMembers();
    });
});

let loadMembers = () => {
    $.ajax({
        type: 'GET',
        url: '/api/members',
        success: (members) => renderMembers(members),
        error: (error) => {
            console.error('오류 발생:', error);
            alert('회원 목록을 불러오는데 실패했습니다.');
            window.location.href = '/';
        }
    });
};

let renderMembers = (members) => {
    const $content = $('#memberContent');
    $content.empty();

    if (members == null || members.length <= 0) {
        $content.append(
            `<tr>
                <td colspan="4" style="text-align: center;">회원이 존재하지 않습니다.</td>
            </tr>`
        );
        return;
    }

    members.forEach((member) => {
        $content.append(
            `
            <tr>
                <td>${member.id}</td>
                <td>${member.userId}</td>
                <td>${member.userName}</td>
                <td>${member.role}</td>
            </tr>
            `
        );
    });
};
