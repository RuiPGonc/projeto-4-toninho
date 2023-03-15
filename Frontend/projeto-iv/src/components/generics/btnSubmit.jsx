import React from "react";
export default function SubmitButton({text,onClick}){
    return(
        <div>
           <button onClick={onClick}>{text}</button>
        </div>
    )
}