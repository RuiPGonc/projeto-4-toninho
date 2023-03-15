import React, { useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import SubmitButton from "../../components/generics/btnSubmit";
import Input from "../../components/form/input";
import Select from "../../components/form/select";
import { AppContext } from "../../router/index";
//import { createNewUser } from "./actions.jsx";
import { useStore } from "../../store/userStore";
import { getUserCategoryList, createNewTask } from "./actions";
import SwitchButton from "./components/switchButton";
import getCurrentDateTime from "../../components/generics/systemDateTime";
import setObject_to_Fetch from "./objectNewTask";

export default function NewTask() {
  const { isLogin } = useContext(AppContext);
  const token = useStore((state) => state.token);
  const navigate = useNavigate();
  const userId = useStore((state) => state.userId);
  const [inputs, setInputs] = useState({});
  //btn Alert
  let [alertValue, setalertValue] = useState(false);
  //input Reminder
  let [reminderValue, sertReminderValue] = useState(false);
  //btn switch Done
  let [doneValue, setDoneValue] = useState(false);
  const [myCategories, setCategories] = useState([]);
  //componente Time Reminder
  const [timeReminderStyle, setShowTimeRimender] = useState(false);
  //definição do startTime
  const [startTime, setStartTime] = useState(getCurrentDateTime());
  //definição de objeto para guardar datas
  const [dateObject, setDates] = useState([]);
  //variável para o Id da Categoria
  const[categoryId,setCategory]=useState("");

  //obter a lsita de categorias do User
  useEffect(() => {
    getUserCategoryList(userId, token).then((response) => {
      setCategories(response);
      console.log(response.id)
    });
  }, []);
  //se não estiver logado é redirecionado para a pagina de login
  useEffect(() => {
    if (!isLogin) {
      //  navigate("/", { replace: true });
    }
  }, [isLogin]);

  const goHome = async (event) => {
    event.preventDefault();
    navigate("/home", { replace: true });
  };


  //obter o id da Categoria
  const handleCategory = (event) =>{
    setCategory(event.target.value);
  }
  //construção do objeto Inputs
  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((values) => ({ ...values, [name]: value }));
  };

  //componente Time Reminder
  const showTimeReminderInput = () => {
    setShowTimeRimender(!timeReminderStyle);
    setalertValue(!alertValue);
  };
  //componente Done
  const handleDonebtn = () => {
    setDoneValue(!doneValue);
  };
  //definição do startTime
  /* const handleChange_startTime = (event) => {
    setStartTime(event.target.value);
  };
  //valor do Time Reminder
 /* const handleChangeReminder = (event) => {
    sertReminderValue(event.target.value);
  };*/

  //construção do objeto data
  const handleChangeDates = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setDates((values) => ({ ...values, [name]: value }));
  };

  //chamada do Fetch
  const handleSubmit = async (event) => {
    event.preventDefault();
    const object_Task = setObject_to_Fetch(
      inputs,
      categoryId,
      dateObject,
      timeReminderStyle,
      doneValue
    );

    createNewTask(userId, token, object_Task).then((response) => {
      if (response === "ok") {
        console.log("Sucess!");
        // navigate("/login", { replace: true });
      }
    });
  };

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
          required
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
          handleOnChange={handleCategory}
          required
        />
        <Input
          type="datetime-local"
          text="Start Date"
          name="startTime"
          defaultValue={startTime}
          handleOnChange={handleChangeDates}
          required
        />
        <Input
          type="datetime-local"
          text="deadline Date"
          name="deadlineTime"
          handleOnChange={handleChangeDates}
        />
      </form>
      <div id="Alert_div">
        <SwitchButton
          label="Alert"
          value={alertValue}
          handleSwitchChange={showTimeReminderInput}
        />
        {timeReminderStyle && (
          <input
            id="timeReminder"
            type="datetime-local"
            name="timeReminder"
            value={reminderValue}
            onChange={handleChangeDates}
          />
        )}
        <SwitchButton
          label="Done"
          value={doneValue}
          handleSwitchChange={handleDonebtn}
        />
      </div>
      <SubmitButton text="Create Task" onClick={handleSubmit} />
      <SubmitButton text="back" onClick={goHome} />
    </div>
  );
}
