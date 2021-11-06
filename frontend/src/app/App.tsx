import React from "react";

import { Client } from "@stomp/stompjs";
import axios from "axios";
import { SOCKET_URL, LIST_URL, CREATE_URL } from "./Constants";
import {
  MonitoringDict,
  Service,
  CreateMonitoringList,
  CreateMonitoringResponse,
} from "./Types";
import { CreateMonitoring } from "./create_monitoring/CreateMonitoring";
import { ServiceList } from "./service_list/ServiceList";

export class App extends React.Component<{}, MonitoringDict> {
  constructor(props: any) {
    super(props);
    this.state = {
      services: {},
    };
  }

  _onSocketEvent = (msg) => {
    if (msg.body) {
      var jsonBody = JSON.parse(msg.body);
      const oldState = this.state.services;
      const newServiceValue: Service = {
        reference: jsonBody.reference,
        name: jsonBody.name,
        url: jsonBody.url,
        status: jsonBody.status,
        creationTime: jsonBody.creationTime,
      };
      const newState = {
        ...oldState,
        [newServiceValue.reference]: newServiceValue,
      };

      this.setState({
        services: newState,
      });
    }
  };

  componentDidMount = () => {
    this.loadServices();

    let onConnected = () => {
      console.log("Socket connection established!");
      client.subscribe("/topic/monitoring", this._onSocketEvent);
    };

    let onDisconnected = () => {
      console.log("Disconnected socket!");
    };

    const client = new Client({
      brokerURL: SOCKET_URL,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: onConnected,
      onDisconnect: onDisconnected,
    });

    client.activate();
  };

  loadServices = () => {
    axios
      .get(LIST_URL)
      .then((res) => {
        const list: any[] = res.data;
        let services = {};
        for (const item of list) {
          services[item.reference] = {
            reference: item.reference,
            name: item.name,
            url: item.url,
            status: item.status,
            creationTime: item.creationTime,
          };
        }
        this.setState({
          services,
        });
        console.log("Initial load of services complete");
        console.log(services);
      })
      .catch((err) => {
        console.error("Failed loading services on initilization");
      });
  };

  createMonitoring: CreateMonitoringList["create"] = async (
    name: string,
    url: string
  ) => {
    // TODO: Split validation/parsing and action.
    const response = await this._createMonitoring(name, url);
    const error = response.error;

    if (typeof error === "undefined") {
      if (typeof response.service === "undefined") {
        return "Internal error";
      }
      const oldState = this.state.services;
      const newState = {
        ...oldState,
        [response.service.reference]: response.service,
      };
      this.setState(() => ({
        services: newState,
      }));

      return null;
    } else {
      return error;
    }
  };

  _createMonitoring = async (
    name: string,
    url: string
  ): Promise<CreateMonitoringResponse> => {
    const data = {
      name: name,
      url: url,
    };

    return await axios
      .post(CREATE_URL, data)
      .then((res) => {
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
            service,
          };
        } else {
          return {
            error: res.data,
          };
        }
      })
      .catch((err) => {
        return {
          error: err,
        };
      });
  };

  render() {
    return (
      <div className="App">
        <div className="Form-Wrapper">
          <CreateMonitoring create={this.createMonitoring} />
          <ServiceList services={this.state.services} />
        </div>
      </div>
    );
  }
}
