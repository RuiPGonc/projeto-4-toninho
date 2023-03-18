import React from "react";
import Sidebar from "../../components/navbar/Sidebar";
import { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";

import { AppContext } from "../../router/index";

import { BtnDelete } from "../../components/generics/btnDelete";
import { getUserTaskList } from "../showTaskList/actions";
import { useStore } from "../../store/userStore";

function Activity_of_User() {
  const [myactivities, setActivities] = useState([]);
  const navigate = useNavigate();

  //const username=useStore((state)=>state.username);
  const userId = useStore((state) => state.userId);
  const token = useStore((state) => state.token);
  const userIDEdited = useStore((state) => state.editedUserId);

  //se não estiver logado é redirecionado para a pagina de login
  useEffect(() => {
    if (!userId) {
      navigate("/");
    }
  }, [userId]);

  useEffect(() => {
    console.log("userIDEdited da storage " + userIDEdited);
    getUserTaskList(userIDEdited, token).then((response) => {
      setActivities(response);
      console.log(response);
    });
  }, []);

  return (
    <div className="user-Activity" id="activity-outer-container">
      <Sidebar
        pageWrapId={"user-activity-page-wrap"}
        outerContainerId={"user-activity-outer-container"}
      />
      <div className="page-wrap" id="user-activity-page-wrap">
        <h4>User Activities</h4>
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
              {myactivities
                ? myactivities.map((task) => (
                    <tr key={task.id}>
                      <td>{task.id}</td>
                      <td>{task.title}</td>
                      <td>{task.categoryTitle}</td>
                      <td>{task.details}</td>
                      <td>{task.done ? "yes" : "no"}</td>
                    </tr>
                  ))
                : null}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
export default Activity_of_User;
