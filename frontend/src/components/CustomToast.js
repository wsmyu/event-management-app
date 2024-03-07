import React from 'react';
import Toast from 'react-bootstrap/Toast';
import ToastContainer from "react-bootstrap/ToastContainer";

const CustomToast = ({ showToast, setShowToast, toastVariant, toastMessage }) => {
    return (
        <ToastContainer
            className="p-3"
            position='top-end'
            style={{zIndex: 1}}
        >
        <Toast
            show={showToast}
            onClose={() => setShowToast(false)}
            delay={5000}
            autohide
            className={`bg-${toastVariant}`}
        >
            <Toast.Header closeButton={false}>
                <strong className="mr-auto">Notification</strong>
            </Toast.Header>
            <Toast.Body className="toast-body">{toastMessage}</Toast.Body>
        </Toast>
        </ToastContainer>

    );
};

export default CustomToast;
