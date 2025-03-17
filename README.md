# integration-demo
EIS-Hackathon - integration demo

## API Specification:
http://localhost:8080/swagger-ui/index.html

## Database Console:
http://localhost:8080/h2-console

### Database Queries:
SELECT * FROM USER_EVENT ;
SELECT * FROM HIGH_RATING_LOG ;

## To test this application
- Run the IntegrationDemoApplication class main method
- Navigate to the API Specification page
    - /message will simply insert the message being provided with no concern about what is already in the USER_EVENT table
    - /event will do three things:
        1. Insert or Update the row in the USER_EVENT table
        2. Insert into the High Rating table when rating >= 5.0
        3. Create an xml file when active = true