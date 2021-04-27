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
        window.location.href = result.isAluno ? '/workouthelper/' : '/workouthelper/profissional/fichas/index.html';
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
    if (!fichas) return;

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

const makeRequestGetDetalhesTreino = async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const tipoTreino = urlParams.get('type');
    const idTreino = urlParams.get('treino');
    document.getElementById("nome-treino").innerText = "Treino " + tipoTreino;

    let exerciciosList = await makeRequestGet('/rltreinoexercico?treino=' + idTreino);
    if (!exerciciosList) return;
    document.getElementById("qtde-exercicios").innerText = exerciciosList.length + " exercícios";
    console.log(exerciciosList);

    for (const exercicios of exerciciosList) {
        const exercicio = exercicios.exercicio;
        if (!exercicio) continue;

        let elementTag = document.createElement("a");
        elementTag.href = '/workouthelper/detalhe-do-exercicio.html?id=' + exercicio.id;

        const element =
            `<li>
                <div>
                    <img src="header-musculacao-bt.png" alt="Img Musculação">
                </div>
                <div>
                    <h4>${exercicio.nome}</h4>
                    <h5>${exercicio.descricao}</h5>
                </div>
                <div>
                    <svg width="8" height="12" viewBox="0 0 8 12" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M1.41985 11L6 6L1.41985 1" stroke="black" stroke-width="1.5"/>
                    </svg>
                </div>
            </li>`;

        elementTag.innerHTML = element
        document.getElementById("list-exercicios").append(elementTag);
    }
}

const makeRequestGetExercicios = async () => {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    let exercicio = await makeRequestGet('/exercicio/' + id);
    console.log(exercicio);
    if (!exercicio) return;

    document.getElementById("nome-exercicio").innerText = exercicio.nome;
    document.getElementById("descricao-exercicio").innerText = exercicio.descricao;
    document.getElementById("recomendacao").innerText = "Recomendação: " + exercicio.recomendacao;
}

const makeRequestGetAvaliacao = async () => {
    let avaliacoes = await makeRequestGet('/avaliacao?aluno=' + localStorage.getItem("id"));
    console.log(avaliacoes)
    if (!avaliacoes) return;

    for (const avaliacao of avaliacoes) {
        let elementTag = document.createElement("tr");
        let medidas = avaliacao.medidas.explode(';');
        const element = `
            <td>${avaliacao.dataAvaliacao}</td>
            <td>${medidas[0]}cm</td>
            <td>${medidas[1]}cm</td>
            <td>${medidas[2]}cm</td>
            <td>${medidas[3]}cm</td>
            <td>${medidas[4]}%</td>
            <td>${avaliacao.peso}</td>
        `;
        elementTag.innerHTML = element;
        document.getElementById("tabela-avaliacoes").append(elementTag);
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