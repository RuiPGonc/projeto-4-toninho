import React, { createContext, useState } from "react";
import { BrowserRouter as Router } from "react-router-dom";
import { Route, Routes } from "react-router-dom";

import Login from "../pages/login/login";
import Home from "../pages/home/home";
import Activity from "../pages/activity/activity";
import Profile from "../pages/profile/profile";

export const AppContext = createContext();

function AppRouter() {
  //const [isLogin, setIsLogin] = useState(false);
  //const changeLoginStatus = () => setIsLogin(!isLogin);

  const [isLogin, setIsLogin]= useState(false);
  const changeLoginStatus = () => setIsLogin(!isLogin);
  
  const[ token,setToken]=useState("");
  const updateToken = (newToken) => {
  return setToken(newToken);
  }


  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={
            <AppContext.Provider
            value={{ isLogin: isLogin, changeLoginStatus: changeLoginStatus,
              token: token,updateToken: updateToken}}

            >
              <Login />
            </AppContext.Provider>
          }
        />
        <Route
          path="/home"
          element={
            <AppContext.Provider
              value={{ isLogin: isLogin, changeLoginStatus: changeLoginStatus,
                token: token,updateToken: updateToken}}
            >
              <Home />
            </AppContext.Provider>
          }
        />
        <Route
          path="/activity"
          element={
            <AppContext.Provider
            value={{ isLogin: isLogin, changeLoginStatus: changeLoginStatus,
              token: token,updateToken: updateToken}}
            >
              <Activity />
            </AppContext.Provider>
          }
        />
        <Route
          path="/profile"
          element={
            <AppContext.Provider
            value={{ isLogin: isLogin, changeLoginStatus: changeLoginStatus,
              token: token,updateToken: updateToken}}
            >
              <Profile />
            </AppContext.Provider>
          }
        />
        <Route
          path="/login"
          element={
            <AppContext.Provider
            value={{ isLogin: isLogin, changeLoginStatus: changeLoginStatus,
              token: token,updateToken: updateToken}}
            >
              <Login />
            </AppContext.Provider>
          }
        />
      </Routes>
    </Router>
  );
}

export default AppRouter;
