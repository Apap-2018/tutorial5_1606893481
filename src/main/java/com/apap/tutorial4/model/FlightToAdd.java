package com.apap.tutorial4.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esak on 10/11/2018.
 */
public class FlightToAdd {
    private List<FlightModel> flights;
    private PilotModel pilot;

    public FlightToAdd(PilotModel pilot) {
        this.flights = new ArrayList<FlightModel>();
        this.pilot = pilot;
        flights.add(new FlightModel());
    }

    public List<FlightModel> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightModel> flights) {
        this.flights = flights;
    }

    public PilotModel getPilot() {
        return pilot;
    }

    public void setPilot(PilotModel pilot) {
        this.pilot = pilot;
    }
}
