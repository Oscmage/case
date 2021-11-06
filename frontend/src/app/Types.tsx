export enum Status {
  Pending = "Pending",
  Ok = "Ok",
  Error = "Error",
}

export interface Service {
  reference: string;
  name: string;
  url: string;
  status: Status;
  creationTime: string;
}

export interface MonitoringDict {
  services: { [id: string]: Service };
}

export interface CreateMonitoringList {
  create: (name: string, url: string) => Promise<string | null>;
}

export interface CreateMonitoringResponse {
  error?: string;
  service?: Service;
}
