import React from "react";

export default function Input({type, text,name,placeholder,handleOnChange,defaultValue}){
return (
    <div className="input">
        <label htmlFor={name}>{text}:</label>
        <input
         type={type} 
         name={name}
         id= {name}
         placeholder={placeholder}
         onChange={handleOnChange}
         defaultValue={defaultValue}/>
    </div>
)

}