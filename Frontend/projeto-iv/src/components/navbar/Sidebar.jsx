import React, { useContext, useState, useEffect } from "react";
import { slide as Menu } from "react-burger-menu";
import "./Sidebar.css";
import { AppContext } from "../../router/index";
import { useStore } from "../../store/userStore";

export default (props) => {
  // const{credentials}=useContext(AppContext);
  const adminCredentials = useStore((state) => state.adminCredentials);

  const [showUserLink, setShowUserLink] = useState(false);

  useEffect(() => {
    if (adminCredentials === "yes") {
      setShowUserLink(true);
    }
  });
  const showUserLinkStyle = {
    display: showUserLink ? "block" : "none",
  };

  return (
    // <AppContext.Provider value={credentials}>
    <Menu>
      <a className="menu-item" href="/home">
        Home
      </a>
      <a className="menu-item" href="/activity">
        Activity
      </a>
      <a className="menu-item" href="/profile">
        Profile
      </a>
      <a className="menu-item" href="/newTask">
        New Task
      </a>
      <a
        className="menu-item"
        id="newUser-link"
        href="/register"
        style={showUserLinkStyle}
      >
        Register New User
      </a>
    </Menu>
    //</AppContext.Provider>
  );
};
