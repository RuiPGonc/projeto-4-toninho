export async function getUserTaskList(userId, token) {
  try {
    return await fetch(
      `http://localhost:8080/Projeto-iv/rest/ToDo_app/users/activities/${userId}`,
      {
        method: "GET",
        headers: {
          Accept: "application/json",
          token: token,
        },
      }
    )
      .then((response) => response.json())
      .then((response) => {
        const list = [...response];
        return list;
      });
  } catch (error) {
    console.error(error);
  }
}

export async function newTask(token,inputs){
    try{
        fetch("http://localhost:8080/Projeto-iv/rest/ToDo_app/users/task", {
            method: "POST",
            headers: {
              Accept: "*/*",
              "Content-Type": "application/json",
              token: token
            },
            body: JSON.stringify(inputs),

          }).then(function (response) {
            return response;
          });

    }catch(error){
        console.log(error);
    }
}
