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

## Application run

Inside the root directory, do a: 

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