function $(selector) {
    return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function initEvents() {
    const loginBtn = $(".btn.btn_mint.btn_login");
    loginBtn.addEventListener("click", registerJoinHandler);
}

function fetchManager({url, method, body, headers}) {
    fetch(url, {method, body, headers, credentials: "same-origin"})
        .then(response => {
            if(response.status == '200') {
                alert("로그인 성공");
                location.replace('/');
            }
            return response.json();
        })
        .then(result => {
            if(!result) return;
            console.log(result);
            alertErrorMessage(result.errors);
        });
}

function registerJoinHandler(evt) {
    evt.preventDefault();

    const memberId = $("#member_id").value;
    const password = $("#pwd").value;

    fetchManager({
        url: '/api/members/login',
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({memberId, password}),
    })
}

function alertErrorMessage(errors) {
    for (let error of errors) {
        alert(error.errorMessage);
    }
}