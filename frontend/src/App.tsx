import React from 'react';
import logo from './logo.svg';
import './App.css';
import { CreateMonitoring } from './CreateMonitoring';
//import { MonitoringList } from './MonitoringList';

export function App() {
  /*
  const service: Service = {
    name: "Test name",
    url: "Url"
  }
  */
  return (
    <div className="App">
      <div className="Form-Wrapper">
        <CreateMonitoring />
      </div>
    </div>
  );
}
