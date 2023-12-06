# SimplSrecka

## Mikrostoritev: Lottery Ticket Catalog

### Funkcionalnosti te mikrostoritve:
* Ogled kataloga srečk
* Dodajanje srečke
* Ogled podatkov srečke
* Urejanje srečk

### Use Case
* Ogled podatkov izbrane srečke
* Urejanje glavnega dobitka izbrane srečke


## Zagon in Testiranje

### Prerequisites

```bash
docker run -d --name pg-lottery-ticket -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=lottery-ticket -p 5432:5432 postgres:13
```

### Build and run commands
```bash
mvn clean package
cd api/target
java -jar image-catalog-api-1.0.0-SNAPSHOT.jar
```


