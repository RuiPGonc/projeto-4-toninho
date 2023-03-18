import React, { useEffect } from "react";
import Sidebar from "../../components/navbar/Sidebar";
import Activity from "../showTaskList/showTaskList";
import { useNavigate } from "react-router-dom";
import "./styles.css";
import { useStore } from "../../store/userStore";

import SubmitButton from "../../components/generics/btnSubmit";

function Home() {
  const username = useStore((state) => state.username);
  console.log({ username });

  const navigate = useNavigate();

  //direciona o user se não estiver logado
  useEffect(() => {
    if (!username) {
      navigate("/", { replace: true });
    }
  }, [username]);

  function goNewTask() {
    navigate("/newTask", { replace: true });
  }
  return (
    <div className="Home" id="home-outer-container">
      <Sidebar
        pageWrapId={"home-page-wrap"}
        outerContainerId={"home-outer-container"}
      />
      <div className="page-wrap" id="home-page-wrap">
        <h1>Home</h1>
      </div>
      <Activity />
      <SubmitButton text="Add new task" onClick={goNewTask} />
    </div>
  );
}

export default Home;
