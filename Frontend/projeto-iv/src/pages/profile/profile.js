import React, { useEffect, useState } from "react";
import Sidebar from "../../components/navbar/Sidebar";
import { useNavigate } from "react-router-dom";

import { useStore } from "../../store/userStore";
import HomePageButton from "../../components/generics/goHome";
import Input from "../../components/form/input";
import SubmitButton from "../../components/generics/btnSubmit";
import { getUserInfo, updateUserInfo } from "./actions";

function Profile() {
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});
  const [userInfo, setUserInfo] = useState([]);
  const token = useStore((state) => state.token);
  const userId = useStore((state) => state.userId);

  useEffect(() => {
    if (!userId) {
      navigate("/");
    }
  }, [userId]);

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((values) => ({ ...values, [name]: value }));
  };

  //submit
  const handleSubmit = async (event) => {
    event.preventDefault();
    updateUserInfo(token, inputs, userId);
    alert("Update success!");
  };

  //obter info do User Logado
  useEffect(() => {
    getUserInfo(token, userId).then((response) => {
      setUserInfo(response);
      console.log(response);
      console.log(userInfo);
    });
  }, []);

  return (
    <div className="Profile" id="profile-outer-container">
      <Sidebar
        pageWrapId={"profile-page-wrap"}
        outerContainerId={"profile-outer-container"}
      />
      <div className="page-wrap" id="profile-page-wrap">
        <h1>My Profile</h1>

        <div id="blocked-user-info">
          <div>
            <p>username:{userInfo.username}</p>
          </div>
          <div>
            <p>userId:{userId}</p>
          </div>
          <div>
            <p>account state:{userInfo.state}</p>
          </div>
          <div>
            <p>Photo:{userInfo.photoUrl}</p>
          </div>
        </div>
        <form onSubmit={handleSubmit}>
          <Input
            type="text"
            text="First Name"
            name="firstName"
            defaultValue={userInfo.firstName || "empty"}
            handleOnChange={handleChange}
          />
          <Input
            type="text"
            text="Last Name"
            name="lastName"
            defaultValue={userInfo.lastName || "empty"}
            handleOnChange={handleChange}
          />
          <Input
            type="text"
            text="Phone"
            name="phone"
            defaultValue={userInfo.phone || "empty"}
            handleOnChange={handleChange}
          />
          <Input
            type="text"
            text="Photo Url"
            name="photoUrl"
            defaultValue={userInfo.photoUrl || "empty"}
            handleOnChange={handleChange}
          />
          <Input
            type="text"
            text="Email"
            name="email"
            defaultValue={userInfo.email || "empty"}
            handleOnChange={handleChange}
          />
          <SubmitButton text="Update Profile" onClick={handleSubmit} />
        </form>
        <div>{HomePageButton()}</div>
      </div>
    </div>
  );
}

export default Profile;
