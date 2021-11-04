
export enum Status {
    Pending = "Pending",
    OK = "Ok",
    Error = "Error",
}

export interface Service {
    name: string,
    url: string,
    status: Status,
};

export interface MonitoringListInterface {
    services: Service[];
};

export interface CreateMonitoringList {
    create: (name: string, url: string) => void
};