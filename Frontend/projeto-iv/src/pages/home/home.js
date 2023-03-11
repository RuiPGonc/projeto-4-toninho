import React, { useContext, useEffect } from "react";
import Sidebar from "../../components/navbar/Sidebar";
import { useNavigate } from "react-router-dom";
import "./styles.css";

import { AppContext } from "../../router/index";

function Home() {
  const { isLogin } = useContext(AppContext);
  const navigate = useNavigate();

  const{token}=useContext(AppContext);


  useEffect(() => {
    if (!isLogin) {
      navigate("/", { replace: true });
    }
  }, [isLogin]);

  console.log(token);

  return (
    <div className="Home" id="home-outer-container">
      <Sidebar
        pageWrapId={"home-page-wrap"}
        outerContainerId={"home-outer-container"}
      />
      <div className="page-wrap" id="home-page-wrap">
        <h1>Home</h1>
      </div>
    </div>
  );
}

export default Home;
