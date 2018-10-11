package com.apap.tutorial4.controller;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.FlightToAdd;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;
import com.sun.rowset.internal.Row;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by esak on 10/4/2018.
 */

@Controller
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private PilotService pilotService;

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
    private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {

        PilotModel pilot = pilotService.getPilotDataByLicenseNumber(licenseNumber);
        FlightToAdd flights = new FlightToAdd(pilot);
        model.addAttribute("flights", flights);
        return "addFlight";
    }

    @RequestMapping(value = "/flight/add/{licenseNumber}", params={"submit"})
    private String addFlightSubmit(@ModelAttribute FlightToAdd flightsToAdd) {
        List<FlightModel> flights = flightsToAdd.getFlights();
        for (FlightModel flight : flights) {
            flight.setPilot(flightsToAdd.getPilot());
            flightService.addFlight(flight);
        }
        return "add";
    }

    @RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
    private String delete(@ModelAttribute PilotModel pilot, Model model) {
        for(FlightModel flight : pilot.getPilotFlight()) {
            flightService.deleteFlightById(flight.getId());
        }

        return "delete";
    }

    @RequestMapping(value="/flight/update/{flightNumber}", method = RequestMethod.GET)
    private String updateFlight(@PathVariable(value="flightNumber") String flightNumber, Model model) {
        FlightModel flight = flightService.getDataByFlightNumber(flightNumber);

        model.addAttribute("flight",flight);

        return "updateFlight";
    }

    @RequestMapping(value = "/flight/update", method = RequestMethod.POST)
    private String updateFlightSubmit(@ModelAttribute FlightModel flight) {
        flightService.updateFlight(flight);
        return "update";
    }

    @RequestMapping("/flight/view")
    private String viewFlight(@RequestParam("flightNumber") String flightNumber, Model model){
        List<FlightModel> allFlights = flightService.getFlightList();
        ArrayList<FlightModel> flights = new ArrayList<FlightModel>() {
        };
        for (FlightModel flight : allFlights) {
            if(flight.getFlightNumber().equals(flightNumber))
                flights.add(flight);
        }
        model.addAttribute("flights",flights);

        return "view-flight";
    }


    @RequestMapping(value="/flight/add/{licenseNumber}", params={"addRow"})
    public String addRow(final FlightToAdd flights, Model model) {
        flights.getFlights().add(new FlightModel());
        model.addAttribute("flights", flights);

        return "addFlight";
    }

    @RequestMapping(value="/flight/add/{licenseNumber}", params={"removeRow"})
    public String removeRow(final FlightToAdd flights, Model model,
            final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        flights.getFlights().remove(rowId.intValue());

        model.addAttribute("flights", flights);

        return "addFlight";
    }
}
