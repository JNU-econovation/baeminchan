function $(selector) {
    return document.querySelector(selector);
}

function fetchManager({ url, method, body, headers, callback }) {
    fetch(url, {
        method,
        body,
        headers,
        credentials: "same-origin"
    }).then((response) => {
        const data = response.json();
        console.log(data);

        return data.then(result =>  {
            return {
                'result' : result,
                'status' : response.status
            }
        })
    }).then( ({result, status}) => {
        if(status >= 400) {
            console.log('error 가 발생했네요 ', result.error);
            console.log(result);
        }else{
            callback(result)
        }
    }).catch(err => {
        console.log("oops..", err)
    })
}


function signUp() {
    const email = $('#email_id').value + '@' + $('#email_domain').value;
    const password = $('#pw1').value;
    const passwordForCheck = $('#pw2').value;
    const name = $('#name').value;
    const phoneNumber = $('#cell1').value + '-' + $('#cell2').value + '-' + $('#cell3').value;

    const signUpDTO = {
        email: email,
        password: password,
        passwordForCheck: passwordForCheck,
        name: name,
        phoneNumber: phoneNumber
    };

    console.log(signUpDTO);

    fetchManager({
        url: '/member/sign-up',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=UTF-8'},
        body: JSON.stringify({signUpDTO}),
        callback: redirectLoginForm
    })
}

function redirectLoginForm(data) {
    console.log(data);
    window.location = 'http://localhost:8080/login';
}

function checkLoginFrm(data) {

}