import React, { useState, useEffect } from 'react';
import { ListGroup, Button, Form, Spinner } from 'react-bootstrap';
const TaskItem = ({ task, deleteTask, changeTaskStatus, updateTaskDescription, currentlyEditing, setCurrentlyEditing, tempDescription, setTempDescription }) => {

    // Determine if this task is currently being edited
    const isEditing = currentlyEditing === task.taskId;

    const handleEdit = () => {
        setCurrentlyEditing(task.taskId);
        setTempDescription(task.taskDescription); // Initialize temporary description with current task description
    };

    const handleSave = () => {
        updateTaskDescription(task.taskId, tempDescription);
        setCurrentlyEditing(null); // Clear currently editing state
    };

    const handleCancel = () => {
        setCurrentlyEditing(null); // Exit editing mode without saving
    };

    return (
        <ListGroup.Item className="d-flex justify-content-between align-items-center">
            {isEditing ? (
                <>
                    <Form.Control
                        type="text"
                        value={tempDescription}
                        onChange={(e) => setTempDescription(e.target.value)}
                    />
                    <div>
                        <Button variant="success" size="sm" onClick={handleSave}>Save</Button>
                        <Button variant="secondary" size="sm" onClick={handleCancel}>Cancel</Button>
                    </div>
                </>
            ) : (
                <>
                    {task.taskDescription}
                    <div>
                        <Button variant="custom-button" size="sm" onClick={handleEdit}>Edit</Button>
                        <Button variant="danger" size="sm" onClick={() => deleteTask(task.taskId)}>Delete</Button>
                        <Button variant={task.status ? "success" : "secondary"} size="sm" onClick={() => changeTaskStatus(task.taskId, !task.status)}>
                            {task.status ? 'Completed' : 'Incomplete'}
                        </Button>
                    </div>
                </>
            )}
        </ListGroup.Item>
    );
};

const TasksDisplay = ({ tasks, deleteTask, changeTaskStatus, fetchEventDetails, updateTaskDescription }) => {
    const [eventNames, setEventNames] = useState({});
    const [isEditing, setIsEditing] = useState(false);
    const [editedDescription, setEditedDescription] = useState('');
    const [currentlyEditing, setCurrentlyEditing] = useState(null);
    const [tempDescription, setTempDescription] = useState("");

    useEffect(() => {
        const fetchAllEventDetails = async () => {
            const uniqueEventIds = [...new Set(tasks.map(task => task.eventId))];
            const eventDetailsPromises = uniqueEventIds.map(eventId => fetchEventDetails(eventId));
            const results = await Promise.all(eventDetailsPromises);

            const names = results.reduce((acc, result, index) => {
                acc[uniqueEventIds[index]] = result.eventName;
                return acc;
            }, {});

            setEventNames(names);
        };

        if (tasks.length > 0) {
            fetchAllEventDetails();
        }
    }, [tasks, fetchEventDetails]);

    // Group tasks by eventId
    const groupedTasks = tasks.reduce((acc, task) => {
        (acc[task.eventId] = acc[task.eventId] || []).push(task);
        return acc;
    }, {});

return (
    <div>
        {Object.entries(groupedTasks).map(([eventId, tasksInGroup]) => (
            <div key={eventId}>
                <h3>{eventNames[eventId] || <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true" />}</h3>
                <ListGroup>
                    {tasksInGroup.map(task => (
                        <TaskItem
                            key={task.taskId}
                            task={task}
                            deleteTask={deleteTask}
                            changeTaskStatus={changeTaskStatus}
                            updateTaskDescription={updateTaskDescription}
                            currentlyEditing={currentlyEditing}
                            setCurrentlyEditing={setCurrentlyEditing}
                            tempDescription={tempDescription}
                            setTempDescription={setTempDescription}
                        />
                    ))}
                </ListGroup>
            </div>
        ))}
    </div>
);
};

export default TasksDisplay;
