import React from "react";
import { MonitoringListProps, Service } from "./Types";

export class MonitoringList extends React.Component<MonitoringListProps, {}> {
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
