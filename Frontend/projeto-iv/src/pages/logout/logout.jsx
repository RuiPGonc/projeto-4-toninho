import React from "react"
import { cleanStorage } from "../../store/cleanStorage";
import { useNavigate } from "react-router-dom";
import { useStore } from "../../store/userStore";
import { backendLogout } from "./actions";
import BtnLogout from "./components/btnLogout";

export default function Logout() {
  const navigate = useNavigate();
  const token = useStore((state) => state.token);

  function handleLogout(){
  backendLogout(token)
    .then(() => {
      cleanStorage().then(() => navigate("/", { replace: true }));
    })
    .catch((error) => {
      console.error("Logout error:", error);
    });
}
  return (
    <BtnLogout onClick={handleLogout}/>
  )
}
