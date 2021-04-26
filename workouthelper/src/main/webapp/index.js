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
        localStorage.setItem("isAluno", result.isAluno)
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
        console.log(e);
        // alert("Erro ao fazer request");
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
        console.log(e);
        // alert("Erro ao fazer request");
        return false;
    })
}

const makeRequestEditUser = async () => {
    const requestObj = getFormValues();
    const response = await makeRequest('/usuario/' + localStorage.getItem('id'), requestObj, 'PUT')

    if (response) window.location.href = '/workouthelper/';
}

makeRequestGetTreinos = async () => {
    const url = '/fichatreino?id=' + 2;
    const fichas = await makeRequestGet(url);
    if(!fichas) return;
    
    const ficha = fichas.pop();
    const treinos = [ficha.treinoA, ficha.treinoB, ficha.treinoC];

    if (!treinos) return;
    
    for (let [i, treino] of treinos.entries()) {
        if (!treino) continue;

        let stringTreino;
        if (i == 0) stringTreino = "A";
        if (i == 1) stringTreino = "B";
        if (i == 2) stringTreino = "C";

        let exercicios = await makeRequestGet('/rltreinoexercico?treino=' + treino.id);
        let elementTag = document.createElement("div");
        let element = `<a href="detalhe-do-treino.html?treino=${treino.id}&type=${stringTreino}">
                                <div class="box-treino-de-hoje">
                                    <div class="row">
                                        <div class="col">
                                            <div class="texto-infos-box-treino">
                                                <h4>Treino ${stringTreino} - ${treino.grupamento}</h4>
                                                <h5>${exercicios.length}</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </a>`;

        elementTag.innerHTML = element
        document.getElementById("list-treinos").append(elementTag);
    }
}

const init = () => {
    if (!localStorage.getItem('auth') && !window.location.href.includes('login')) {
        window.location.href = '/workouthelper/login.html';
        return;
    }

    const elementAluno = document.getElementById('nome-aluno');
    if (elementAluno) elementAluno.innerText = localStorage.getItem('name');
}

init();