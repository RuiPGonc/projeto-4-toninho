import React, { useContext, useEffect, useState } from "react";
import Sidebar from "../../components/navbar/Sidebar";
import { useNavigate } from "react-router-dom";

import { AppContext } from "../../router/index";
import { useStore } from "../../store/userStore";
import HomePageButton from "../../components/generics/goHome";
import Input from "../../components/form/input";
import SubmitButton from "../../components/generics/btnSubmit";
import BtnFilter from "../../components/generics/btnFilter";
import BtnLogout from"../logout/components/btnLogout"

import ShowUsers from "./showUsersList/showUsers";

function Users() {
  const { isLogin } = useContext(AppContext);
  //const{credentials}=useContext(AppContext);
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});
  const [userInfo, setUserInfo] = useState([]);
  const token = useStore((state) => state.token);
  const userId = useStore((state) => state.userId);
  const [admins, setAdmins] = useState([true]);

  useEffect(() => {
    if (!isLogin) {
      //navigate("/", { replace: true });
    }
  }, [isLogin]);

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((values) => ({ ...values, [name]: value }));
  };

  //obter info do User Logado
  useEffect(() => {
    //getUserInfo(token).then((response) => {
    // setUserInfo(response);
    // });
  }, []);
  const handleGetAdmins = async (event) => {
    setAdmins(true);
    event.preventDefault();
  };

  return (
    <div className="Profile" id="profile-outer-container">
      <Sidebar
        pageWrapId={"profile-page-wrap"}
        outerContainerId={"profile-outer-container"}
      />
      <div className="page-wrap" id="profile-page-wrap">
        <h1>Comunity</h1>
        <div>
          <BtnFilter onClick={handleGetAdmins} text="Only Admins  " />
        </div>
        <div>
          <ShowUsers value={admins} />
        </div>
        <div>{HomePageButton()}</div>
      </div>
      <div>
        <BtnLogout/>
      </div>
    </div>
  );
}

export default Users;
