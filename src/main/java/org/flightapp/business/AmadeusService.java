package org.flightapp.business;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referencedata.Locations;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.Location;
import lombok.AllArgsConstructor;
import org.flightapp.api.dto.ReservationsDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AmadeusService {

    private final Amadeus amadeus;

    public Location[] getLocation(String keyword) throws ResponseException {
        return amadeus.referenceData.locations.get(Params
                .with("keyword", keyword)
                .and("subType", Locations.ANY));
    }

    public List<ReservationsDTO> getFlights(
            String origin,
            String destination,
            String departureDate,
            String returnDate,
            String adults,
            Boolean nonStop) throws ResponseException {
        Params params = Params.with("originLocationCode", origin)
                .and("destinationLocationCode", destination)
                .and("departureDate", departureDate)
                .and("adults", adults)
                .and("max", 5)
                .and("nonStop", nonStop);
        if (returnDate != null && !returnDate.isEmpty()) {
            params = params.and("returnDate", returnDate);
        }
        FlightOfferSearch[] flightOffer = amadeus.shopping.flightOffersSearch.get(params);

        List<ReservationsDTO> results = new ArrayList<>();
        for (FlightOfferSearch offer : flightOffer) {
            ReservationsDTO result = new ReservationsDTO();

            int lastDepartureSegment = offer.getItineraries()[0].getSegments().length - 1;
            result.setDepartureOrigin(offer.getItineraries()[0].getSegments()[0].getDeparture().getIataCode());
            result.setDepartureDestination(offer.getItineraries()[0].getSegments()[lastDepartureSegment].getArrival().getIataCode());
            result.setDepartureDate(LocalDateTime.parse(offer.getItineraries()[0].getSegments()[0].getDeparture().getAt()));
            result.setDepartureReturnDate(LocalDateTime.parse(offer.getItineraries()[0].getSegments()[lastDepartureSegment].getArrival().getAt()));
            result.setDepartureAirline(offer.getItineraries()[0].getSegments()[0].getCarrierCode());
            result.setDepartureFlightNumber(offer.getItineraries()[0].getSegments()[0].getNumber());

            if (offer.getItineraries().length > 1) {
                int lastReturnSegment = offer.getItineraries()[1].getSegments().length - 1;
                result.setReturnOrigin(offer.getItineraries()[1].getSegments()[0].getDeparture().getIataCode());
                result.setReturnDestination(offer.getItineraries()[1].getSegments()[lastReturnSegment].getArrival().getIataCode());
                result.setReturnDepartureDate(LocalDateTime.parse(offer.getItineraries()[1].getSegments()[0].getDeparture().getAt()));
                result.setReturnReturnDate(LocalDateTime.parse(offer.getItineraries()[1].getSegments()[lastDepartureSegment].getArrival().getAt()));
                result.setReturnAirline(offer.getItineraries()[1].getSegments()[0].getCarrierCode());
                result.setReturnFlightNumber(offer.getItineraries()[1].getSegments()[0].getNumber());
            }
            result.setPrice(new BigDecimal(offer.getPrice().getTotal()));
            result.setCurrency(offer.getPrice().getCurrency());
            result.setNumberOfPassengers(Integer.parseInt(adults));
            int departureStops = Math.max(offer.getItineraries()[0].getSegments().length-1, 0);
            int returnStops = offer.getItineraries().length > 1 ? Math.max(offer.getItineraries()[1].getSegments().length-1, 0) : 0;
            result.setNumberOfStops(departureStops + returnStops);
            results.add(result);
        }
        return results;
    }




}
