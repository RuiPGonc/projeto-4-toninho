import React, { useContext } from 'react'
import { slide as Menu } from 'react-burger-menu';
import './Sidebar.css';
import { AppContext } from "../../router/index";

export default props => {
  const{credentials}=useContext(AppContext);
 // const { setCredentials } = useContext(AppContext);
 //setCredentials(credentials);
/*
const{isLogin}=useContext(AppContext);
const pressitLogin=isLogin;
*/
  return (
  // <AppContext.Provider value={credentials}>
   <Menu>  
      <a className="menu-item" href="/home">
      Home</a>
      <a className="menu-item" href="/activity">Activity</a>
      <a className="menu-item" href="/profile">Profile</a>
    </Menu>
    //</AppContext.Provider>

  );
};