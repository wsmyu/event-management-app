import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import moment from 'moment';

const TaskModal = ({ show, handleClose, handleSave, taskDescription, handleTaskDescriptionChange, selectedEvent }) => {

    const onSave = () => {
        handleSave({
            taskDescription,
            eventId: selectedEvent ? selectedEvent.eventId : null,
            eventDate: selectedEvent ? selectedEvent.eventDate : null,
        });
        handleClose();
    };

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Add Task for {selectedEvent ? `${selectedEvent.eventName} - ${moment(selectedEvent.eventDate).format('LL')}` : ''}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3">
                        <Form.Label>Task Description</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Enter task description"
                            value={taskDescription}
                            onChange={handleTaskDescriptionChange}
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>Close</Button>
                <Button variant="primary" onClick={onSave}>Save Changes</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default TaskModal;