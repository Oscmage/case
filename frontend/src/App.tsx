import React from 'react';
import logo from './logo.svg';
import './App.css';
import { CreateMonitoring } from './CreateMonitoring';
import { MonitoringList} from './MonitoringList';
import { CreateMonitoringList, MonitoringListInterface, Service, Status } from './Types';

export class App extends React.Component<{}, MonitoringListInterface> {

  constructor(props: any) {
    super(props);
    this.state = {
      services: []
    };
  }

  createMonitoring: CreateMonitoringList["create"] = (name: string, url: string) => {
    const service: Service = {
      name: name,
      url: url,
      status: Status.Pending,
      creationTime: new Date().toLocaleString(),
    };
    
    this.setState(prevState => ({
      services: [...prevState.services, service]
    }));
  }

  render() {
    return (
      <div className="App">
        <div className="Form-Wrapper">
          <CreateMonitoring create={this.createMonitoring} />
          <MonitoringList services={this.state.services}/>
        </div>
      </div>
    );
  }
}
