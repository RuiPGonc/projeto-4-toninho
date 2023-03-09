const loggedInPages = [ "/paj-proj2/details.html",  "/paj-proj2/edit-profile.html", "/paj-proj2/home.html",  "/paj-proj2/users-list.html"];

// EVENTOS ON LOAD
window.addEventListener("load", () => {
  //carrega os valores da SessionStorage na variável credenciais
  var credentials = JSON.parse(sessionStorage.getItem("credentials"));
 
  //se o user forçar a entrada ara outra página sem efetuar login, é encaminhado para a index.html
  if (loggedInPages.includes(window.location.pathname) &&
  credentials === null) {
  goIndexPage();
}

// carrega a informação do utilizador activo para preencher o cabeçalho
getLoggedProfile();
});

// ESPECIFICAÇÃO R5c - // carrega a informação do utilizador activo para preencher o cabeçalho
function getLoggedProfile() {

let credentials=JSON.parse(sessionStorage.getItem('credentials'));

fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/' + credentials.username + '/logged',
  {
      method: 'GET',
      headers: {
          'Accept': 'application/json',
          'username':credentials.username,
          'password':credentials.password
      },
  }
). then(response => response.json())
.then(response => fillTopNav(JSON.stringify(response)));
}


function fillTopNav(user) {
  let loggedUser = JSON.parse(user);

  document.getElementById('utilizadorAtivo').innerHTML = loggedUser.firstName + " " + loggedUser.lastName;
  
  let img = document.createElement('img');
  img.classList.add('profile-picture');
  img.src = loggedUser.photoUrl;
  img.onerror= function(){
    img.src ="ProfileImage.png";
    };

  document.getElementById('profile-picture-box').appendChild(img);
}


// Função Logout
function logout() {
  fetch(
    "http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/logout",
    {
      method: "POST",
      headers: {
        Accept: "application/json",
      },
    }
  ).then((response) => response.json());

  sessionStorage.clear();
  localStorage.clear();
}

//Para precaver saidas abruptas da aplicação
/*window.addEventListener('unload', function() {
    sessionStorage.clear();
  });
*/

// funções de abertura e fecho da sidebar
function openNav() {
  document.getElementById("mySidebar").style.width = "300px";
  document.getElementById("pageName").style.marginLeft = "300px";
}
function closeNav() {
  document.getElementById("mySidebar").style.width = "0";
  document.getElementById("pageName").style.marginLeft = "0px";
}

function goHomePage() {
  window.open("home.html", "_self");
}
function goDetailsPage() {
  window.open("details.html", "_self");
}
function goBackPage() {
  window.history.go(-1);
}
function goIndexPage() {
  window.open("index.html", "_self");
}
