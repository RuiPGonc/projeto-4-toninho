import React from "react";
import { cleanStorage } from "../../store/cleanStorage";
import { useNavigate } from "react-router-dom";
import { useStore } from "../../store/userStore";
import { backendLogout } from "./actions";
import BtnLogout from "./components/btnLogout";

export default function Logout() {
  const navigate = useNavigate();
  const token = useStore((state) => state.token);
  const logout = useStore((state) => state.logout);
  const handleLogout = () => {
    logout(token, () => {
      navigate("/");
    });
  };

  return <BtnLogout onClick={handleLogout} />;
}
