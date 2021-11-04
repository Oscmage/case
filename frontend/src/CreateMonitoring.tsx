import React from "react";
import { CreateMonitoringList } from "./Types";

interface CreateState {
    name: string;
    url: string;
}

const initialState = {
    name: "",
    url: "",
}

export class CreateMonitoring extends React.Component<CreateMonitoringList, CreateState> {
    constructor(props: any) {
      super(props);
      this.state = initialState;
    }

    // These two methods are duplicated, simplify
    handleInputChangeUrl = (event: any) => {
        const target = event.target;
        const value = target.value;
        this.setState({
            url: value
        });
    }

    handleInputChangeName = (event: any) => {
        const target = event.target;
        const value = target.value;
        this.setState({
            name: value
        });
    }

    handleSubmit = (event: any) => {
        this.props.create(this.state.name, this.state.url);
        this.setState(initialState);
        event.preventDefault();
    }
  
    render() {
      return (
        <form onSubmit={this.handleSubmit}>
            <label>
            Service Name:
            <input
                name="serviceName"
                type="text"
                value={this.state.name}
                onChange={this.handleInputChangeName} />
            </label>
            <br />
            <label>
                Url:
                <input
                name="urlToPoll"
                type="text"
                value={this.state.url}
                onChange={this.handleInputChangeUrl} />
            </label>
            <input type="submit" value="Submit" />
        </form>
      );
    }      
}
