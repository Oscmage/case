import { MonitoringDict } from '../Types';
import './MonitoringList.css';

export const MonitoringList = (props: MonitoringDict) => {
    const listItems = Object.entries(props.services).map(([_, service]) =>
            <li key={service.url}>
                <div className="service-text-info-wrapper">
                    <label>Name: {service.name}</label>
                    <label>Url: {service.url}</label>
                    <label>Created: {service.creationTime}</label>
                </div>
                <div className="service-status-wrapper tooltip">
                    <span className={"dot " + service.status}></span>
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