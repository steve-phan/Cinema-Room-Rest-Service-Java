# Cinema Room REST Service

This is a RESTful web service that provides various endpoints for managing a cinema room. The endpoints allow you to book and return seats, get information about the available and purchased seats, and retrieve statistics about the current state of the cinema room.

## Requirements

To run this service, you will need to have the following software installed on your system:

- Java 11 or higher
- Gradle 7.0 or higher

### Enpoints

1. `GET /seats` - All infomation the available seats with format `{ "row": 1, "column": 1, "price": 10 }`
2. `POST /purchase` - With request body `{row : int, column : int}` then return `{
    "token": "00ae15f2-1ab6-4a02-a01f-07810b42c0ee",
    "ticket": {
        "row": 1,
        "column": 1,
        "price": 10
    }
}`

3. `POST /return` With request body `{token: UUID}` then return `{
    "returned_ticket": {
        "row": 1,
        "column": 2,
        "price": 10
    }
}`

4. `POST /stats` - The URL parameters contain a password key with a super_secret value, return the movie theatre statistics in the following format:
   `{
    "current_income": 0,
    "number_of_available_seats": 81,
    "number_of_purchased_tickets": 0
}`

## Contributing

If you find a bug or would like to suggest a new feature, please open an issue or submit a pull request on GitHub.

Thank to [hyperskill](https://hyperskill.org/) the great learning platform
