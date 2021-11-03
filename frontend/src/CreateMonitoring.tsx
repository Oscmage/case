import React from "react";

interface CreateState {
    name: string;
    url: string;
}

export class CreateMonitoring extends React.Component<{}, CreateState> {
    constructor(props: any) {
      super(props);
      this.state = {
        name: "",
        url: "",
      };
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
        alert('Trying to submit url: ' + this.state.url);
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
