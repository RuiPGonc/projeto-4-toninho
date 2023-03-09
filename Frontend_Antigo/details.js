const inputTitleEdit = document.getElementById('edit-title');
const editDetails = document.getElementById('edit-description');
const editDeadline = document.getElementById('edit-deadline');
const inputCatEdit = document.getElementById('edit-category');
const taskId = JSON.parse(sessionStorage.getItem('editIDTask'));


// EVENTOS ON LOAD
window.addEventListener('load', () => {
    // introduz o username do utilizador activo no cabeçalho
    var credentials=JSON.parse(sessionStorage.getItem('credentials'));
    document.getElementById('utilizadorAtivo').innerHTML = credentials.username || 'bem-vindo';
   
    // obtem detalhes da atividade seleccionada
    getTaskData(taskId);
})


// ESPECIFICAÇÃO R11 - obtem detalhes da atividade seleccionada
function getTaskData(taskId){

    let credentials=JSON.parse(sessionStorage.getItem('credentials'));

    fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/' + credentials.username + '/activities/details/' + taskId,
    {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'username':credentials.username,
            'password':credentials.password
        },
    }
    ). then(response => response.json())
    .then(response => fillDataFields(JSON.stringify(response)));
}


// preenche os campos do formulário com os dados obtidos da tarefa seleccionada
function fillDataFields(task){

    var taskData = JSON.parse(task);

    inputTitleEdit.value = taskData.title;
    editDetails.value = taskData.details;
    editDeadline.value = taskData.deadline;
    inputCatEdit.value = taskData.category;

    let dropButton = document.getElementById('edit-dropbtn');
    if (taskData.category == 'amarela') {
        dropButton.style.color = '#F2C12E';
    } else if (taskData.category == 'laranja') {
        dropButton.style.color = '#ED8B16';
    } else if (taskData.category == 'vermelha') {
        dropButton.style.color = '#A62B1F';
    }
}


// ESPECIFICAÇÃO R8 - edita tarefa, encontrada por id, de determinado utilizador
function editTask(e) {
    e.preventDefault();
    
    if (inputTitleEdit.value == "") {
        alert("O título da tarefa não pode ser deixado vazio");
    } else {

    let task = {
        'title': inputTitleEdit.value,
        'details': editDetails.value,
        'deadline': editDeadline.value,
        'category': inputCatEdit.value
    };

    let credentials=JSON.parse(sessionStorage.getItem('credentials'));

    fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/'+ credentials.username +'/activities/' + taskId,
    {
        method: 'POST',
        headers:
        {
            'Accept': '*/*',
            'Content-Type': 'application/json',
            'username':credentials.username,
            'password':credentials.password
        },
        body: JSON.stringify(task)
    }
    ).then(function (response) {
        if (response.status == 200) {
            alert('Tarefa editado com sucesso.');
            goHomePage();
        } else {
            alert('Ups! Something went wrong!')
        }
    })
}
}

// altera o texto do botão de submissão da pagina detalhes e pagina de Perfil de utilizador
function changeButton() {
    document.getElementById('saveBtn').classList.remove('disabled');
    // document.getElementById('btnEdit').classList.remove('disabled');
    document.getElementById('cancelBtn').innerHTML = 'cancelar';
}

// verifica se a data introduzida é anterior à do dia atual
function checkDate() {
    let dataIntroduzida = new Date(editDeadline.value);
    let today = new Date();
    if (dataIntroduzida.getTime() < today.getTime()) {
        alert("A data introduzida é anterior à data de hoje");
    }
}

//eventos on click para escolha da categoria / cor da tarefa
document.addEventListener("click", (e) => {

    let selectedItem = e.target;
    let dropButton = document.getElementById('edit-dropbtn');
    

    // comportamento do dropdown menu para escolha de cores/categorias
    if (selectedItem.classList.contains("fa-circle")) {
        if (selectedItem.classList.contains("dropYellow")) {
            dropButton.style.color = '#F2C12E';
            inputCatEdit.value = 'amarela';
            document.getElementById("myDropdown").classList.remove("show");
        } else if (selectedItem.classList.contains("dropOrange")) {
            dropButton.style.color = '#ED8B16';
            inputCatEdit.value = 'laranja';
            document.getElementById("myDropdown").classList.remove("show");
        } else if (selectedItem.classList.contains("dropRed")) {
            dropButton.style.color = '#A62B1F';
            inputCatEdit.value = 'vermelha';
            document.getElementById("myDropdown").classList.remove("show");
        }
    }
});

// função do dropdown
function dropFunction() {
    document.getElementById("myDropdown").classList.toggle("show"); 
}
