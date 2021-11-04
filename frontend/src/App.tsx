import React from 'react';
import logo from './logo.svg';
import './App.css';
import { CreateMonitoring } from './CreateMonitoring';
import { MonitoringList} from './MonitoringList';
import { CreateMonitoringList, MonitoringListInterface, Service, Status } from './Types';

interface CreateMonitoringResponse {
    error?: string,
    service?: Service,
}

export class App extends React.Component<{}, MonitoringListInterface> {

  constructor(props: any) {
    super(props);
    this.state = {
      services: []
    };
  }

  createMonitoring: CreateMonitoringList["create"] = (name: string, url: string) => {
    const response = this.createMonitoring2(name, url);
    const error= response.error

    if (typeof error === 'undefined') {
      if (typeof response.service === 'undefined') {
        throw new Error('Expecting service if there is no error');
      }
      
      this.setState(prevState => ({
        services: [...prevState.services, response.service]
      }));
    } else {
      return error;
    }
  }

  createMonitoring2 = (name: string, url: string): CreateMonitoringResponse => {
    const service: Service = {
      name: name,
      url: url,
      status: Status.Pending,
      creationTime: new Date().toLocaleString(),
    };
    return {
        service
    }
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
