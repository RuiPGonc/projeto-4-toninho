import React from "react";
export default function Select({
  text,
  name,
  options = [],
  handleOnChange,
  value,
}) {
  return (
    <div className="Select_input">
      <label htmlFor={name}>{text}:</label>
      <select
        name={name}
        id={name}
        onChange={handleOnChange}
        value={value || ""}
      >
        <option value="" disabled>
          Select one Category
        </option>
        {options.map((option) => (
          <option
            key={option.categoryId}
            value={option.categoryId}
            id={option.categoryId}
          >
            {option.title}
          </option>
        ))}
      </select>
    </div>
  );
}
