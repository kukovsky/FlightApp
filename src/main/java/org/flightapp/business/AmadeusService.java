package org.flightapp.business;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referencedata.Locations;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightPrice;
import com.amadeus.resources.Location;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AmadeusService {

    private final Amadeus amadeus;

    // Pobieranie dostępnych lokalizacji (miast, lotnisk)
    public Location[] getLocation(String keyword) throws ResponseException {
        return amadeus.referenceData.locations.get(Params
                .with("keyword", keyword)
                .and("subType", Locations.ANY));
    }

    // Pobieranie dostępnych lotów
    public FlightOfferSearch[] getFlights(String origin, String destination, String departureDate, String returnDate, String adults) throws ResponseException {
        Params params = Params.with("originLocationCode", origin)
                .and("destinationLocationCode", destination)
                .and("departureDate", departureDate)
                .and("adults", adults)
                .and("max", 5);
        if (returnDate != null && !returnDate.isEmpty()) {
            params = params.and("returnDate", returnDate);
        }
        return amadeus.shopping.flightOffersSearch.get(params);

    }

    public FlightPrice confirmFlightPrice(FlightOfferSearch offer) throws ResponseException {
        return amadeus.shopping.flightOffersSearch.pricing.post(offer);
    }



}
