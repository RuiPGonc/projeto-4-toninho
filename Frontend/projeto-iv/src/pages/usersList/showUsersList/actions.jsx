export async function getUsers(token) {
  try {
    return await fetch(`http://localhost:8080/Projeto-iv/rest/ToDo_app/users`, {
      method: "GET",
      headers: {
        Accept: "application/json",
        token: token,
      },
    })
      .then((response) => response.json())
      .then((response) => {
        const list = [...response];

        return list;
      });
  } catch (error) {
    console.error(error);
  }
}

export async function getAdmins(token) {
  try {
    return await fetch(
      `http://localhost:8080/Projeto-iv/rest/ToDo_app/users/allAdmins`,
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
        console.log(list);
        console.log(response);

        return list;
      });
  } catch (error) {
    console.error(error);
  }
}

export async function changeUserRole(token, id) {
  try {
    return await fetch(
      `http://localhost:8080/Projeto-iv/rest/ToDo_app/users/userRole/${id}`,
      {
        method: "PUT",
        headers: {
          Accept: "application/json",
          token: token,
        },
      }
    ).then(console.log("success"));
  } catch (error) {
    console.error(error);
  }
}
export async function deleteUser(token, id) {
  try {
    await fetch(
      `http://localhost:8080/Projeto-iv/rest/ToDo_app/users/deleted/${id}`,
      {
        method: "DELETE",
        headers: {
          Accept: "application/json",
          token: token,
        },
      }
    );
    console.log("success");
  } catch (error) {
    console.error(error);
  }
}
