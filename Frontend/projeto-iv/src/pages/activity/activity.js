import React from "react";
import Sidebar from "../../components/navbar/Sidebar";
import { useState, useEffect, useContext } from "react";
import "./activity.css";
import { useNavigate } from "react-router-dom";

import { AppContext } from "../../router/index";

import { getUserTaskList, newTask } from "./actions";
import { useStore } from "../../store/userStore";

function Activity() {
  const [myactivities, setActivities] = useState([]);
  const [inputs, setInputs] = useState({});

  const { isLogin } = useContext(AppContext);
  //const { credentials } = useContext(AppContext);
  const navigate = useNavigate();

  //const username=useStore((state)=>state.username);
  const userId = useStore((state) => state.userId);
  const token = useStore((state) => state.token);

  //se não estiver logado é redirecionado para a pagina de login
  useEffect(() => {
    if (!isLogin) {
      //  navigate("/", { replace: true });
    }
  }, [isLogin]);

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    setInputs((values) => ({ ...values, [name]: value }));
  };

  useEffect(() => {
    getUserTaskList(userId, token).then((response) => {
      setActivities(response);
    });
    //setActivities(["a", "b"]);
  }, []);

  //carregar nova tarefa
  const handleSubmit = (event) => {
    event.preventDefault();
    const response = newTask(token, inputs);
    if (response.status === 200) {
      setActivities((values) => [...values, inputs]);

      console.log(myactivities);
    } else {
      alert("something went worng");
    }
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
                <th>Done</th>
              </tr>
            </thead>
            <tbody>
              {myactivities.map((task) => (
                <tr key={task.id}>
                  <td>{task.id}</td>
                  <td>{task.title}</td>
                  <td>{task.categoryId}</td>
                  <td>{task.details}</td>
                  <td>{task.done}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <h1>Add New Activity</h1>
        <div>
          <form onSubmit={handleSubmit}>
            <label>
              Activity Title
              <input
                type="text"
                name="title"
                defaultValue=""
                onChange={handleChange}
              />
            </label>
            <input type="submit" name="Add" />
          </form>
        </div>
      </div>
    </div>
  );
}
export default Activity;