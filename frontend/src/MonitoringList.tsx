import React from "react";
import { MonitoringListInterface, Service } from "./Types";
import './MonitoringList.css';

export class MonitoringList extends React.Component<MonitoringListInterface, {}> {
    render() {
        const listItems = this.props.services.map((service: Service) =>
            <li>
                <div className="service-text-info-wrapper">
                    <label>Name: {service.name}</label>
                    <label>Url: {service.url}</label>
                </div>
                <div className="service-status-wrapper tooltip">
                    <span className="dot"></span>
                    <span className="tooltiptext">{service.status}</span>
                </div>
            </li>
        );
        return (
            <div>
                <ul className="monitoring-list">
                    {listItems}
                </ul>
            </div>
        );
    }
}
