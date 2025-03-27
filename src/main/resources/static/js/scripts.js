document.addEventListener("DOMContentLoaded", function () {
    const today = new Date().toISOString().split('T')[0];
    document.getElementById("departureDate").setAttribute("min", today);
    document.getElementById("returnDate").setAttribute("min", today);

    document.getElementById("departureDate").addEventListener("change", function () {
        document.getElementById("returnDate").setAttribute("min", this.value);
    });

    document.getElementById("passengers").addEventListener("input", function () {
        if (this.value < 1) this.value = 1;
        if (this.value > 9) this.value = 9;
    });

    // Wyszukiwanie miejsc przy wpisywaniu w pole wyszukiwania
    document.getElementById("originSearch").addEventListener("input", function () {
        const keyword = this.value;
        if (keyword.length >= 2) { // Minimum 2 litery
            searchLocations(keyword, "origin");
        } else {
            clearSuggestions("origin");
        }
    });

    document.getElementById("destinationSearch").addEventListener("input", function () {
        const keyword = this.value;
        if (keyword.length >= 2) { // Minimum 2 litery
            searchLocations(keyword, "destination");
        } else {
            clearSuggestions("destination");
        }
    });

    // Zdarzenie formularza do wyszukiwania lotów
    document.getElementById("flightSearchForm").addEventListener("submit", function (event) {
        event.preventDefault();
        searchFlights();
    });

});

// Funkcja do wyszukiwania lokalizacji
async function searchLocations(keyword, type) {
    try {
        const response = await fetch(`/flightapp/api/locations?keyword=${keyword}`);
        if (!response.ok) {
            console.error('Błąd odpowiedzi API:', response.status);
            showAlert("Błąd podczas ładowania lokalizacji", "danger");
            return;
        }

        const locations = await response.json();
        console.log('Odpowiedź z API:', locations);
        populateSuggestions(locations, type);
    } catch (error) {
        console.error('Błąd podczas pobierania lokalizacji:', error);
        showAlert("Błąd podczas ładowania lokalizacji.", "danger");
    }
}

// Wypełnia listę sugestii na podstawie wyników wyszukiwania
function populateSuggestions(locations, type) {
    const inputElement = type === 'origin' ? document.getElementById("originSearch") : document.getElementById("destinationSearch");
    const suggestionsList = type === 'origin' ? document.getElementById("originSuggestions") : document.getElementById("destinationSuggestions");

    suggestionsList.innerHTML = ''; // Czyścimy poprzednie sugestie

    if (locations.length === 0) {
        suggestionsList.innerHTML = '<li class="suggestion-item">Brak wyników</li>';
        return;
    }

    locations.forEach(location => {
        const suggestionItem = document.createElement("li");
        suggestionItem.classList.add("suggestion-item");

        // HTML dla lepszego stylu
        suggestionItem.innerHTML = `
                <div class="suggestion-content">
                    <span class="airport-name">${location.name}</span>
                    <span class="iata-code">(${location.iataCode})</span>
                </div>
            `;

        // Dodajemy zdarzenie kliknięcia, by uzupełnić input
        suggestionItem.addEventListener('click', function () {
            inputElement.value = `${location.name} (${location.iataCode})`;
            inputElement.nextElementSibling.value = location.iataCode; // Uzupełniamy kod IATA
            suggestionsList.innerHTML = ''; // Usuwamy sugestie po wybraniu
        });

        suggestionsList.appendChild(suggestionItem);
    });
}


// Wyczyść listę sugestii
function clearSuggestions(type) {
    const suggestionsList = document.getElementById(`${type}Suggestions`);
    suggestionsList.innerHTML = '';
}

// Wyszukiwanie lotów
async function searchFlights() {
    const origin = document.getElementById("originCode").value;
    const destination = document.getElementById("destinationCode").value;
    const departureDate = document.getElementById("departureDate").value;
    const returnDate = document.getElementById("returnDate").value;
    const adults = document.getElementById("passengers").value;
    const directFlight = document.getElementById("directFlight").checked;


    const url = `/flightapp/api/flights?origin=${origin}&destination=${destination}&departDate=${departureDate}&adults=${adults}` +
        (returnDate ? `&returnDate=${returnDate}` : '') +
        (directFlight ? `&nonStop=true` : '');


    try {
        const response = await fetch(url);
        if (response.ok) {
            const flightOffers = await response.json();
            displayFlights(flightOffers);
        } else {
            showAlert("Błąd podczas wyszukiwania lotów.", "danger");
        }
    } catch (error) {
        console.error('Błąd podczas wyszukiwania lotów:', error);
    }
}


// Wyświetlanie wyników lotów
function displayFlights(flightOffers) {
    const resultsDiv = document.getElementById('flightsResults');
    resultsDiv.innerHTML = ''; // Czyść poprzednie wyniki

    if (flightOffers.length === 0) {
        resultsDiv.innerHTML = '<p>Brak dostępnych lotów.</p>';
        return;
    }

    const table = document.createElement('table');
    table.classList.add('table', 'table-bordered', 'text-center', 'align-middle');
    const thead = document.createElement('thead');
    thead.innerHTML = `
                <tr>
                    <th>Miejsce wylotu</th>
                    <th>Miejsce przylotu</th>
                    <th>Godzina wylotu</th>
                    <th>Godzina przylotu</th>
                    <th>Numer lotu</th>
                    <th>Linia lotnicza</th>
                    <th>Liczba przesiadek</th>
                    <th>Cena</th>
                    <th>Waluta</th>
                    <th>Akcja</th>
                </tr>
            `;
    table.appendChild(thead);

    const tbody = document.createElement('tbody');

    flightOffers.forEach(offer => {
        const numOfTransfers = offer.numberOfStops;
        const hasReturnFlight = !!offer.returnOrigin; // Sprawdź, czy jest lot powrotny

        const reserveButton = document.createElement('button');
        reserveButton.classList.add('btn', 'btn-primary');
        reserveButton.textContent = 'Rezerwuj';
        reserveButton.onclick = function () {
            reserveFlight(offer);
        };

        const rowOutbound = document.createElement('tr');
        const outboundDepartureDate = new Date(offer.departureDate);
        const outboundReturnDate = new Date(offer.departureReturnDate);

        rowOutbound.innerHTML = `
                <td>${offer.departureOrigin}</td>
                <td>${offer.departureDestination}</td>
                <td>${outboundDepartureDate.toLocaleString()}</td>
                <td>${outboundReturnDate.toLocaleString()}</td>
                <td>${offer.departureFlightNumber}</td>
                <td>${offer.departureAirline || 'N/A'}</td>
                <td ${hasReturnFlight ? 'rowspan="2"' : ''}>${numOfTransfers}</td>
                <td ${hasReturnFlight ? 'rowspan="2"' : ''}>${offer.price}</td>
                <td ${hasReturnFlight ? 'rowspan="2"' : ''}>${offer.currency}</td>
                `;

        const actionCell = document.createElement('td');
        actionCell.classList.add('align-middle');
        if (hasReturnFlight) {
            actionCell.setAttribute('rowspan', '2');
        }
        actionCell.appendChild(reserveButton);
        rowOutbound.appendChild(actionCell);

        tbody.appendChild(rowOutbound);

        if (hasReturnFlight) {
            const rowInbound = document.createElement('tr');
            const inboundDepartureDate = new Date(offer.returnDepartureDate);
            const inboundReturnDate = new Date(offer.returnReturnDate);

            rowInbound.innerHTML = `
                    <td>${offer.returnOrigin}</td>
                    <td>${offer.returnDestination}</td>
                    <td>${inboundDepartureDate.toLocaleString()}</td>
                    <td>${inboundReturnDate.toLocaleString()}</td>
                    <td>${offer.returnFlightNumber}</td>
                    <td>${offer.returnAirline || 'N/A'}</td>
                    `;

            tbody.appendChild(rowInbound);
        }
    });

    table.appendChild(tbody);
    resultsDiv.appendChild(table);
}


var userName = '${user.userName}';

// Funkcja rezerwacji lotu
function reserveFlight(offer) {
    console.log('Obiekt offer:', offer); // Sprawdzamy dane lotu
    if (!userName) {
        showAlert("Zaloguj się, by zarezerwować lot.", "danger");
        return;
    }

    // Pobranie liczby pasażerów
    const adults = document.getElementById("passengers").value;

    // Tworzenie obiektu rezerwacji
    const reservation = {
        userName: userName,
        departureOrigin: offer.departureOrigin, // Miejsce wylotu
        departureDestination: offer.departureDestination, // Miejsce przylotu
        departureDate: offer.departureDate, // Data wylotu
        departureReturnDate: offer.departureReturnDate, // Data przylotu
        departureAirline: offer.departureAirline, // Linia lotnicza wylotu
        departureFlightNumber: offer.departureFlightNumber, // Numer lotu wylotowego
        returnOrigin: offer.returnOrigin, // Miejsce wylotu powrotnego
        returnDestination: offer.returnDestination, // Miejsce przylotu powrotnego
        returnDepartureDate: offer.returnDepartureDate, // Data wylotu powrotnego
        returnReturnDate: offer.returnReturnDate, // Data przylotu powrotnego
        returnAirline: offer.returnAirline, // Linia lotnicza powrotna
        returnFlightNumber: offer.returnFlightNumber, // Numer lotu powrotnego
        numberOfStops: offer.numberOfStops, // Liczba przesiadek
        numberOfPassengers: adults, // Liczba pasażerów
        price: offer.price, // Cena lotu
        currency: offer.currency // Waluta
    };

    console.log('Obiekt reservation przed wysłaniem:', reservation); // Sprawdzamy dane rezerwacji przed wysłaniem

    // Jeśli istnieje lot powrotny, dodajemy go do rezerwacji
    if (offer.returnFlightNumber) {
        reservation.returnFlight = {
            returnOrigin: offer.returnOrigin, // Miejsce wylotu powrotnego
            returnDestination: offer.returnDestination, // Miejsce przylotu powrotnego
            returnDepartureDate: offer.returnDepartureDate, // Data wylotu powrotnego
            returnReturnDate: offer.returnReturnDate, // Data przylotu powrotnego
            returnAirline: offer.returnAirline, // Linia lotnicza powrotnego
            returnFlightNumber: offer.returnFlightNumber // Numer lotu powrotnego
        };

        // Dodajemy returnDate, jeśli istnieje lot powrotny
        reservation.returnDate = offer.returnDepartureDate;
    }

    console.log('Obiekt reservation po dodaniu lotu powrotnego:', reservation); // Sprawdzamy dane rezerwacji po dodaniu lotu powrotnego

    // Wysłanie rezerwacji do backendu
    const url = '/flightapp/reservations/reserve';
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify(reservation)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Odpowiedź z backendu:', data);
            showAlert(data.message, "success");
        })
        .catch(error => {
            console.error('Błąd podczas rezerwacji lotu:', error);
            showAlert(error.message, "danger");
        });
}

function showAlert(message, type) {
    let alertContainer;
    let alertMessageElement;

    if (type === 'success') {
        alertContainer = document.getElementById('alertContainerSuccess');
        alertMessageElement = document.getElementById('alertMessageSuccess');
    } else if (type === 'danger') {
        alertContainer = document.getElementById('alertContainerDanger');
        alertMessageElement = document.getElementById('alertMessageDanger');

    }
    console.log({alertContainer, alertMessageElement, message, type});
    if (alertContainer && alertMessageElement) {
        alertMessageElement.textContent = message;
        alertContainer.classList.remove('d-none');
    }
}

function closeAlert(type) {
    let alertContainer;

    if (type === 'success') {
        alertContainer = document.getElementById('alertContainerSuccess');
    } else if (type === 'danger') {
        alertContainer = document.getElementById('alertContainerDanger');

    }
    if (alertContainer) {
        alertContainer.classList.add('d-none');
    }
}