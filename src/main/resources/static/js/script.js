function $(selector) {
    return document.querySelector(selector);
}

function createCategory() {
    const title = $('#title').value;
    const parent = $('#parentId').value;

    console.log(title);
    console.log(parent);

    const headers = new Headers({
        'Accept': 'application/json;charset=UTF-8',
        'Content-Type': 'application/json;charset=UTF-8'
    });

    fetch('/admin/category/create', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify({
            'title': title,
            'parentId': parent
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
            result = JSON.parse(result);
            showCategory(result.id);
        }
    }).catch(err => {
        console.log("oops..", err)
    })
}

function updateCategory() {
    const id = $('#category-id').value;
    const title = $('#update-title').value;
    const parent = $('#update-parentId').value;

    console.log(id);
    console.log(title);
    console.log(parent);

    const headers = new Headers({
        'Accept': 'application/json;charset=UTF-8',
        'Content-Type': 'application/json;charset=UTF-8'
    });

    fetch('/admin/category/' + id + '/update', {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify({
            'title': title,
            'parentId': parent
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
        result = JSON.parse(result);
        if (status >= 400) {
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
            location.reload();
        }
    }).catch(err => {
        console.log("oops..", err)
    })
}

function deleteCategory() {
    const id = $('#delete-category-id').value;

    console.log(id);

    const headers = new Headers({
        'Accept': 'application/json;charset=UTF-8',
        'Content-Type': 'application/json;charset=UTF-8'
    });

    fetch('/admin/category/' + id + '/delete', {
        method: 'DELETE',
        headers: headers
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
            location.reload();
        }
    }).catch(err => {
        console.log("oops..", err)
    })
}

function showCategory(id) {
    const headers = new Headers({
        'Accept': 'application/json;charset=UTF-8',
        'Content-Type': 'application/json;charset=UTF-8'
    });

    fetch('/admin/category/' + id, {
        method: 'GET',
        headers: headers
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
                    log += result.errors[i].errorMessage + "\n";
                }

                alert(log);
            } else {
                alert(result.message);
            }

        } else {
            result = JSON.parse(result);

            // 왜 안되냐..
            // const html =
            //         "<h5>id : " + `${result.id}` + " 이름 : " + `${result.title}` + " 상위 카테고리: " + `${result.parent}` + " 하위 카테고리: " + `${result.children}` + "</h5>";
            //
            // $(".categoryList").append(html);

            location.reload();
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
                    log += result.errors[i].errorMessage + "\n";
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
                    log += result.errors[i].errorMessage + "\n";
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