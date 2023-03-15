import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import SubmitButton from "../../components/generics/btnSubmit";
import Input from "../../components/form/input";
import { AppContext } from "../../router/index";
import { createNewUser } from "./actions";
import { useStore } from "../../store/userStore";

export default function Register() {
  const navigate = useNavigate();
  const token = useStore((state) => state.token);

  const [inputs, setInputs] = useState({});

  //para guardar os valores da password e da password de confirmação
  const [arrayPassword, setPassword] = useState({});
  let userPassword = "";
  const takePassword = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setPassword((values) => ({ ...values, [name]: value }));
  };
  function confirmPassword() {
    if (arrayPassword.confirmPassword === arrayPassword.password) {
      userPassword = arrayPassword.password;
    } else {
      console.log("As passwords não correspondem");
    }
  }

  const handleChange = (event) => {
    console.log("aquiiiii");
    const name = event.target.name;
    const value = event.target.value;
    setInputs((values) => ({ ...values, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    confirmPassword();
    console.log(userPassword);
    createNewUser(token, inputs, userPassword).then((response) => {
      if (response === "ok") {
        console.log("Sucess!");
        navigate("/login", { replace: true });
      }
    });
  };
  const goHome = async (event) => {
    event.preventDefault();
    navigate("/home", { replace: true });
  };

  return (
    <div className="Register" id="register-form">
      <form onSubmit={handleSubmit}>
        <Input
          type="text"
          text="Username"
          name="username"
          defaultValue=""
          handleOnChange={handleChange}
        />
        <Input
          type="text"
          text="First Name"
          name="firstName"
          defaultValue=""
          handleOnChange={handleChange}
        />
        <Input
          type="text"
          text="Last Name"
          name="lastName"
          defaultValue=""
          handleOnChange={handleChange}
        />
        <Input
          type="text"
          text="Email"
          name="email"
          defaultValue=""
          handleOnChange={handleChange}
        />
        <Input
          type="text"
          text=" Url Photo"
          name="photoUrl"
          defaultValue=""
          handleOnChange={handleChange}
        />
        <Input
          type="text"
          text="Phone"
          name="phone"
          defaultValue=""
          handleOnChange={handleChange}
        />
        <Input
          type="password"
          text="Password"
          name="password"
          defaultValue=""
          handleOnChange={takePassword}
        />
        <Input
          type="password"
          text="Confirm Password"
          name="confirmPassword"
          defaultValue=""
          handleOnChange={takePassword}
        />
        <SubmitButton text="Create User" onClick={handleSubmit} />
      </form>
      <SubmitButton text="back" onClick={goHome} />
    </div>
  );
}
