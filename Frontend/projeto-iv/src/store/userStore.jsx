import { create } from "zustand";
import { persist, createJSONStorage } from "zustand/middleware";
import React from "react";
//define the store
export const useStore = create(
  persist(
    (set) => ({
      username: "", //state variable
      updateName: (username) => set({ username }), //função para atualizar o estado da variável
      userId: "",
      updateUserId: (userId) => set({ userId }),
      token: "",
      updateToken: (token) => set({ token }),
      adminCredentials: "",
      updateAdminCredentials: (adminCredentials) => set({ adminCredentials }),
    }),
    {
      name: "my-store", //nome usado para os presisted data
      sotrage: createJSONStorage(() => sessionStorage), //mecanismo de armazenamento
    }
  )
);
