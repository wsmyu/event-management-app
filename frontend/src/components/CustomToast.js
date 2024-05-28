import React from 'react';
import Toast from 'react-bootstrap/Toast';
import ToastContainer from "react-bootstrap/ToastContainer";

const CustomToast = ({ showToast, setShowToast, toastVariant, toastMessage,testID }) => {
    return (
        <ToastContainer
            className="p-3"
            position='top-end'
            style={{zIndex: 1}}
        >
        <Toast
            show={showToast}
            onClose={() => setShowToast(false)}
            delay={3000}
            autohide
            className={`bg-${toastVariant}`}
        >
            <Toast.Body className="toast-body" data-testid={testID}>{toastMessage}</Toast.Body>
        </Toast>
        </ToastContainer>

    );
};

export default CustomToast;
