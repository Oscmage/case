
export interface Service {
    name: string,
    url: string,
}

export interface MonitoringListProps {
    services: Service[];
}