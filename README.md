# Semantic Enrichment

## Project setup

```
semanticEnrichment
├─┬ backend     → backend module with Java code for database interactions
│ ├── src
│ └── pom.xml
├─┬ frontend    → frontend module with Vue.js code
│ ├── src
│ └── pom.xml
└── pom.xml     → Maven parent pom managing both modules
```

## Application configuration

Configure virtuoso at the files:

```
src/main/resources/application.properties
src/test/resources/application.properties
```

Configuration parameters:

```
virtuoso.endpoint=localhost
virtuoso.port=1111
virtuoso.user=dba
virtuoso.password=dba
virtuoso.frequencyCodelistGraph=http://localhost:8890/codelists
virtuoso.languageCodelistGraph=http://localhost:8890/codelists
virtuoso.countryCodelistGraph=http://localhost:8890/codelists
virtuoso.dataGraph=http://testgraph.org
```

| Parameter              | Description |
| ---- | ----|
| virtuoso.endpoint      | The virtuoso endpoint URL |
| virtuoso.port          | The port virtuoso listens | 
| virtuoso.user          | Username to connect to virtuoso |
| virtuoso.password      | Password to connect to virtuoso |
| virtuoso.frequencyCodelistGraph | The GRAPH where the frequency code list is stored (all codelists can be stored at the same GRAPH)|
| virtuoso.languageCodelistGraph | The GRAPH where the language code list is stored  (all codelists can be stored at the same GRAPH)|
| virtuoso.countryCodelistGraph | The GRAPH where the country code list is stored  (all codelists can be stored at the same GRAPH)|
| virtuoso.dataGraph     | The GRAPH to store the dataset metadata |

## Application run

Inside the root directory, do a: 

```
mvn validate
```

Clean & install the application: 

```
mvn clean install
```

Run the application:

```
mvn --projects backend spring-boot:run
```

The application by default is available at http://localhost:8098/.

## Build and run with Docker

Run Docker build:

```
docker build . --tag semantic-alignment:latest
```

Start the Docker container:

```
docker run -d -p 8098:8098 --name mysemantic semantic-alignment
```