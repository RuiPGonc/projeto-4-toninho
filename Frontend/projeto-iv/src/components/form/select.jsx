import React from "react";
export default function Select({ text, name, options, handleOnChange, value }) {
  return (
    <div className="Select_input">
      <label htmlFor={name}>{text}:</label>
      <select
        name={name}
        id={name}
        onChange={handleOnChange}
        value={value || ""}
      >
        <option>Select one Category</option>
        {options.map((option) => (
          <option value={option.id} key={option.id}>
            {option.title}
          </option>
        ))}
      </select>
    </div>
  );
}
