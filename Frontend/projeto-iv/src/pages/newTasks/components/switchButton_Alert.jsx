import Form from 'react-bootstrap/Form';
import React from "react";


function SwitchButton_Alert(handleSwitchChange,switchValue) {
  return (
    <Form>
      <Form.Check 
        type="switch"
        id="custom-switch"
        label="Alert"
        value="switchValue"
        onChange={handleSwitchChange}
      />
    </Form>
  );
}

export default SwitchButton_Alert;