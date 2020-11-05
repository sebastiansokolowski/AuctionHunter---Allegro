import React from 'react';
import './App.css';
import {HashRouter, Route, Switch} from 'react-router-dom'
import ListTargetComponent from './components/ListTargetComponent';
import HeaderComponent from './components/HeaderComponent';
import ViewTargetComponent from './components/ViewTargetComponent';

function App() {
  return (
    <div>
        <HashRouter>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {ListTargetComponent}></Route>
                          <Route path = "/add" component = {ViewTargetComponent}></Route>
                          <Route path = "/edit/:id" component = {ViewTargetComponent}></Route>
                    </Switch>
                </div>
        </HashRouter>
    </div>
    
  );
}

export default App;
