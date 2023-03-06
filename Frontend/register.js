// R3 - ADICIONA NOVO UTILIZADOR
function addUser(e) {
e.preventDefault();

    if (checkRegisterInput() == 1){
        alert("Todos os campos devem ser preenchidos.")
    } else if (checkRegisterInput() == 2) {
        alert("As passwords devem ser coincidentes.")
    }else if(checkRegisterInput() == 3){
        alert("Verifique o preenchimento do campo de email.")
    }else if(checkRegisterInput() == 4){
        alert("O número de telefone deve conter 9 dígitos.")
   
    } else {
        let newUser = {
            'username' : document.getElementById('username').value,
            'firstName': document.getElementById('firstName').value,
            'lastName': document.getElementById('lastName').value,
            'email': document.getElementById('email').value,
            'phone': document.getElementById('phone').value,
            'photoUrl': document.getElementById('photo').value
        };
    
        fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/register',
        {
            method: 'POST',
            headers:
            {
                'Accept': '*/*',
                'Content-Type': 'application/json',
                'password': document.getElementById('password1').value,
            },
            body: JSON.stringify(newUser)
        }
        ).then(function (response) {
           if (response.status == 200) {
            alert('Adicionou um novo utilizador com sucesso.');
            goIndexPage();
            } else if(response.status == 401) {
            alert('O username escolhido já existe. Por favor, insira outro.');
            }else{
            alert('Ups! Algo de errado aconteceu.')
            }
        })
    };
}


// verifica se inserção de valores nos campos é válida
function checkRegisterInput() {
    let username = document.getElementById('username').value;
    let firstName = document.getElementById('firstName').value;
    let lastName = document.getElementById('lastName').value;
    let email = document.getElementById('email').value;
    let phone = document.getElementById('phone').value;
    let photo = document.getElementById('photo').value;
    let password1 = document.getElementById('password1').value;
    let password2 = document.getElementById('password2').value;

    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const regexTelefone= /^\d{9}$/;

    if (username == "" || firstName == "" || lastName == "" || email == "" || phone == "" || photo == "" || password1 == 0 || password2 == 0) {
        return 1;
    }else if(!regexEmail.test(email)){
        return 3;
    }else if(!regexTelefone.test(phone)){
        return 4;
    } else if (password1 !== password2) {
        return 2;
    
}
}

function goIndexPage() {
    window.open("index.html", "_self");
}
  