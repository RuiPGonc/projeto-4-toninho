const toDoList = document.getElementById('to-do-list');
const newTaskDeadline = document.getElementById('newTaskDeadline');
const newTaskCategory = document.getElementById('categoryBtn');
var taskList = [];

window.addEventListener('load', () => {
    // introduz o username do utilizador activo no cabeçalho
    var credentials=JSON.parse(sessionStorage.getItem("credentials"));
    
    // carrega lista de tarefas on load
    if(credentials.username!=null){
    getUserTasks();
    }
})


// ESPECIFICAÇÃO R6 - obter lista de atividades de um determinado utilizador
function getUserTasks(){

    let credentials=JSON.parse(sessionStorage.getItem('credentials'));

    fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/' + credentials.username + '/activities',
    {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'username':credentials.username,
            'password':credentials.password
        },
    }
). then(response => response.json())
.then(response => fillUserTaskList(JSON.stringify(response)));
}


//ordenar a lista de tarefas
function fillUserTaskList(taskUserList){
    taskList = JSON.parse(taskUserList);
    
    // ordena a lista de tarefas
    taskList.sort((a,b) => a.finishTime - b.finishTime);

    for (i = 0; i < taskList.length; i++) {
        printTask(taskList[i]);
    }
}


// ADDTASK(R9) - ADICIONA NOVA TAREFA AO UTILIZADOR
function addTask(){

    if (document.getElementById('newTaskTitle').value == "") {
        alert("O título da tarefa não pode ser deixado vazio");
    } else {

    let task = {
        'title': document.getElementById('newTaskTitle').value,
        'details': document.getElementById('newTaskDescription').value,
        'deadline': document.getElementById('newTaskDeadline').value,
        'category': document.getElementById('categoryBtn').getAttribute('name')
    };

    let credentials=JSON.parse(sessionStorage.getItem('credentials'));

    fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/'+credentials.username+'/activities',
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
            // alert('Congratulations! A new task was added to the user!');
            printTask(task);
        } else {
            alert('Ups! Algo de errado aconteceu!')
        }
    })
}
}

// PRINT TASK
// coloca lista de tarefas do utilizador na homepage
function printTask (task) {

    //cria a div, atribui classe
    const taskDiv = document.createElement("div");  
    taskDiv.classList.add("toDoListItem");       
    
    //cria id, atribui a div
    const divID = document.createAttribute("id");
    divID.value = task.id;
    taskDiv.setAttributeNode(divID)
    
    //cria div para informações da tarefa 
    const infoDiv = document.createElement("div"); 
    infoDiv.classList.add("toDoListItemInfo");       
    taskDiv.appendChild(infoDiv); 

    //cria checkbox, atribui classe, add na div
    const finishTask = document.createElement("input");  
    finishTask.type = 'checkbox';
    finishTask.classList.add("finishTask");              
    infoDiv.appendChild(finishTask);

    //cria titulo da tarefa, add na div
    const taskTitle = document.createElement("h3");
    taskTitle.innerText = task.title;
    infoDiv.appendChild(taskTitle);

    //cria botão de edição, atribui classe, add na div
    const editButton = document.createElement("button");
    editButton.setAttribute('title', '');
    editButton.classList.add("editTask");
    editButton.innerHTML = '<i class="fa-solid fa-magnifying-glass"></i>';
    infoDiv.appendChild(editButton);

    //cria botão de apagar, atribui classe, add na div
    const deleteButton = document.createElement("button");
    deleteButton.setAttribute('title', '');
    deleteButton.classList.add("removeTask");
    deleteButton.innerHTML = '<i class="fa-solid fa-trash"></i>';
    infoDiv.appendChild(deleteButton);

    //cria div para o calendario
    const calendarDiv = document.createElement("div");
    calendarDiv.classList.add("toDoListItemCalendar");  
    taskDiv.appendChild(calendarDiv); 

    //cria o ícone do calendario, add na div
    const calendarIcon = document.createElement("i");
    calendarIcon.setAttribute('class', "fa-solid fa-calendar-days");
    calendarDiv.appendChild(calendarIcon);

    //cria o prazo, add na div
    const taskDetails = document.createElement("p");
    taskDetails.innerText = task.deadline;
    calendarDiv.appendChild(taskDetails);

    //cria icone com a cor/categoria da tarefa
    const taskColor = document.createElement("i");
    taskColor.setAttribute('class', "fa-solid fa-circle");
    taskColor.classList.add('category-icon');
    if (task.category == 'amarela') {
        taskColor.style.color = '#F2C12E';
    } else if (task.category == 'laranja') {
        taskColor.style.color = '#ED8B16';
    } else if (task.category == 'vermelha') {
        taskColor.style.color = '#A62B1F';
    }
    calendarDiv.appendChild(taskColor);

    //adiciona a div criada na div pai 
    toDoList.appendChild(taskDiv); 
    
    //se a task estiver feita, risca ela e marca o checkbox
    console.log(task.title +"  "+task.done)
    if (task.done =='yes'){
        taskDiv.classList.add("finish");
        finishTask.checked = true;
    } else { //se a task não tiver feita, imprime ela no topo da lista
        const parent = taskDiv.parentElement;
        parent.insertBefore(taskDiv, parent.firstChild);
    }
}


// EVENTOS CLICK
// mostrar / esconder calendário
document.addEventListener("click", (e) => {
    let selectedItem = e.target; 

    if (selectedItem.classList.contains ("new-task-calendar")){
        newTaskDeadline.classList.toggle("hiddenCalendar");
    }
});

// acções do dropdown para escolha de cores / categoria
document.addEventListener("click", (e) => {
    let selectedItem = e.target; 

    if (selectedItem.classList.contains("fa-circle")) {
        if (selectedItem.classList.contains("dropYellow")) {
            newTaskCategory.style.color = '#F2C12E';
            newTaskCategory.setAttribute('name', 'amarela');
            document.getElementById("myDropdown").classList.remove("show");
        } else if (selectedItem.classList.contains("dropOrange")) {
            newTaskCategory.style.color = '#ED8B16';
            newTaskCategory.setAttribute('name', 'laranja');
            document.getElementById("myDropdown").classList.remove("show");
        } else if (selectedItem.classList.contains("dropRed")) {
            newTaskCategory.style.color = '#A62B1F';
            newTaskCategory.setAttribute('name', 'vermelha');
            document.getElementById("myDropdown").classList.remove("show");
        }
    }
});
    
// acções dos botões / icones das caixas de apresentação de tarefas
document.addEventListener("click", (e) => {
    let selectedItem = e.target; 

    const buttonDiv = selectedItem.closest("div"); //guarda a div mais próxima do item clickado
    const selectedDiv = buttonDiv.parentElement;

    // acção de revover tarefa
    if (selectedItem.classList.contains ("removeTask")){
        deleteTask(selectedDiv.id);
        selectedDiv.remove();     
        
    // acção de riscar tarefa
    } else if (selectedItem.classList.contains ("finishTask")){
        changeTaskStatus (selectedDiv.id); //chama o metodo de mudar o status
        selectedDiv.classList.toggle("finish"); //adiciona ou apaga a classe "finish" na div

        if(selectedDiv.classList.contains("finish")){ //se tiver feita
            toDoList.appendChild(selectedDiv); //adiciona na div, vai para o final
        } else { //se estiver por fazer
            const parent = selectedDiv.parentElement; //busca o elemento pai da div
            parent.insertBefore(selectedDiv, parent.firstChild); //adiciona a div no topo da lista ou seja Before do FirstChield
        }
    } 
    // ir para página editar tarefa
    else if (selectedItem.classList.contains ("editTask")) 
     { //se o item clicado for da classe "editTask"
        goDetailsPage();// window.location.href = "details.html"; //manda para a pagina de edição
        sessionStorage.setItem('editIDTask', selectedDiv.id);}
});
    

// FUNÇÕES
// SET STATUS(R8) - EDITA TAREFA (altera estado da tarefa)
function changeTaskStatus(taskId){

    let credentials=JSON.parse(sessionStorage.getItem('credentials'));

    fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/'+ credentials.username +'/activities/' + taskId+'/state',
    {
        method: 'POST',
        headers:
        {
            'Accept': '*/*',
            'Content-Type': 'application/json',
            'username':credentials.username,
            'password':credentials.password,
            'stateChange':'1',
        },
       
    }
    ).then(function (response) {
        if (response.status == 200) {
        } else {
            alert('Ups! Algo de errado aconteceu!')
        }
    })
}


// R10 - ELIMINA TAREFA DE DETERMINADO UTILIZADOR
function deleteTask(taskId){

    let credentials=JSON.parse(sessionStorage.getItem('credentials'));

    fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/' + credentials.username + '/activities/' + taskId,
    {
        method: 'DELETE',
        headers:
        {
            'Accept': '*/*',
            'Content-Type': 'application/json',
            'username':credentials.username,
            'password':credentials.password
        },
    }
    ).then(function (response) {
        if (response.status == 200) {
        } else {
            alert('Ups! Algo de errado aconteceu!')
        }
    })

}

// função do dropdown
function dropFunction() {
    document.getElementById("myDropdown").classList.toggle("show"); 
}

function checkDateHome() {
    let dataIntroduzida = new Date(newTaskDeadline.value);
    let today = new Date();
    if (dataIntroduzida.getTime() < today.getTime()) {
        alert("A data introduzida é anterior à data de hoje");
    }
}
