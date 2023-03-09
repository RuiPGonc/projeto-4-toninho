var userId = sessionStorage.getItem('userViewerID');
var credentials=JSON.parse(sessionStorage.getItem('credentials'));

// EVENTOS ON LOAD
window.addEventListener('load', () => {

    // obtem detalhes do utilizador seleccionado
    if(credentials.username!=null){
    getUserProfile(userId);
    }
})

// ESPECIFICAÇÃO R5b - obter o perfil de utilizador da lista de utilizadores por id
function getUserProfile(userId) {

    let credentials=JSON.parse(sessionStorage.getItem('credentials'));
    
    fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/' + credentials.username + '/' + userId,
    {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'username':credentials.username,
            'password':credentials.password
        },
    }
  ). then(response => response.json())
.then(response => fillUserDataFields(JSON.stringify(response)));
}

function fillUserDataFields(user) {
    let selectedUser = JSON.parse(user);
    document.getElementById('profileFirstNameField').innerHTML = selectedUser.firstName;
    document.getElementById('profileLastNameField').innerHTML = selectedUser.lastName;
    document.getElementById('profileEmailField').innerHTML = selectedUser.email;
    document.getElementById('profilePhoneField').innerHTML = selectedUser.phone;

    let img = document.createElement('img');
    img.classList.add('profilePhoto');
    img.src = selectedUser.photoUrl;
    img.onerror= function(){
        img.src ="ProfileImage.png";
        };
    document.getElementById('profilePhotoField').appendChild(img);
}


document.addEventListener("click", (e) => {
    let selectedItem = e.target; 

    let buttonDiv = selectedItem.closest("div");
    if (buttonDiv.classList.contains('userProfileContainer')) {
        window.open('users-list.html', '_self');
    }
});