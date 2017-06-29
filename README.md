# whittest
Foursquare integration

## How to run

1. Clone the project onto your machine.
2. Navigate to the root folder of this project.
3. Open a terminal window and execute the command "mvn spring-boot:run"
4. Try out the API using a tool to make XHRs, such as Postman.


The test is configured to run on http://localhost:57575


Note: I tried to implement Swagger to automatically document the API, but it didn't work. It just outputs a blank Swagger page on http://localhost:57575/swagger-ui.html

------

# List of endpoints

## GET _/hotels_
Retrieves the full list of prepopulated hotels. Should have 10 hotels initially.
Example response:
```json
[
    {
        "id": "LONBLA",
        "name": "London Blackfriars (Fleet Street)",
        "loc": {
            "id": "1",
            "name": "Blackfriars",
            "lat": 51.513104,
            "lon": -0.105613
        }
    },
    {
        "id": "LONSOU",
        "name": "London Southwark (Tate Modern)",
        "loc": {
            "id": "2",
            "name": "Southwark (Tate)",
            "lat": 51.505292,
            "lon": -0.100202
        }
    },
    {
        "id": "SOUANC",
        "name": "London Southwark (Bankside)",
        "loc": {
            "id": "3",
            "name": "Southwark (Bank)",
            "lat": 51.506724,
            "lon": -0.092649
        }
    },
    {
        "id": "LONMON",
        "name": "London Bank (Tower)",
        "loc": {
            "id": "4",
            "name": "Bank",
            "lat": 51.509607,
            "lon": -0.083538
        }
    },
    {
        "id": "LONSTM",
        "name": "hub London Covent Garden",
        "loc": {
            "id": "5",
            "name": "Covent Garden",
            "lat": 51.5099988,
            "lon": -0.1272863
        }
    },
    {
        "id": "LONWAT",
        "name": "London Waterloo (Westminster Bridge)",
        "loc": {
            "id": "6",
            "name": "Waterloo",
            "lat": 51.50318637663801,
            "lon": -0.11531352996826907
        }
    },
    {
        "id": "LONCOU",
        "name": "London County Hall",
        "loc": {
            "id": "7",
            "name": "County Hall",
            "lat": 51.501472,
            "lon": -0.11876
        }
    },
    {
        "id": "LONALD",
        "name": "London City (Aldgate)",
        "loc": {
            "id": "8",
            "name": "Aldgate",
            "lat": 51.5142363,
            "lon": -0.0697958
        }
    },
    {
        "id": "LONLEI",
        "name": "London Leicester Square",
        "loc": {
            "id": "9",
            "name": "Leicester Square",
            "lat": 51.511143,
            "lon": -0.13035
        }
    },
    {
        "id": "KINPTI",
        "name": "London Kings Cross",
        "loc": {
            "id": "10",
            "name": "Kings Cross",
            "lat": 51.532001,
            "lon": -0.122086
        }
    }
]
```

## POST _/hotels_
Adds a new hotel to the list.
Requires headers - Content-type: application/json
Payload example:
```json
{
    "id": "NEWHOTEL",
    "name": "New London Hotel",
    "loc": {
        "id": "1",
        "name": "Blackfriars",
        "lat": 51,
        "lon": 0
    }
}
```

## GET _/hotels/{id}_
Retrieves a specific hotel from the list.
Example response:
```json
{
    "id": "LONBLA",
    "name": "London Blackfriars (Fleet Street)",
    "loc": {
        "id": "1",
        "name": "Blackfriars",
        "lat": 51.513104,
        "lon": -0.105613
    }
}
```

## PUT _/hotels/{id}_
Updates a specific hotel on the list.
Requires headers - Content-type: application/json
Payload example:
```json
{
    "id": "NEWHOTEL",
    "name": "New London Hotel",
    "loc": {
        "id": "1",
        "name": "Blackfriars",
        "lat": 51,
        "lon": 0
    }
}
```

## DELETE _/hotels/{id}_
Deletes a hotel from the list.
Does not require a payload or any specific headers.

## GET _/hotels/{id}/{query}/{radius}_
Integrates with Foursquare and retrieves the top venues for a specific hotel within a search radius.
**id** should be one of the existing hotels on the list.
**query** can be anything you're looking for, e.g. (bars, cafes, nightclubs, casinos, etc.)
**radius** should be a number between 0 and 100000. A good example would be between 500 and 1500.
Example call and response:
http://localhost:57575/hotels/LONBLA/bars/1000
```json
[
    {
        "id": "4b9a90e1f964a520dbc135e3",
        "name": "Holborn Bars",
        "location": {
            "formattedAddress": [
                "138-140 Holborn",
                "London",
                "Greater London",
                "United Kingdom"
            ],
            "distance": 629,
            "postalCode": null
        },
        "categories": [
            {
                "name": "Meeting Room"
            }
        ],
        "url": null
    },
    {
        "id": "53e3e8fa498e45efd42c588a",
        "name": "Bar Smith",
        "location": {
            "formattedAddress": [
                "18-20 St John St",
                "London",
                "Greater London",
                "EC1M 4AY",
                "United Kingdom"
            ],
            "distance": 843,
            "postalCode": "EC1M 4AY"
        },
        "categories": [
            {
                "name": "Cocktail Bar"
            }
        ],
        "url": "http://www.barsmith.co.uk"
    },
    {
        "id": "4ac518bbf964a52016a220e3",
        "name": "Corney & Barrow",
        "location": {
            "formattedAddress": [
                "3 Fleet Place",
                "London",
                "Greater London",
                "E1W 1YZ",
                "United Kingdom"
            ],
            "distance": 300,
            "postalCode": "E1W 1YZ"
        },
        "categories": [
            {
                "name": "Wine Bar"
            }
        ],
        "url": "http://www.corney-barrow.co.uk/"
    },
    {
        "id": "4cb36a025430b7136b433916",
        "name": "Bar Standards Board",
        "location": {
            "formattedAddress": [
                "289-293 High Holborn",
                "London",
                "Greater London",
                "WC1V 7HZ",
                "United Kingdom"
            ],
            "distance": 854,
            "postalCode": "WC1V 7HZ"
        },
        "categories": [
            {
                "name": "Office"
            }
        ],
        "url": null
    },
    {
        "id": "5658ae34498e1d3da1758a1b",
        "name": "Reserve Bar Stock Exchange",
        "location": {
            "formattedAddress": [
                "United Kingdom"
            ],
            "distance": 964,
            "postalCode": null
        },
        "categories": [],
        "url": null
    }
]
```