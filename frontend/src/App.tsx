import React from 'react';
import logo from './logo.svg';
import './App.css';
import { CreateMonitoring } from './CreateMonitoring';
import { MonitoringList } from './MonitoringList';
import { Service } from './Types';

export class App extends React.Component<{}, {}> {
  render() {
    const service: Service = {
      name: "Test name",
      url: "Url"
    }
    return (
      <div className="App">
        <div className="Form-Wrapper">
          <CreateMonitoring />
          <MonitoringList services={[service]}/>
        </div>
      </div>
    );
  }
}
