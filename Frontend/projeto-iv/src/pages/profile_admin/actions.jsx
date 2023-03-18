export async function getUserInfo(token, userId) {
  try {
    console.log(token)
    console.log(userId)
    return await fetch(
      `http://localhost:8080/Projeto-iv/rest/ToDo_app/users/info/${userId}`,
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
          console.log(response)
        //const list = [...response];
        return response;
      });
  } catch (error) {
    console.error(error);
  }
}

export async function updateUserInfo(token, userInfo, userId) {
  try {
    return await fetch(
      `http://localhost:8080/Projeto-iv/rest/ToDo_app/users/${userId}`,
      {
        method: "POST",
        headers: {
          Accept: "*/*",
          "Content-Type": "application/json",
          token: token,
        },
        body: JSON.stringify(userInfo),
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
