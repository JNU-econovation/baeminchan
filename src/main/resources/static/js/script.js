function $(selector) {
    return document.querySelector(selector);
}

function signUp() {
    const email = $('#email_id').value + '@' + $('#email_domain').value;
    const password = $('#pw1').value;
    const passwordForCheck = $('#pw2').value;
    const name = $('#name').value;
    const phoneNumber = $('#cell1').value + '-' + $('#cell2').value + '-' + $('#cell3').value;

    const headers = new Headers({
        'Accept': 'application/json;charset=UTF-8',
        'Content-Type': 'application/json;charset=UTF-8'
    });

    fetch('/member/sign-up', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify({
            'email': email,
            'password': password,
            'passwordForCheck': passwordForCheck,
            'name': name,
            'phoneNumber': phoneNumber
        })
    }).then((response) => {
        const data = response.text();
        console.log(data);

        return data.then(result => {
            return {
                'result': result,
                'status': response.status
            }
        })
    }).then(({result, status}) => {
        if (status >= 400) {
            result = JSON.parse(result);
            console.log('error 가 발생했네요 ', result.status);
            console.log(result);

            if (result.errors != null) {
                let log = "";

                for (let i = 0; i < result.errors.length; i++) {
                    log += result.errors[i] + "\n";
                }

                alert(log);
            } else {
                alert(result.message);
            }

        } else {
            redirectPage("/login");
        }
    }).catch(err => {
        console.log("oops..", err)
    })
}

function checkLoginForm() {
    const email = $('#member_id').value;
    const password = $('#pwd').value;

    const headers = new Headers({
        'Accept': 'application/json;charset=UTF-8',
        'Content-Type': 'application/json;charset=UTF-8'
    });

    fetch('/member/login', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify({
            'email': email,
            'password': password,
        })
    }).then((response) => {
        const data = response.text();
        console.log(data);

        return data.then(result => {
            console.log(result);

            return {
                'result': result,
                'status': response.status
            }
        })
    }).then(({result, status}) => {
        if (status >= 400) {
            result = JSON.parse(result);
            console.log('error 가 발생했네요 ', result);
            console.log(result);

            if (result.errors != null) {
                let log = "";

                for (let i = 0; i < result.errors.length; i++) {
                    log += result.errors[i] + "\n";
                }

                alert(log);
            } else {
                alert(result.message);
            }

        } else {
            redirectPage("/");
        }
    }).catch(err => {
        console.log("oops..", err)
    })
}

function redirectPage(path) {
    console.log("redirect to " + path);
    location.pathname = path;
}