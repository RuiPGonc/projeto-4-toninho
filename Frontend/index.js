// FETCH R1 - LOGIN DE UTILIZADOR
function login(form) {
    
    let credentials = {
        'username' : document.getElementById('input-username').value,
        'password' : document.getElementById('input-password').value
    };


    fetch('http://localhost:8080/alexandre-rui-proj2/rest/ToDo_app/users/login',
    {
        method: 'POST',
        headers:
        {
             'Accept': '*/*',
             'Content-Type': 'application/json'
        },
           body: JSON.stringify(credentials)
    }    
    ).then(function (response) {
        if (response.status == 200) {
            alert('Login efetuado com sucesso!');
            sessionStorage.setItem('credentials', JSON.stringify(credentials));
            goHomePage();
        } else {
            alert('Login rejeitado!')
        }
    })
}

// FUNÇÕES
function goHomePage(){
    window.open('home.html', '_self');
}