
export interface Service {
    name: string,
    url: string,
};

export interface MonitoringListInterface {
    services: Service[];
};

export interface CreateMonitoringList {
    create: (name: string, url: string) => void
};