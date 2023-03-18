import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import React from "react";


function ConfirmBox(onConfirm,onCancel) {
  return (
    <div
      className="Confirm box"
      style={{ display: 'block', position: 'initial' }}
    >
      <Modal.Dialog>
        <Modal.Header closeButton>
          <h3>Delete Confirm</h3>
        </Modal.Header>

        <Modal.Body>
          <p>Do you really want to delete the user?</p>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="primary" id="no" onClick={onCancel} >No</Button>
          <Button variant="secundary" id="yes" onClick={onConfirm} >Yes</Button>
        </Modal.Footer>
      </Modal.Dialog>
    </div>
  );
}

export default ConfirmBox;