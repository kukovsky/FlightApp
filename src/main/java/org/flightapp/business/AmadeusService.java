package org.flightapp.business;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referencedata.Locations;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightOrder;
import com.amadeus.resources.FlightPrice;
import com.amadeus.resources.Location;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AmadeusService {

    private final Amadeus amadeus;

    public Location[] getLocation(String keyword) throws ResponseException {
        return amadeus.referenceData.locations.get(Params
                .with("keyword", keyword)
                .and("subType", Locations.ANY));
    }

    public FlightOfferSearch[] getFlights(String origin, String destination, String departureDate, String returnDate, String adults) throws ResponseException {
        return amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", origin)
                        .and("destinationLocationCode", destination)
                        .and("departureDate", departureDate)
                        .and("returnDate", returnDate)
                        .and("adults", adults)
                        .and("max", 5));
    }

    public FlightPrice confirmFlightPrice(FlightOfferSearch offer) throws ResponseException {
        return amadeus.shopping.flightOffersSearch.pricing.post(offer);
    }

    public FlightOrder orderFlight(JsonObject order) throws ResponseException {
        return amadeus.booking.flightOrders.post(order);
    }

}
