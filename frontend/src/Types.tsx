
export enum Status {
    Pending = "Pending",
    Ok = "Ok",
    Error = "Error",
}

export interface Service {
    name: string,
    url: string,
    status: Status,
    creationTime: string,
};

export interface MonitoringListInterface {
    services: Service[];
};

export interface CreateMonitoringList {
    create: (name: string, url: string) => string | null
};

export interface CreateMonitoringResponse {
    error?: string,
    service?: Service,
}
