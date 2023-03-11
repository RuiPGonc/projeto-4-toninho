import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AppContext } from "../../router/index";
import { performLogin } from "./actions";

function Login() {
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});
  const { changeLoginStatus } = useContext(AppContext);
  const { updateToken } = useContext(AppContext);

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    setInputs((values) => ({ ...values, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    // validation of login
    const response = await performLogin(inputs.username, inputs.password);

    localStorage.setItem("username", inputs.username);
    changeLoginStatus();
    updateToken(response); //atualizar o token no AppContext
    navigate("/home", { replace: true });
  };

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
