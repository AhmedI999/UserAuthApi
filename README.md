SQL script for creating the required table is in table.sql

Post mapping for registering http://localhost:8080/user/register
Required Json body for registering is
{
    "firstName": "firstName",
    "lastName": "lastName",
    "email": "example@example.com",
    "password": "password",
    "marketingConsent": false
}

Post mapping for authentication users already in database
http://localhost:8080/user/authenticate
Json
{
    "id": "id",
    "password": "password"
}

Get mapping for retrieving user http://localhost:8080/user/{id}
Json
{
    "token": "jwtToken"
}