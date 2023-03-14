import React, { createContext, useState } from "react";
import { BrowserRouter as Router } from "react-router-dom";
import { Route, Routes } from "react-router-dom";

import Login from "../pages/login/login";
import Home from "../pages/home/home";
import Activity from "../pages/activity/activity";
import Profile from "../pages/profile/profile";

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
        <Route
          path="/"
          element={
              <Login />
          }
        />
        <Route
          path="/home"
          element={
              <Home />
          }
        />
        <Route
          path="/activity"
          element={
              <Activity />
          }
        />
        <Route
          path="/profile"
          element={
              <Profile />
          }
        />
        <Route
          path="/login"
          element={
              <Login />
          }
        />
      </Routes>
    </Router>
    </AppContext.Provider>
  );
}

export default AppRouter;
