import React from 'react';
import logo from './logo.svg';
import './App.css';
import { CreateMonitoring } from './CreateMonitoring';
import { MonitoringList} from './MonitoringList';
import { CreateMonitoringList, CreateMonitoringResponse, MonitoringListInterface, Service, Status } from './Types';

import { Client } from '@stomp/stompjs';

const SOCKET_URL = 'ws://localhost:8080/ws-monitoring';

export class App extends React.Component<{}, MonitoringListInterface> {

  constructor(props: any) {
    super(props);
    this.state = {
      services: []
    };
  }

  componentDidMount() {
    let currentComponent = this;
    let onConnected = () => {
      console.log("Connected!!")
      client.subscribe('/topic/message', function (msg) {
        if (msg.body) {
          var jsonBody = JSON.parse(msg.body);
          if (jsonBody.message) {
            //currentComponent.setState({ messages: jsonBody.message })
            console.log(jsonBody)
          }
        }
      });
    }

    let onDisconnected = () => {
      console.log("Disconnected!!")
    }

    const client = new Client({
      brokerURL: SOCKET_URL,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: onConnected,
      onDisconnect: onDisconnected
    });

    client.activate();
  };


  createMonitoring: CreateMonitoringList["create"] = (name: string, url: string) => {
    // TODO: Split validation/parsing and action.
    const response = this.createMonitoring2(name, url);
    const error= response.error

    if (typeof error === 'undefined') {
      if (typeof response.service === 'undefined') {
        return "Internal error";
      }
      
      this.setState(prevState => ({
        services: [...prevState.services, response.service]
      }));
      return null;
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
