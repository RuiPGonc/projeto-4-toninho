import React, { useContext, useEffect, useState } from "react";
import Sidebar from "../../components/navbar/Sidebar";
import { useNavigate, useParams } from "react-router-dom";

import { AppContext } from "../../router/index";
import { useStore } from "../../store/userStore";
import HomePageButton from "../../components/generics/goHome";
import Input from "../../components/form/input";
import SubmitButton from "../../components/generics/btnSubmit";
import { getUserInfo, updateUserInfo } from "./actions";
import {
  getUsers,
  changeUserRole,
  deleteUser,
} from "../usersList/showUsersList/actions";
import Activity_of_User from "./showTaskListOfUser";
import ConfirmBox from "../usersList/components/confirmBox";

function Profile_admin() {
  const navigate = useNavigate();
  const [inputs, setInputs] = useState({});
  const [userInfo, setUserInfo] = useState(null);
  const [isError, setError] = useState(false);
  const [isLoading, setLoading] = useState(true);

  const token = useStore((state) => state.token);
  const { userId } = useParams();
  const [userIdToDelete, setUserIdToDelete] = useState(null);
  const [showConfirmBox, setShowConfirmBox] = useState(false);

  useEffect(() => {
    if (!token) {
      navigate("/");
    }

    if (!userId) navigate("/users");
  }, [token]);

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

  //obter info do User a editar
  useEffect(() => {
    console.log("Equiiiii" + userId);
    setLoading(true);
    getUserInfo(token, userId)
      .then((response) => {
        setUserInfo(response);
        setError(false);
        setLoading(false);
      })
      .catch((err) => {
        setError(true);
        setLoading(false);
      });
  }, []);

  const changeRole = async (event) => {
    event.preventDefault();
    await changeUserRole(token, userId);
    console.log(userId);
    //como renderizar o código após mudar o Role?
    //setAllUsers(allUsers)
  };
  function handleDeleteUser(event) {
    event.preventDefault();
    setUserIdToDelete(userId);
    setShowConfirmBox(true);
  }
  function handleCancel() {
    setShowConfirmBox(false);
    setUserIdToDelete(null);
    //como renderizar após carregar no botão?
  }
  async function handleConfirm() {
    // Lógica para confirmar o delete do user->Fetch
    await deleteUser(token, userIdToDelete);
    setShowConfirmBox(false);
    setUserIdToDelete(null);
  }

  return (
    <div className="Profile" id="profile-admin-outer-container">
      <Sidebar
        pageWrapId={"profile-page-wrap"}
        outerContainerId={"profile-outer-container"}
      />
      <div className="page-wrap" id="profile-admin-page-wrap">
        {isError ? "Erro!" : null}
        {!isLoading && !isError ? (
          <>
            <h1>My Profile</h1>
            <div id="blocked-user-info">
              <div>
                <p>
                  username:
                  {userInfo && userInfo.username ? userInfo.username : ""}
                </p>
              </div>
              <div>
                <p>userId:{userId}</p>
              </div>
              <div>
                <p>
                  account state:
                  {userInfo && userInfo.state ? userInfo.state : ""}
                </p>
              </div>
              <div>
                <p>Photo:{userInfo.photoUrl}</p>
              </div>
              <div>
                <p>Role:{userInfo.admin ? "admin" : "user"}</p>
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
            <div>
              <Activity_of_User />
            </div>
            <div>
              <SubmitButton text="Change User Role" onClick={changeRole} />
            </div>
            <div>
              <SubmitButton text="Delete User" onClick={handleDeleteUser} />
            </div>
            <div>
              {userIdToDelete != null && showConfirmBox && (
                <ConfirmBox onConfirm={handleConfirm} onCancel={handleCancel} />
              )}
            </div>

            <div>
              <HomePageButton />
            </div>
          </>
        ) : null}
      </div>
    </div>
  );
}

export default Profile_admin;
