import React  from "react";
import Sidebar from "../../components/navbar/Sidebar";
import { useState, useEffect,useContext } from "react";
import "./activity.css";
import { AppContext } from "../../router/index";
import { useNavigate } from "react-router-dom";

function Activity() {
  const [activities, setActivities] = useState([]);
  const [inputs, setInputs] = useState({});

  const { isLogin } = useContext(AppContext);
  const navigate = useNavigate();
  useEffect(() => {
    if (!isLogin) {
      navigate("/", { replace: true });
    }
  }, [isLogin]); 


  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    setInputs((values) => ({ ...values, [name]: value }));
  };

  useEffect(() => {
    fetch(
      "http://localhost:8080/Projeto-iv/rest/ToDo_app/users/activities/999",
      {
        method: "GET",
        headers: {
          Accept: "application/json",
          token: "3DAF6E65CD1CA4535788DB7F382E4DB3",
        },
      }
    )
      .then((response) => response.json())
      .then((response) => {
        setActivities(response);
      });
  }, []);

  //carregar nova tarefa
  const handleSubmit = (event) => {
    event.preventDefault();

    fetch("http://localhost:8080/Projeto-iv/rest/ToDo_app/users/task", {
      method: "POST",
      headers: {
        Accept: "*/*",
        "Content-Type": "application/json",
        token: "3DAF6E65CD1CA4535788DB7F382E4DB3",
      },
      body: JSON.stringify(inputs),
    }).then(function (response) {
      if (response.status === 200) {
        //alert('activity is added successfully)

        //you can change the endpoint to return the new activity created in order to have its ID to be shown in the table

        //this line automatically change the table of activities and add a new row
        setActivities((values) => [...values, inputs]);
      } else {
        alert("something went worng");
      }
    });
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
              {activities.map((task) => (
                <tr key={task.Id}>
                  <td>{task.Id}</td>
                  <td>{task.Title}</td>
                  <td>{task.Category}</td>
                  <td>{task.Details}</td>
                  <td>{task.Done}</td>
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
