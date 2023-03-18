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
import dateTime_format from "../../components/generics/dateTimeFormated";
import HomePageButton from "../../components/generics/goHome";

export default function NewTask() {
  const token = useStore((state) => state.token);
  const navigate = useNavigate();
  const userId = useStore((state) => state.userId);
  const [inputs, setInputs] = useState({});
  //btn Alert
  let [alertValue, setAlertValue] = useState(false);
  //input Reminder
  let [reminderValue, setReminderValue] = useState(false);
  //btn switch Done
  let [doneValue, setDoneValue] = useState(false);
  const [myCategories, setCategories] = useState([]);
  //componente Time Reminder
  const [timeReminderStyle, setShowTimeRimender] = useState(false);
  //definição do startTime
  const [startTime, setStartTime] = useState(getCurrentDateTime());
  const [deadlineTime, setDeadlineTime] = useState();
  const [timeReminder, setTimeReminder] = useState();
  //variável para o Id da Categoria
  const [categoryId, setCategory] = useState();

  //obter a lsita de categorias do User
  useEffect(() => {
    getUserCategoryList(userId, token).then((response) => {
      setCategories(response);
    });
  }, []);

  //se não estiver logado é redirecionado para a pagina de login
  useEffect(() => {
    if (!userId) {
      navigate("/", { replace: true });
    }
  }, [userId]);

  //construção do objeto Inputs
  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((values) => ({ ...values, [name]: value }));
  };

  //componente Time Reminder
  const showTimeReminderInput = () => {
    setShowTimeRimender(!timeReminderStyle);
    setAlertValue(!alertValue);
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

  //chamada do Fetch
  const handleSubmit = async (event) => {
    event.preventDefault();
    /*fazer estas validações noutra página
os ifs abaixo obrigam a que a data e hora seja bem preenchida
    if ( !dateObject.startTime || isNaN(dateObject.startTime.getTime())) {
      alert("Por favor, preencha a data e hora de início corretamente.");
      return;
    }
    if ( !dateObject.deadlineTime || isNaN(dateObject.deadlineTime.getTime())) {
      alert("Por favor, preencha a data e hora de final corretamente.");
      return;
    }
    if ( !dateObject.timeReminder || isNaN(dateObject.timeReminder.getTime())) {
      alert("Por favor, preencha a data e hora do Alarme corretamente.");
      return;
    }
*/

    console.log(
      "Submit",
      inputs,
      { categoryId: categoryId },
      {
        deadlineTime,
        timeReminder,
        startTime,
      },
      timeReminderStyle,
      doneValue
    );

    // const object_Task = await setObject_to_Fetch(
    //   inputs,
    //   { categoryId },
    //   {
    //     deadlineTime,
    //     timeReminder,
    //     startTime,
    //   },
    //   timeReminderStyle,
    //   doneValue
    // );

    // createNewTask(token, object_Task).then((response) => {
    //   if (response === "ok") {
    //     console.log("Sucess!");
    //     // navigate("/login", { replace: true });
    //   }
    // });
  };

  console.log({ categoryId, myCategories });

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
          value={inputs.title}
          handleOnChange={handleChange}
          required
        />
        <Input
          type="text"
          text="Details"
          name="details"
          value={inputs.details}
          handleOnChange={handleChange}
        />
        <Select
          name="categoryId"
          text="Select a Category"
          options={myCategories}
          handleOnChange={(e) => setCategory(e.target.value)}
          value={{ categoryId }}
          required
        />
        <Input
          type="datetime-local"
          text="Start Date"
          name="startTime"
          value={startTime}
          handleOnChange={(e) => setStartTime(e.target.value)}
          required
        />
        <Input
          type="datetime-local"
          text="deadline Date"
          name="deadlineTime"
          value={deadlineTime}
          handleOnChange={(e) => setDeadlineTime(e.target.value)}
        />
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
              value={timeReminder}
              handleOnChange={(e) => setTimeReminder(e.target.value)}
            />
          )}
          <SwitchButton
            label="Done"
            value={doneValue}
            handleSwitchChange={handleDonebtn}
          />
        </div>
        <button type="submit">Create task</button>
      </form>
      <div>
        <HomePageButton />
      </div>
    </div>
  );
}
