import React from "react";
import Sidebar from "../../components/navbar/Sidebar";
import { useState, useEffect, useContext } from "react";
import "./activity.css";
import { useNavigate } from "react-router-dom";

import { AppContext } from "../../router/index";

import { getUserTaskList, newTask } from "./actions";
import { useStore } from "../../store/userStore";
import CheckBox from "../../components/generics/chqbox";
import BtnDelete from "../../components/generics/btnDelete";
import BtnEdit from "../../components/generics/btnEdit";

function Activity() {
  const [myactivities, setActivities] = useState([]);

  const navigate = useNavigate();

  const userId = useStore((state) => state.userId);
  const token = useStore((state) => state.token);

  //se não estiver logado é redirecionado para a pagina de login
  useEffect(() => {
    if (userId) {
      getUserTaskList(userId, token).then((response) => {
        setActivities(response);
      });
    } else if (!userId) {
      navigate("/");
    }
  }, [userId]);

  const handleEditTask = (event) => {
    event.preventDefault();
    //ir para nova página editar a tarefa
  };
  const handleDeleteTask = (event) => {
    event.preventDefault();
    //eliminar tarefa
  };
  const handleComplete = (event) => {
    event.preventDefault();
    //ir para Fetch completar Task
  };

  return (
    <div className="Activity" id="activity-outer-container">
      <Sidebar
        pageWrapId={"activity-page-wrap"}
        outerContainerId={"activity-outer-container"}
      />
      <div className="page-wrap" id="activity-page-wrap">
        <h1>My Activities</h1>

        <div>
          <table className="tables" cellPadding="0" cellSpacing="0">
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Category</th>
                <th>Description</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {myactivities
                ? myactivities.map((task) => (
                    <tr key={task.id}>
                      <td>{task.id}</td>
                      <td>{task.title}</td>
                      <td>{task.categoryTitle}</td>
                      <td>{task.details}</td>
                      <td>
                        <CheckBox
                          title="Done"
                          defaultChecked={task.done ? true : false}
                          onClick={handleComplete}
                        />
                        <BtnEdit onClick={handleEditTask} />
                        <BtnDelete onClick={handleDeleteTask} />
                      </td>
                    </tr>
                  ))
                : null}
            </tbody>
          </table>
        </div>
        <div></div>
      </div>
    </div>
  );
}
export default Activity;
