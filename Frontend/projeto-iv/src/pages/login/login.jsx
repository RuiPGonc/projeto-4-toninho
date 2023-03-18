import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { performLogin } from "./actions";
import { useStore } from "../../store/userStore";

function Login() {
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});

  const updateName = useStore((state) => state.updateName);
  const updateToken = useStore((state) => state.updateToken);
  const updateUserId = useStore((state) => state.updateUserId);
  const updateAdminCredentials = useStore(
    (state) => state.updateAdminCredentials
  );
  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((values) => ({ ...values, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    // validation of login
    performLogin(inputs.username, inputs.password).then((response) => {
      updateName(inputs.username); //atualiza o username na Store
      updateToken(response.token);
      updateUserId(response.userId);
      console.log(response.adminCredentials);
      updateAdminCredentials(response.adminCredentials);

      navigate("/home");
    });
  };

  function goToRegisterPage() {
    console.log("Aqui");
    navigate("/register");
  }

  return (
    <div className="Login" id="login-outer-container">
      <div className="page-wrap" id="login-page-wrap">
        <h1>Login</h1>
        <form onSubmit={handleSubmit}>
          <label>
            Enter your username:
            <input
              type="text"
              name="username"
              defaultValue={inputs.username || ""}
              onChange={handleChange}
            />
          </label>
          <label>
            Enter your password:
            <input
              type="password"
              name="password"
              defaultValue={inputs.password || ""}
              onChange={handleChange}
            />
          </label>
          <input type="submit" value="Login" />
        </form>
      </div>
    </div>
  );
}

export default Login;
