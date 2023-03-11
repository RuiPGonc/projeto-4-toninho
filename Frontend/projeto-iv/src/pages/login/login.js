import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AppContext } from "../../router/index";

function Login() {
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});
  const { changeLoginStatus } = useContext(AppContext);

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    setInputs((values) => ({ ...values, [name]: value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    // validation of login
    localStorage.setItem("username", inputs.username);
    changeLoginStatus();
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
              type="text"
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
