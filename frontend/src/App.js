import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EventDetailPage from './EventDetailPage';
import CreateEventPage from './CreateEventPage';

function App() {
    return (
        <Router>
            <div className="App">
                <Switch>
                    <Route path="/event/:id" component={EventDetailPage} />
                    <Route path="/create-event" component={CreateEventPage} />
                </Switch>
            </div>
        </Router>
    );
}

export default App;