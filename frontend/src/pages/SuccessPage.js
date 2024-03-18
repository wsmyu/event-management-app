import react from 'react'
import {useNavigate,useLocation} from "react-router-dom";
import React from "react";

const SuccessPage = ({eventId,message}) => {


    const navigate = useNavigate();
    return (
        <div className="container d-flex align-items-center justify-content-center"
             style={{height: "80vh"}}>
            <div className="d-flex flex-column align-items-center">
                <h1>{message}</h1>
                <button className="btn btn-primary" onClick={() => navigate(`/event/${eventId}`)}>View Event
                    Detail
                </button>
            </div>
        </div>

    )

}

export default SuccessPage;