import React, { useState, useEffect, useCallback } from 'react';
import { useUser } from '../components/UserContext';
import {  Button, Alert, Container } from 'react-bootstrap';
import moment from 'moment'; // For handling dates easily
import TasksDisplay from '../components/TasksDisplay';
import TaskModal from '../components/TaskModal';

// TaskManagementPage component
const TaskManagementPage = () => {
    const { loggedInUser } = useUser();
    const [tasks, setTasks] = useState([]);
    const [showTaskModal, setShowTaskModal] = useState(false);
    const [taskDescription, setTaskDescription] = useState('');
    const [invitations, setInvitations] = useState([]); // Added state for invitations
    const [selectedEvent, setSelectedEvent] = useState(null); // Added state for selected event
    const [selectedTask, setSelectedTask] = useState(null); // For edit purposes
 const toggleTaskModal = () => setShowTaskModal(!showTaskModal);

 const handleEventSelect = (invitation) => {
        setSelectedEvent(invitation);
        setShowTaskModal(true); // Open the task modal directly
    };

 // Handle change in task description input
 const handleTaskDescriptionChange = (event) => {
     setTaskDescription(event.target.value);
 };
   // Fetch tasks belonging to the logged-in user
    const fetchTasks = useCallback(async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/tasks/user/${loggedInUser.userId}`, {
                headers: {
                    'Authorization': `Bearer ${loggedInUser.token}`,
                },
            });
            if (!response.ok) {
                throw new Error('Failed to fetch tasks');
            }
            const data = await response.json();
            setTasks(data);
        } catch (error) {
            console.error('Error fetching tasks:', error);
        }
    }, [loggedInUser]);

// Fetch invitations along with event details
const fetchInvitations = useCallback(async () => {
  if (!loggedInUser || !loggedInUser.userId) return;

  try {
    const response = await fetch(`http://localhost:8080/api/guests/user/${loggedInUser.userId}/invitations`, {
      headers: { 'Authorization': `Bearer ${loggedInUser.token}` },
    });
    if (!response.ok) throw new Error('Failed to fetch invitations');

    let invitationsData = await response.json();
    invitationsData = invitationsData.filter(invitation => invitation.status === 'accepted'); // Filter by accepted invitations

    // Fetch event details for each invitation
    const invitationsWithDetails = await Promise.all(invitationsData.map(async (invitation) => {
      const eventDetails = await fetchEventDetails(invitation.eventId);
      return { ...invitation, eventName: eventDetails.eventName, eventDate: eventDetails.eventDate };
    }));

    setInvitations(invitationsWithDetails);
  } catch (error) {
    console.error('Error fetching invitations:', error);
  }
}, [loggedInUser]);

const fetchEventDetails = async (eventId) => {
  try {
    const response = await fetch(`http://localhost:8080/api/events/${eventId}`, {
      headers: { 'Authorization': `Bearer ${loggedInUser.token}` },
    });
    if (!response.ok) {
      throw new Error(`Failed to fetch event details for event ID: ${eventId}`);
    }
    const data = await response.json();
    return { eventName: data.eventName, eventDate: data.eventDate }; // Return both name and date
  } catch (error) {
    console.error('Error fetching event details:', error);
    return { eventName: 'Unknown Event', eventDate: 'Unknown Date' }; // Fallback values
  }
};


   useEffect(() => {
       fetchTasks();
       fetchInvitations(); // Fetch invitations
   }, [fetchTasks, fetchInvitations]);

// Add or Update Task based on the presence of selectedTask
const saveTask = async (taskDetails) => {
    const url = taskDetails.taskId ? `http://localhost:8080/api/tasks/${taskDetails.taskId}/description` : `http://localhost:8080/api/tasks/`;
    const method = taskDetails.taskId ? 'PATCH' : 'POST';
    const body = JSON.stringify({
        ...taskDetails,
        userId: loggedInUser.userId,
        status: false,
    });

    try {
        const response = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${loggedInUser.token}` },
            body
        });

        if (!response.ok) {
            throw new Error('Failed to save task');
        }

        const savedTask = await response.json();


        setTasks(prevTasks => [...prevTasks, savedTask]);

        // Close the modal and reset task-related states
        setShowTaskModal(false);
        setSelectedTask(null);
        setTaskDescription('');
        setSelectedEvent(null);

    } catch (error) {
        console.error('Error saving task:', error);
        // Optionally, set an error state to display the error to the user
    }
};


// Function to prepare editing a task
const editTask = (task) => {
    setSelectedTask(task); // Set the selected task for editing
    setTaskDescription(task.taskDescription); // Set initial task description
    toggleTaskModal(); // Show the task modal for editing
};

// Delete Task
const deleteTask = async (taskId) => {
    try {
        const response = await fetch(`http://localhost:8080/api/tasks/${taskId}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${loggedInUser.token}` },
        });
        if (!response.ok) {
            throw new Error('Failed to delete task');
        }
        await fetchTasks(); // Refresh task list
    } catch (error) {
        console.error('Error deleting task:', error);
    }
};


// Helper function to categorize tasks by their due dates
const categorizeTasksByDueDate = () => {
    // Categorize tasks based on due date
    const categories = {
        withinDay: [],
        withinWeek: [],
        withinMonth: [],
        later: []
    };
    tasks.forEach(task => {
        const today = moment();
        const eventDate = moment(task.eventDate);
        if (eventDate.diff(today, 'days') <= 1) {
            categories.withinDay.push(task);
        } else if (eventDate.diff(today, 'days') <= 7) {
            categories.withinWeek.push(task);
        } else if (eventDate.diff(today, 'days') <= 30) {
            categories.withinMonth.push(task);
        } else {
            categories.later.push(task);
        }
    });
    return categories;
};

const changeTaskStatus = async (taskId, newStatus) => {
    try {
        await fetch(`http://localhost:8080/api/tasks/${taskId}/status`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${loggedInUser.token}` },
            body: JSON.stringify(newStatus),
        });
        fetchTasks(); // Refresh the list of tasks
    } catch (error) {
        console.error('Error changing task status:', error);
    }
};

const updateTaskDescription = async (taskId, description) => {
    try {
        await fetch(`http://localhost:8080/api/tasks/${taskId}/description`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${loggedInUser.token}` },
            body: JSON.stringify(description),
        });
        fetchTasks(); // Refresh the list of tasks to show the updated description
    } catch (error) {
        console.error('Error updating task description:', error);
    }
};

    useEffect(() => {
        if (loggedInUser) {
            fetchTasks();
        }
    }, [fetchTasks, loggedInUser]);

   return (
        <Container className="mt-5">
            <h2 className="mb-4">My Tasks</h2>
            {/* Display Invited Events */}
            {invitations.length > 0 ? (
              <div>
                <h3>Invited Events</h3>
                {invitations.map((invitation) => (
                  <Button
                      key={invitation.eventId}
                      variant="outline-primary"
                      className="m-2"
                      onClick={() => handleEventSelect(invitation)}>
                    {`${invitation.eventName} - ${moment(invitation.eventDate).format('LL')}`}
                  </Button>
                ))}
              </div>
            ) : (
              <Alert variant="info">No invited events.</Alert>
            )}

            {/* Display Tasks */}
            <TasksDisplay
                tasks={tasks}
                editTask={editTask}
                deleteTask={deleteTask}
                fetchEventDetails={fetchEventDetails}
                changeTaskStatus={changeTaskStatus}
                updateTaskDescription={updateTaskDescription}
            />
            
            {/* Task Modal */}
            <TaskModal
                show={showTaskModal}
                handleClose={() => setShowTaskModal(false)}
                handleSave={saveTask}
                taskDescription={taskDescription}
                handleTaskDescriptionChange={handleTaskDescriptionChange}
                selectedEvent={selectedEvent}
            />
        </Container>
    );
};
export default TaskManagementPage;