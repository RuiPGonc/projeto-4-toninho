import { useNavigate } from "react-router-dom";
import React from "react";



function HomePageButton(){
    const navigate = useNavigate();

 function goHome (event) {
    event.preventDefault();
    navigate("/home", { replace: true });

 }
    return(
        <div>
            <button onClick={goHome}>Home Page</button>
        </div>
    );

}
export default HomePageButton;