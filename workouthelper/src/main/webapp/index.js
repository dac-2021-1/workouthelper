const logout = () => {
    localStorage.clear();
    window.location.href = '/workouthelper/login.html';
}

const getFormValues = () => {
    const elements = document.querySelector('form').elements;
    console.log(elements);
    var obj = {};
    for (var i = 0; i < elements.length; i++) {
        var item = elements.item(i);
        console.log(item);
        if (item.localName == 'select' && !item.value) {
            for (let i of item.selectedOptions) {
                if (i.value) item.value += item.value ? `,${i.value}` : i.value;
            }
        }
        if (item.value && !obj[item.name]) obj[item.name] = item.value;
        else if (item.value && obj[item.name]) obj[item.name] = obj[item.name] + ';' + item.value;
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
        localStorage.setItem("idUsuario", result.idUsuario)
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
    const response = await makeRequest('/usuario/' + localStorage.getItem('idUsuario'), requestObj, 'PUT')

    if (response) window.location.href = '/workouthelper/';
}

const makeRequestGetFichas = async () => {
    const isAluno = localStorage.getItem("isAluno");
    const id = localStorage.getItem("id")
    const userType = isAluno && isAluno !== 'false' ? 'aluno' : 'professor';
    let url = `/fichatreino?${userType}=${id}`;

    // const url = '/fichatreino?id=' + 2;
    return await makeRequestGet(url);
}

makeRequestGetTreinos = async () => {
    const fichas = await makeRequestGetFichas();
    if (!fichas) return;
    console.log(fichas);
    const ficha = fichas.pop();
    const treinos = [ficha.treinoA, ficha.treinoB, ficha.treinoC];
    console.log(treinos);
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

const makeRequestGetAllExercicios = async (id = null) => {
    id = id ? id : '';
    const url = `/exercicio/${id}`;
    return await makeRequestGet(url);
}

const makeRequestGetExercicios = async () => {
    const urlParams = new URLSearchParams(window.location.search);
    let exercicio = await makeRequestGetAllExercicios(urlParams.get('id'));
    console.log(exercicio);
    if (!exercicio) return;

    document.getElementById("nome-exercicio").innerText = exercicio.nome;
    document.getElementById("descricao-exercicio").innerText = exercicio.descricao;
    document.getElementById("recomendacao").innerText = "Recomendação: " + exercicio.recomendacao;
}

const makeRequestGetAvaliacao = async () => {
    const isAluno = localStorage.getItem("isAluno");
    const userType = isAluno && isAluno !== 'false' ? 'aluno' : 'professor'
    const url = `/avaliacao?${userType}=${localStorage.getItem("id")}`
    console.log(url);
    let avaliacoes = await makeRequestGet(url);
    console.log(avaliacoes)
    if (!avaliacoes) return;

    for (const avaliacao of avaliacoes) {
        let elementTag = document.createElement("tr");
        let medidas = avaliacao.medidas.split(';');
        let element = `
            <td>${avaliacao.dataAvaliacao}</td>
            <td>${medidas[0]}cm</td>
            <td>${medidas[1]}cm</td>
            <td>${medidas[2]}cm</td>
            <td>${medidas[3]}cm</td>
            <td>${medidas[4]}cm</td>
            <td>${medidas[5]}%</td>
            <td>${avaliacao.peso}kg</td>
        `;
        if (userType !== 'aluno') element = `<td>${avaliacao.idAluno.id}</td>${element}`;
        elementTag.innerHTML = element;
        document.getElementById("tabela-avaliacoes").append(elementTag);
    }
}

const makeRequestGetFichasProfessor = async () => {
    const fichas = await makeRequestGetFichas();
    if (!fichas) return;
    console.log(fichas);
    for (const ficha of fichas) {
        let elementTag = document.createElement("tr");
        let treinoC = ficha.treinoC ? ficha.treinoC.grupamento : '--';
        const element = `
            <td>${ficha.idAluno.id}</td>
            <td>${ficha.dataInicio}</td>
            <td>${ficha.dataFinal}</td>
            <td>${ficha.treinoA.grupamento}</td>
            <td>${ficha.treinoB.grupamento}</td>
            <td>${treinoC}</td>
        `;
        elementTag.innerHTML = element;
        document.getElementById("tabela-fichas").append(elementTag);
    }
}

const makeRequestGetExerciciosProfessor = async () => {
    const exercicios = await makeRequestGetAllExercicios();
    console.log(exercicios);
    if (!exercicios) return;

    for (const exercicio of exercicios) {
        let elementTag = document.createElement("tr");
        const element = `
            <td>${exercicio.nome}</td>
            <td>${exercicio.descricao}</td>
            <td>${exercicio.link_video || '--'}</td>
            <td>${exercicio.recomendacao || '--'}</td>
        `;
        elementTag.innerHTML = element;
        document.getElementById("tabela-exercicios").append(elementTag);
    }
}

const makeRequestGetTreinosProfessor = async () => {
    let treinos = await makeRequestGet('/treino');
    console.log(treinos)
    if (!treinos) return;

    let treino_exercicio = await makeRequestGet('/rltreinoexercico');
    console.log(treino_exercicio);
    if (!treino_exercicio) return;

    for (const treino of treinos) {
        let elementTag = document.createElement("tr");
        let exercicios = treino_exercicio.filter(te => te.treino.id == treino.id).map(ex => ex.exercicio.nome);

        const element = `
            <td>${treino.grupamento}</td>
            <td>${exercicios.join(', ') || '--'}</td>
        `;
        elementTag.innerHTML = element;
        document.getElementById("tabela-treinos").append(elementTag);
    }
}

const makeRequestGetAlunos = async () => {
    const usuarios = await makeRequestGet('/usuario');
    console.log(usuarios);
    if (!usuarios) return;

    const alunos = usuarios.filter(u => u.aluno && u.aluno.id);
    console.log(alunos);
    for (const aluno of alunos) {
        let elementTag = document.createElement("option");
        elementTag.text = aluno.nome;
        elementTag.setAttribute("value", aluno.aluno.id);
        document.getElementById("select-aluno-form").append(elementTag);
    }
    //$('select').selectpicker();
}

const makeRequestGetExerciciosToTreino = async () => {
    const exercicios = await makeRequestGet('/exercicio');
    console.log(exercicios);
    if (!exercicios) return;

    for (const exercicio of exercicios) {
        let elementTag = document.createElement("option");
        elementTag.text = exercicio.nome;
        elementTag.setAttribute("value", exercicio.id);
        console.log(elementTag);
        document.getElementById("select-exercicio-form").append(elementTag);
    }
    //$('select').selectpicker();
}

const makeRequestGetTreinosToFicha = async () => {
    const treinos = await makeRequestGet('/treino');
    console.log(treinos);
    if (!treinos) return;

    for (const treino of treinos) {
        let elementTag = document.createElement("option");
        elementTag.text = treino.grupamento;
        elementTag.setAttribute("value", treino.id);
        let elementTagB = elementTag.cloneNode(true);
        let elementTagC = elementTag.cloneNode(true);
        document.getElementById("select-treinoA-form").append(elementTag);
        document.getElementById("select-treinoB-form").append(elementTagB);
        document.getElementById("select-treinoC-form").append(elementTagC);
    }
}

const makeRequestCreateAvaliacao = async () => {
    const requestObj = getFormValues();
    console.log(requestObj);
    const obj = {
        dataAvaliacao: requestObj.data,
        medidas: requestObj.medidas,
        peso: requestObj.peso,
        idAluno: {
            id: requestObj.aluno
        },
        idProfessor: {
            id: localStorage.getItem("id")
        }
    }
    console.log(obj);
    const response = await makeRequest('/avaliacao/', obj, 'POST')
    console.log(response);
    if (response) window.location.href = '/workouthelper/profissional/avaliacao-professor/index.html';
}

const makeRequestCreateExercicios = async () => {
    const requestObj = getFormValues();
    console.log(requestObj);
    const response = await makeRequest('/exercicio/', requestObj, 'POST')
    console.log(response);
    if (response) window.location.href = '/workouthelper/profissional/exercicios/index.html';
}

const makeRequestCreateTreinos = async () => {
    const requestObj = getFormValues();
    console.log(requestObj);
    const requestTreino = {
        grupamento: requestObj.grupamento
    }
    const response = await makeRequest('/treino/', requestTreino, 'POST')
    console.log(response);
    if (response && response.id) {
        const requestTreinoExercicio = {
            serie: requestObj.serie,
            repeticao: requestObj.repeticao,
            intervalo: requestObj.intervalo,
            exercicio: { id: requestObj.exercicio },
            treino: { id: response.id }
        }
        const response2 = await makeRequest('/rltreinoexercico/', requestTreinoExercicio, 'POST')
        console.log(response2);
        window.location.href = '/workouthelper/profissional/treinos/index.html';
    }
}

const makeRequestCreateFichas = async () => {
    const requestObj = getFormValues();
    
    requestObj.idAluno = {
        id: requestObj.idAluno
    }
    
    requestObj.idProfessor = {
        id: localStorage.getItem("id")
    }

    requestObj.treinoA = {
        id: requestObj.treinoA
    }

    requestObj.treinoB = {
        id: requestObj.treinoB
    }

    requestObj.treinoC = {
        id: requestObj.treinoC
    }

    console.log(requestObj);
    const response = await makeRequest('/fichatreino/', requestObj, 'POST')
    console.log(response);
    if (response) window.location.href = '/workouthelper/profissional/fichas/index.html';
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