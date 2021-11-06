import { render, screen } from "@testing-library/react";
import { App } from "./App";

test("Test render add monitoring", () => {
  render(<App />);
  const linkElement = screen.getByText(/Add monitoring/i);
  expect(linkElement).toBeInTheDocument();
});
