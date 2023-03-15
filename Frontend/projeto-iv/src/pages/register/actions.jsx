export async function createNewUser(token,userInfo, password) {
    try {
        
        console.log("No FETCH"+token)

        console.log(userInfo.photoUrl)
      return await fetch(
        `http://localhost:8080/Projeto-iv/rest/ToDo_app/users/newUser`,
        {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            token: token,
            password:password,
          },
          body: JSON.stringify(userInfo),
        }
      )
        .then((response) => {
            if (response.status === 200) {
                const success="ok"
                console.log("Sucesso ao criar user")
                return success;
              } else {
                throw new Error("Erro ao criar novo usuário");
              }
        });
    } catch (error) {
      console.error(error);
    }
  }