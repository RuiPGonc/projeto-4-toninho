import React, { useContext, useEffect } from "react";
import Sidebar from "../../components/navbar/Sidebar";
import Activity from "../showTaskList/showTaskList";
import { useNavigate } from "react-router-dom";
import "./styles.css";
import { useStore } from "../../store/userStore";

import { AppContext } from "../../router/index";
import SubmitButton from "../../components/generics/btnSubmit";

function Home() {
  const { isLogin } = useContext(AppContext);
  // const { credentials } = useContext(AppContext);
  // const { setCredentials } = useContext(AppContext);
  const { changeLoginStatus } = useContext(AppContext);

  const username = useStore((state) => state.username);
  const userId = useStore((state) => state.userId);
  const token = useStore((state) => state.token);

  const navigate = useNavigate();

  //direciona o user se não estiver logado
  useEffect(() => {
    if (!isLogin) {
      navigate("/", { replace: true });
    }
  }, [isLogin]);

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
