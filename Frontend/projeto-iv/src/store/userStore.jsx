import { create } from "zustand";
import { persist, createJSONStorage } from "zustand/middleware";
import React from "react";
import { backendLogout } from "../pages/logout/actions";

const initialState = {
  username: "",
  userId: "",
  token: "",
  adminCredentials: "",
};

//define the store
export const useStore = create(
  persist(
    (set, get) => ({
      ...initialState,
      updateName: (username) => set({ username }), //função para atualizar o estado da variável
      updateUserId: (userId) => set({ userId }),
      updateToken: (token) => set({ token }),
      updateAdminCredentials: (adminCredentials) => set({ adminCredentials }),
      logout: async (token, callback) => {
        backendLogout(token)
          .then(() => {
            set(initialState);
            if (typeof callback === "function") callback();
          })
          .catch((error) => {
            console.error("Logout error:", error);
          });
      }, // clears the entire store, actions included
    }),
    {
      name: "my-store", //nome usado para os presisted data
      storage: createJSONStorage(() => sessionStorage), //mecanismo de armazenamento
    }
  )
);
