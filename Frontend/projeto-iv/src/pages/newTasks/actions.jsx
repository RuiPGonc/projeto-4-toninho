export async function getUserCategoryList(userId, token) {
    try {
      return await fetch(
        `http://localhost:8080/Projeto-iv/rest/ToDo_app/users/categories/${userId}`,
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
  export async function createNewTask(userId,token,task){
    try {
        return await fetch(
          `http://localhost:8080/Projeto-iv/rest/ToDo_app/users/categories/${userId}`,
          {
            method: "GET",
            headers: {
              Accept: "application/json",
              token: token,
            },
            body:task,
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



