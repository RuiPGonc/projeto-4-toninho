import React, { useContext, useEffect } from "react";
import Sidebar from "../../components/navbar/Sidebar";
import { useNavigate } from "react-router-dom";

import { AppContext } from "../../router/index";

function Profile() {
  const { isLogin } = useContext(AppContext);
  const navigate = useNavigate();
  useEffect(() => {
    if (!isLogin) {
      navigate("/", { replace: true });
    }
  }, [isLogin]); 



  return (
    <div className="Profile" id="profile-outer-container">
      <Sidebar
        pageWrapId={"profile-page-wrap"}
        outerContainerId={"profile-outer-container"}
      />
      <div className="page-wrap" id="profile-page-wrap">
        <h1>My Profile</h1>
      </div>
    </div>
  );
}

export default Profile;
