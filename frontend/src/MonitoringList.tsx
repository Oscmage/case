import React from "react";
import { MonitoringListInterface, Service } from "./Types";

export class MonitoringList extends React.Component<MonitoringListInterface, {}> {
    render() {
        const listItems = this.props.services.map((service: Service) =>
            <li>Name: {service.name}, Url: {service.url}</li>
        );
        return (
            <ul>
                {listItems}
            </ul>
        );
    }
}
