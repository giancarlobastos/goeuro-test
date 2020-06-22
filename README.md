# goeuro-test
Application that converts location response data from json to csv.

It retrieves location info from webservice https://www.omio.com/suggester-api/v5/position?term={location}&locale=en&hierarchical=true and 
returns it in csv format containing only _id, name, type, latitude and longitude_.

**How to build:**
```
mvn clean install
```

**How to run:**
```
java -jar GoEuroTest.jar "CITY_NAME"
```

**With Docker:**
```
mvn clean install
docker build -t goeuro-test .
docker run goeuro-test CITY_NAME
```
