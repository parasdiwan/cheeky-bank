## Cheeky Bank

To run the app, follow these steps:
- Clone the repo
- Navigate to project root folder
- Run command `./run-app`
- If that doesn't work, run using gradle task `run` | `./gradlew clean run`

###Two endpoints:
- Ping endpoint
```$xslt
curl --location --request GET 'localhost:8000/ping'
```
- Transfers endpoint
```
curl --location --request POST 'localhost:8000/transfers' \
--header 'Content-Type: application/json' \
--data-raw '{
	"userId": "jifdhkuba",
    "sourceAccountId": "fdsajkbf",
	"destAccountId": "iojdhusagyv",
    "amount": 248.55
}'
```