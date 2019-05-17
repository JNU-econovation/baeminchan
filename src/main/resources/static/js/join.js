function $(selector) {
    return document.querySelector(selector);
}

document.addEventListener("DOMContentLoaded", () => {
    initEvents();
})

function initEvents() {
    const joinBtn = $(".btn.btn_mint.btn_big");
    joinBtn.addEventListener("click", registerJoinHandler);
}

function fetchManager({url, method, body, headers}) {
    fetch(url, {method, body, headers, credentials: "same-origin"})
        .then(response => {
            if(response.status == '201') {
                alert("회원가입이 정상적으로 처리됐습니다.");
                location.replace('login');
            }
            return response.json();
        })
        .then(result => {
            if(!result) return;
            console.log(result);
            addErrorMessage(result.errors);
        });
}

function registerJoinHandler(evt) {
    evt.preventDefault();

    const emailId = $("#email_id").value;
    const emailDomain = $("#email_domain").value;
    const password = $("#pw1").value;
    const password2 = $("#pw2").value;
    const name = $("#name").value;
    const cell1 = $("#cell1").value;
    const cell2 = $("#cell2").value;
    const cell3 = $("#cell3").value;

    fetchManager({
        url: '/api/members',
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({emailId, emailDomain, password, password2, name, cell1, cell2, cell3}),
    })
}

function goNext(element, length, target) {
    if(element.value.length == length) {
        $("#" + target).focus();
    }
}

function removeErrorMessage() {
    $('#email_caution').textContent = "";
    $('#password_caution').textContent = "";
    $('#validPassword_caution').textContent = "";
    $('#name_caution').textContent = "";
    $('#cell_caution').textContent = "";
}

function addErrorMessage(errors) {
    if(!errors){
        alert(errors);
        return;
    }
    removeErrorMessage();

    for(let error of errors) {
        let target = error.fieldName + '_caution';
        if(target.startsWith('cell')) target = 'cell_caution';
        if(target.startsWith('email')) target = 'email_caution';
        $('#' + target).textContent = error.errorMessage;
    }
}
