import React, { createContext, useState } from "react";
import { BrowserRouter as Router } from "react-router-dom";
import { Route, Routes } from "react-router-dom";

import Login from "../pages/login/login";
import Home from "../pages/home/home";
import Activity from "../pages/showTaskList/showTaskList";
import Profile from "../pages/profile/profile";
import Register from "../pages/register/register";
import NewTask from "../pages/newTasks/newTask";
import Users from "../pages/usersList/comunity";
import Profile_admin from "../pages/profile_admin/profile_admin";

export const AppContext = createContext();

function AppRouter() {
  const [isLogin, setIsLogin] = useState(false);
  const changeLoginStatus = () => setIsLogin(!isLogin);
  //const mantainLogin = () =>isLogin=true;

  const [credentials, setCredentials] = useState({ token: "", userId: "0" });

  const updateCredentials = (response) => {
    return setCredentials((prevState) => ({
      ...prevState,
      token: response.token,
      userId: response.userId,
    }));
  };

  const newValue = {
    isLogin: isLogin,
    changeLoginStatus: changeLoginStatus,
    credentials: credentials,
    setCredentials: setCredentials,
  };

  return (
    <AppContext.Provider value={newValue}>
      <Router>
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/" element={<Login />} />
          <Route path="/home" element={<Home />} />
          <Route path="/activity" element={<Activity />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/login" element={<Login />} />
          <Route path="/newTask" element={<NewTask />} />
          <Route path="/users" element={<Users />} />
          <Route path="/profile_admin" element={<Profile_admin />} />

        </Routes>
      </Router>
    </AppContext.Provider>
  );
}

export default AppRouter;
