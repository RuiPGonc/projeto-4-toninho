import Form from 'react-bootstrap/Form';
import React from "react";


function switchButton({label,handleSwitchChange,switchValue}) {
  return (
    <Form>
      <Form.Check 
        type="switch"
        id="custom-switch"
        label={label}
        value={switchValue}
        onChange={handleSwitchChange}
      />
    </Form>
  );
}

export default switchButton;