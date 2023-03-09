var usersList = [];

// EVENTOS ON LOAD
window.addEventListener('load', () => {

    // obtem lista de utilizadores
    getUsersList();
})

// ESPECIFICAÇÃO R4 - obter lista de atividades de um determinado utilizador
function getUsersList(){

    let credentials=JSON.parse(sessionStorage.getItem('credentials'));

    fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/',
    {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'username':credentials.username,
            'password':credentials.password
        },
    }
). then(response => response.json())
.then(response => fillUsersList(JSON.stringify(response)));
}

function fillUsersList(allUsersList){
    let usersList = JSON.parse(allUsersList);
    // console.log(usersList);
    for (i = 0; i < usersList.length; i++) {
        printUser(usersList[i]);
    }
}

function printUser(user) {

    // cria o div que inclui as informações do utilizador
    let userDiv = document.createElement('div');
    userDiv.classList.add('userDiv');

    // atribui o ID do user ao div que o tem
    let divId = document.createAttribute('id');
    divId.value = user.userId;
    userDiv.setAttributeNode(divId);

    // cria a tabela para apresentação dos dados do utilizador
    let userBox = document.createElement('table');
    userBox.classList.add('userBox');
    userDiv.appendChild(userBox);

    let row = userBox.insertRow();

    let cellA = row.insertCell();
    cellA.classList.add('nameCell');

    let cellB = row.insertCell();
    cellB.classList.add('pictureCell');

    let img = document.createElement('img');
    img.classList.add('userPicture');
    img.src = user.photoUrl;
    img.onerror= function(){
        img.src ="ProfileImage.png";
        };

    cellA.innerHTML = user.firstName + " " + user.lastName;
    cellB.appendChild(img);

    document.getElementById('usersListContainer').appendChild(userDiv);
}


// acções dos botões / icones das caixas de apresentação de tarefas
document.addEventListener("click", (e) => {
    let selectedItem = e.target; 
    
    let usrDiv = selectedItem.closest('div');
    let usrDivId = usrDiv.id;

    sessionStorage.setItem('userViewerID', usrDivId);
    goUserProfilePage();
    
});


function goUserProfilePage(){
    window.open('user-profile.html', '_self');
}