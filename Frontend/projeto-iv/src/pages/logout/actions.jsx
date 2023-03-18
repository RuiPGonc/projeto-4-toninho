export async function backendLogout(token){

    try {
        return await fetch(
          `http://localhost:8080/Projeto-iv/rest/ToDo_app/users/logout`,
          {
            method: "POST",
            headers: {
              Accept: "application/json",
              token: token,
            },
          }
        )
          .then((response) => {
            if(response.status===200){
             console.log("Logout Success!")}
          });
      } catch (error) {
        console.error(error);
      }
    }