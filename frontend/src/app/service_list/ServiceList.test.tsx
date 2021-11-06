import { render, screen } from "@testing-library/react";
import { Service, Status } from "../Types";
import { ServiceList } from "./ServiceList";

test("Test renders element with url", () => {
  const service: Service = {
    reference: "ref",
    name: "name",
    url: "http://localhost:3000",
    status: Status.Ok,
    creationTime: "2021-11-06",
  };
  const props = {
    [service.reference]: service,
  };
  render(<ServiceList services={props} />);
  const linkElement = screen.getByText(/localhost/i);
  expect(linkElement).toBeInTheDocument();
});
