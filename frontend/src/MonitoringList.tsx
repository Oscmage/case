import React from "react";
import { MonitoringListProps } from "./Types";

export class MonitoringList extends React.Component<MonitoringListProps, {}> {

    constructor(props: MonitoringListProps) {
      super(props);
    }
  
    render() {
        const listItems = this.props.services.map((number) =>
                <li>{number}</li>
        );
        return (
            <ul>
                {listItems}
            </ul>
        );
    }      
}
