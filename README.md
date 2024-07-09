# Foreign Exchange Application
# Initializer
This application is a simple foreign exchange service, designed to provide current exchange rates, perform currency conversions, and keep a history of conversions. It is built using Spring Boot and integrates with an external service provider for exchange rates.

# Overview
The Foreign Exchange Application provides three main functionalities:

# Exchange Rate Endpoint: 
Fetches the current exchange rate between two currencies.
Currency Conversion Endpoint: Converts a specified amount from one currency to another.
Conversion History Endpoint: Retrieves a history of currency conversions based on transaction identifiers or dates.
##### You can test the application using the Swagger UI available at http://localhost:8080/swagger-ui/index.html.

# Purpose
The purpose of this application is to demonstrate a simple yet fully functional foreign exchange service. It integrates with an external exchange rate provider and supports common requirements such as error handling, pagination, and detailed API documentation.

# Features
Fetch Current Exchange Rates: Get the latest exchange rates between any two supported currencies.
Convert Currency Amounts: Convert a specified amount from one currency to another.
View Conversion History: Retrieve a paginated list of past conversions filtered by transaction ID or date.
Error Handling: Provides meaningful error messages and specific error codes.
API Documentation: Interactive API documentation using Swagger.
Docker Support: Containerized application for consistency across different environments.

# Usage
Running the Application
### Clone the repository:
git clone [https://github.com/your-username/foreign-exchange-app.git](https://github.com/nikolafb/zetta.git)
cd foreign-exchange-app

### Build the project:
./mvnw clean install

### Run the application:
./mvnw spring-boot:run

### Access the Swagger UI:
Open your web browser and go to http://localhost:8080/swagger-ui/index.html to interact with the API endpoints.


# Acknowledgements
Exchange rate data provided by https://www.exchangerate-api.com/
