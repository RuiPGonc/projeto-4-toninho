import React from "react";

export default function Input({
  type,
  text,
  name,
  placeholder,
  handleOnChange,
  value,
  defaultValue,
}) {
  return (
    <div className="input">
      <label htmlFor={name}>{text}:</label>
      <input
        type={type}
        name={name}
        id={name}
        placeholder={placeholder}
        onChange={handleOnChange}
        value={value || defaultValue}
      />
    </div>
  );
}
