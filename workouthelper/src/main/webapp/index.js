const logout = () => {
    localStorage.clear();
    window.location.href = '/workouthelper/login.html';
}

const getFormValues = () => {
    const elements = document.querySelector('form').elements;

    var obj = {};
    for (var i = 0; i < elements.length; i++) {
        var item = elements.item(i);
        if (item.value) obj[item.name] = item.value;
    }

    return obj;
}

const makeRequestLogin = () => {
    const headers = new Headers({
        'content-type': 'application/json'
    });
    const requestObj = getFormValues();

    fetch('/workouthelper/resources/login', {
        method: "POST",
        body: JSON.stringify(requestObj),
        headers
    }).then(res => {
        console.log(res);
        return res.json();
    }).then(result => {
        console.log(result)
        localStorage.setItem("auth", result.token)
        localStorage.setItem("name", result.name)
        localStorage.setItem("id", result.id)
        window.location.href = '/workouthelper/';
    }).catch(e => {
        console.log(e);
        alert("Erro ao fazer login");
    })
}

const makeRequest = async (url, data, method) => {
    const headers = new Headers({
        'content-type': 'application/json',
        'auth': localStorage.getItem('auth')
    });

    return await fetch('/workouthelper/resources' + url, {
        method,
        body: JSON.stringify(data),
        headers
    }).then(res => {
        return res.json();
    }).then(result => {
        console.log(result);
        return result;
    }).catch(e => {
        alert("Erro ao fazer request");
        return false;
    })
}

const makeRequestGet = async (url) => {
    const headers = new Headers({
        'content-type': 'application/json',
        'auth': localStorage.getItem('auth')
    });

    return await fetch('/workouthelper/resources' + url, {
        headers
    }).then(res => {
        console.log(res);
        return res.json();
    }).then(result => {
        console.log(result);
        return result;
    }).catch(e => {
        alert("Erro ao fazer request");
        return false;
    })
}

const makeRequestEditUser = async () => {
    const requestObj = getFormValues();
    const response = await makeRequest('/usuario/' + localStorage.getItem('id'), requestObj, 'PUT')

    if (response) window.location.href = '/workouthelper/';
}

const init = () => {
    if (!localStorage.getItem('auth') && !window.location.href.includes('login')) {
        window.location.href = '/workouthelper/login.html';
        return;
    }

    const elementAluno = document.getElementById('nome-aluno');
    if (elementAluno) elementAluno.innerText = localStorage.getItem('name');

    makeRequestGet('/treino/2');
    makeRequestGet('/fichatreino');
}

init();