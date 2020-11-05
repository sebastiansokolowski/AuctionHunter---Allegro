import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListTargetComponent from './components/ListTargetComponent';
import HeaderComponent from './components/HeaderComponent';
import ViewTargetComponent from './components/ViewTargetComponent';

function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {ListTargetComponent}></Route>
                          <Route path = "/add" component = {ViewTargetComponent}></Route>
                          <Route path = "/edit/:id" component = {ViewTargetComponent}></Route>
                    </Switch>
                </div>
        </Router>
    </div>
    
  );
}

export default App;
