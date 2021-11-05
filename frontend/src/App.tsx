import React from 'react';
import logo from './logo.svg';
import './App.css';
import { CreateMonitoring } from './CreateMonitoring';
import { MonitoringList} from './MonitoringList';
import { CreateMonitoringList, CreateMonitoringResponse, MonitoringDict, Service, Status } from './Types';
import update from 'react-addons-update';

import { Client } from '@stomp/stompjs';
import axios from 'axios';
import { STATUS_CODES } from 'http';

const SOCKET_URL = 'ws://localhost:8080/ws-monitoring';
const CREATE_URL = 'http://localhost:8080/create'

export class App extends React.Component<{}, MonitoringDict> {

  constructor(props: any) {
    super(props);
    this.state = {
      services: {}
    };
  }

  componentDidMount = () => {
    let onConnected = () => {
      console.log("Connected!!")
      client.subscribe('/topic/monitoring', (msg) => {
        console.log("Msg received!")

        if (msg.body) {
          var jsonBody = JSON.parse(msg.body);
          console.log(jsonBody);
          const oldState = this.state.services;
          const newServiceValue: Service = {
            reference: jsonBody.reference,
            name: jsonBody.name,
            url: jsonBody.url,
            status: jsonBody.status,
            creationTime: jsonBody.creationTime,
          };
          const newState = {...oldState, [newServiceValue.reference]: newServiceValue};
          
          this.setState({
            services: newState
          });
        }
      });
    }
    //onConnected.bind(this);

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

  createMonitoring: CreateMonitoringList["create"] = async (name: string, url: string) => {
    // TODO: Split validation/parsing and action.
    const response = await this.createMonitoring2(name, url);
    const error = response.error

    if (typeof error === 'undefined') {
      if (typeof response.service === 'undefined') {
        return "Internal error";
      }
      const oldState = this.state.services
      const newState = {...oldState, [response.service.reference]: response.service};
      this.setState(() => ({
        services: newState
      }));

      return null;
    } else {
      return error;
    }
  }

  createMonitoring2 = async (name: string, url: string): Promise<CreateMonitoringResponse> => {
    const data = {
      name: name,
      url: url,
    }

    return await axios.post(
      CREATE_URL,
      data,
    ).then((res) => {
      // TODO: Can't seem to find a javascript class with HTTP status code mapping instead of just doing something like this.
      if (res.status === 201) {
        // TODO: This should be deserilization using JSON.
        const service: Service = {
          reference: res.data.reference,
          name: res.data.name,
          url: res.data.url,
          status: res.data.status,
          creationTime: res.data.creationTime,
        };
        console.log(res.data);
        return {
            service
        }
      } else {
        return {
          error: res.data
        };
      }
    }).catch((err) => {
      return {
        error: err
      };
    });
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
