import Form from 'react-bootstrap/Form';
import React from "react"

function CheckBox(title, defaultChecked, onClick) {
  return (
    <Form>
             
          <Form.Check 
            type="checkbox"
            title={title}
            id={`default-cheqbox`}
            defaultChecked={defaultChecked}
            onClick={onClick}
          />
    
    </Form>
  );
}

export default CheckBox;