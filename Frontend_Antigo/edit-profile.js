var credentials = [];

// eventos on load
// procura o nome do utilizador e os detalhes da tarefa escolhida, colocando-os nos respetivos campos
window.addEventListener("load", () => {
  // obtem o elemento onde introduzir o nome do utilizador activo e guarda-o numa variavel
  credentials = JSON.parse(sessionStorage.getItem("credentials"));

  fetchUserInfo();
});


//Prenchimento dos inputs
function getUserInfo(user) {
  userInfo = JSON.parse(user);

  username = document.getElementById("usernameInput");
  username.setAttribute("value", credentials.username);
  username.classList.add("usernameBloqued");

  const password = document.getElementById("newPasswordInput");

  const firstName = document.getElementById("firstNameInput");
  firstName.setAttribute("value", userInfo.firstName);
  firstName.classList.add("editInfo");

  const lastName = document.getElementById("lastNameInput");
  lastName.setAttribute("value", userInfo.lastName);
  lastName.classList.add("editInfo");

  const email = document.getElementById("emailInput");
  email.setAttribute("value", userInfo.email);
  email.classList.add("editInfo");

  const phone = document.getElementById("phoneInput");
  phone.setAttribute("value", userInfo.phone);
  phone.classList.add("editInfo");

  const urlPhoto = document.getElementById("photoInput");
  urlPhoto.setAttribute("value", userInfo.photoUrl);
  urlPhoto.classList.add("editInfo");

  const pass = document.getElementById("newPasswordInput");
  pass.classList.add("newPasswordInput");
  const confirmPass = document.getElementById("confirmNewPasswordInput");
  pass.classList.add("confirmNewPasswordInput");
}


//OBTER DADOS DO USER
function fetchUserInfo() {

  fetch("http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/"+credentials.username, {
    method: "GET",
    headers: {
      Accept: "*/*",
      "Content-Type": "application/json",
      username: credentials.username,
      password: credentials.password,
    },
  })
    .then((response) => response.json())
    .then((response) => getUserInfo(JSON.stringify(response)));
}


// ESPECIFICAÇÃO R7 - editar perfil de utilizador
function goEditProfile(e) {
  e.preventDefault();
  let password = document.getElementById("newPasswordInput").value;
  let confirmpassord = document.getElementById("confirmNewPasswordInput").value;

  //Alteração da Password
  if (password == credentials.password) {
    window.alert("A password introduzida não pode ser igual à existente.");
    document.getElementById("confirmNewPasswordInput").value = "";
    document.getElementById("newPasswordInput").value = "";
  } else if (password !== confirmpassord) {
    window.alert("As passwords introduzidas não são iguais.");
    document.getElementById("confirmNewPasswordInput").value = "";
    document.getElementById("newPasswordInput").value = "";
  } else {
    if (password.trim() == "" && confirmpassord.trim() == "") {
      password = credentials.password;
    }
    //Enviar Fetch com info para enviar a password
    //------------------------------------------------------------
    if (password !== credentials.password) {
      var newCredentials = {
        username: credentials.username,
        password: password,
      };
     
      var jsonPassword = { 
        'password':password,
        'userId':'0',
        'userme':'0'
         };

      fetch(
        "http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/"+credentials.username+"/pass",
        {
          method: "POST",
          headers: {
            Accept: "*/*",
            "Content-Type": "application/json",
            username: credentials.username,
            password: credentials.password,
          },
          body: JSON.stringify(jsonPassword),
        }
      ).then(function (response) {
      
        if (response.status == 200) {
         
          sessionStorage.setItem("credentials", JSON.stringify(newCredentials));
          credentials = JSON.parse(sessionStorage.getItem("credentials")); // atualiza a variável Credenciais com o valor da LocalStorage
         
          editUserInformations();
        }
      });
    } else {
      editUserInformations();
    }
  }
}

//------------------------------------------
function editUserInformations() {
 
  let fistName = document.getElementById("firstNameInput").value;
  let lastName = document.getElementById("lastNameInput").value;
  let email = document.getElementById("emailInput").value;
  let phone = document.getElementById("phoneInput").value;
  let urlPhoto = document.getElementById("photoInput").value;

  let profileChanges = {
    firstName: fistName,
    lastName: lastName,
    email: email,
    phone: phone,
    photoUrl: urlPhoto,
  };
  // console.log(profileChanges)

  fetch(
    "http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/" + credentials.username +"/",
    {
      method: "POST",
      headers: {
        Accept: "*/*",
        "Content-Type": "application/json",
        username: credentials.username,
        password: credentials.password,
      },
      body: JSON.stringify(profileChanges),
    }
  ).then(function (response) {
    if (response.status == 200) {
      alert("Dados do utilizador editados com sucesso!");
      goHomePage();
    } else {
      alert("Edição negada!");
    }
  });
}

function goHomePage() {
  window.open("home.html", "_self");
}
