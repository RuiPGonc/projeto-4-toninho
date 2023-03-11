export async function performLogin(username, password) {
  try {
    return await fetch(
      "http://localhost:8080/Projeto-iv/rest/ToDo_app/users/login",
      {
        method: "POST",
        mode: "cors",
        headers: {
          Accept: "*/*",
          "Content-Type": "application/json",
          username: username,
          password: password,
        },
      }
    )
      .then((response) => response.json())
      .then((response) => {
        return response;
      });
  } catch (error) {
    console.error(error);
  }
}
