import React, { useState,userState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import SubmitButton from "../../components/generics/btnSubmit";
import Input from "../../components/form/input";
import Select from "../../components/form/select";
import { AppContext } from "../../router/index";
//import { createNewUser } from "./actions.jsx";
import { useStore } from "../../store/userStore";
import { getUserCategoryList, createNewTask } from "./actions";
import SwitchButton_Alert from "./components/switchButton_Alert";
import DateTime_component from "./components/dateTimeComponent";


export default function NewTask() {
  const { isLogin } = useContext(AppContext);
  const token = useStore((state) => state.token);
  const navigate = useNavigate();
  const userId = useStore((state) => state.userId);
  const [inputs, setInputs] = useState({});

  //se não estiver logado é redirecionado para a pagina de login
  useEffect(() => {
    if (!isLogin) {
      //  navigate("/", { replace: true });
    }
  }, [isLogin]);

  const handleChange = (event) => {
    console.log("aquiiiii");
    const name = event.target.name;
    const value = event.target.value;
    setInputs((values) => ({ ...values, [name]: value }));
  };
  const handleSubmit = async (event) => {
    event.preventDefault();
    createNewTask(userId, token, inputs).then((response) => {
      if (response === "ok") {
        console.log("Sucess!");
        // navigate("/login", { replace: true });
      }
    });
  };

  //btn switch Alerta
  let [switchValue ,setSwitchValue]=useState(false);
 
  const handleSwitchChange = (event) =>{
    setSwitchValue(event.target.checked);
    if(event.target.checked ){ //' !! garante a variável como boolean
      //aparece a data para o time reminder alerta deadline 
    }
  }

  
  const goHome = async (event) => {
    event.preventDefault();
    navigate("/home", { replace: true });
  };

  const [myCategories, setCategories] = useState([]);
  useEffect(() => {
    getUserCategoryList(userId, token).then((response) => {
      setCategories(response);
    });
  }, []);

  return (
    <div className="Register" id="register-form">
        <div className="page-wrap" id="home-page-wrap">
        <h1>New Task</h1>
      </div>
      <form onSubmit={handleSubmit}>
        <Input
          type="text"
          text="Title"
          name="title"
          defaultValue=""
          handleOnChange={handleChange}
        />
        <Input
          type="text"
          text="Details"
          name="details"
          defaultValue=""
          handleOnChange={handleChange}
        />
        <Select
          name="categoryId"
          text="Select one Category"
          options={myCategories}
        />
        <SwitchButton_Alert handleOnChange={handleSwitchChange} value="switchValue"/>

        <SubmitButton text="Create Task" onClick={handleSubmit} />
       
      </form>
      <SubmitButton text="back" onClick={goHome} />
    </div>
  );
}
