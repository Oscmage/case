import React from "react";
import { CreateMonitoringList } from "../Types";
import "./CreateMonitoring.css";

interface CreateState {
  name: string;
  url: string;
}

const initialState = {
  name: "",
  url: "",
};

export class CreateMonitoring extends React.Component<
  CreateMonitoringList,
  CreateState
> {
  constructor(props: any) {
    super(props);
    this.state = initialState;
  }

  render() {
    return (
      <form className="monitoring-form" onSubmit={this.handleSubmit}>
        <div className="form-row">
          <label>Service Name:</label>
          <input
            name="serviceName"
            type="text"
            value={this.state.name}
            onChange={this.handleInputChangeName}
          />
        </div>
        <div className="form-row">
          <label>Url:</label>
          <input
            name="urlToPoll"
            type="text"
            value={this.state.url}
            onChange={this.handleInputChangeUrl}
          />
        </div>
        <input type="submit" value="Add monitoring" />
      </form>
    );
  }

  handleInputChangeUrl = (event: any) => {
    const target = event.target;
    const value = target.value;
    this.setState({
      url: value,
    });
  };

  handleInputChangeName = (event: any) => {
    const target = event.target;
    const value = target.value;
    this.setState({
      name: value,
    });
  };

  handleSubmit = async (event: any) => {
    event.preventDefault();

    const error: string | null = await this.props.create(
      this.state.name,
      this.state.url
    );
    if (error !== null) {
      alert("Received error: " + error);
    } else {
      this.setState(initialState);
    }
  };
}
