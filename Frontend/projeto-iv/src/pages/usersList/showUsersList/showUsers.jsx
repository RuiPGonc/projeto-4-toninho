import React from "react";
import Sidebar from "../../../components/navbar/Sidebar";
import { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";

import { useStore } from "../../../store/userStore";
import { getUsers, changeUserRole, deleteUser, getAdmins } from "./actions";
import BtnChangeRole from "../../../components/generics/btnChangeRole";
import BtnEditProfile from "../../../components/generics/btnEdit";
import BtnDelete from "../../../components/generics/btnDelete";
import ConfirmBox from "../components/confirmBox";

function ShowUsers(admins) {
  const [allUsers, setAllUsers] = useState([]);
  const token = useStore((state) => state.token);
  const [showConfirmBox, setShowConfirmBox] = useState(false);
  const [userIdToDelete, setUserIdToDelete] = useState(null);
  const navigate = useNavigate();

  //const userId = useStore((state) => state.userId);

  const updateUsers = () => {
    if (admins === true) {
      getAdmins(token).then((response) => {
        setAllUsers(response);
      });
    } else {
      getUsers(token).then((response) => {
        setAllUsers(response);
      });
    }
  };

  //obter a lista de todos os Users
  useEffect(() => {
    if (admins === true) {
      getAdmins(token).then((response) => {
        setAllUsers(response);
      });
    } else {
      getUsers(token).then((response) => {
        setAllUsers(response);
      });
    }
  }, []);

  const changeRole = async (userId) => {
    await changeUserRole(token, userId);
    updateUsers();
  };

  function handleDeleteUser(event, id) {
    event.preventDefault();
    setUserIdToDelete(id);
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
    updateUsers();
    setShowConfirmBox(false);
    setUserIdToDelete(null);
  }

  const handleEditUserProfile = (id) => {
    navigate(`/profile_admin/${id}`);
  };

  return (
    <div className="Comunity" id="comunity-outer-container">
      <Sidebar
        pageWrapId={"comunity-page-wrap"}
        outerContainerId={"comunity-outer-container"}
      />
      <div className="page-wrap" id="comunity-page-wrap">
        <div className="table-row"></div>
        {allUsers.map((user) => (
          <div className="table-row" key={user.userId}>
            <span className="table-data">Username: {user.username}</span>
            <span className="table-data">
              Role: {user.admin ? "Admin" : "User"}
            </span>
            <span className="table-data">UserId: {user.userId}</span>
            <span className="table-data">First Name: {user.firstName}</span>
            <span className="table-data">Last Name: {user.lastName}</span>
            <span className="table-data">Phone: {user.phone}</span>
            <span className="table-data">Photo: {user.photoUrl}</span>
            <span className="table-data">
              State: {user.state ? "ative" : "disable"}
            </span>
            <div id="btn-edit-users-by-adim">
              <div>
                <BtnChangeRole
                  name={user.userId}
                  onClick={() => changeRole(user.userId)}
                />
              </div>
              <div>
                <BtnEditProfile
                  name={user.userId}
                  onClick={() => handleEditUserProfile(user.userId)}
                />
              </div>
              <div>
                <BtnDelete
                  name={user.userId}
                  value={user.username}
                  onClick={(event) => handleDeleteUser(event, user.userId)}
                />
                <div>
                  {userIdToDelete === user.userId && showConfirmBox && (
                    <ConfirmBox
                      onConfirm={handleConfirm}
                      onCancel={handleCancel}
                    />
                  )}
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
export default ShowUsers;
