## Lottery Ticket Catalog

### Funkcionalnosti te mikrostoritve:
* Ogled kataloga srečk
* Dodajanje srečke
* Ogled podatkov srečke
* Urejanje srečk
* Brisanje srečke


#### API dokumentacija je na voljo [tukaj](http://52.226.192.46/lottery-ticket-catalog/openapi)
#### API Swagger (UI) je na voljo [tukaj](http://52.226.192.46/lottery-ticket-catalog/api-specs/ui/?url=http://52.226.192.46/lottery-ticket-catalog/openapi&oauth2RedirectUrl=http://52.226.192.46/lottery-ticket-catalog/api-specs/ui/oauth2-redirect.html)

## Zagon in Testiranje

### Predpogoj je PostgreSQL baza

```bash
docker run -d --name pg-lottery-ticket -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=lottery-ticket -p 5432:5432 postgres:13
```

### Sestavljanje in zagon ukazov
```bash
mvn clean package
cd api/target
java -jar lottery-ticket-catalog-api-1.0.0-SNAPSHOT.jar
```
Aplikacija je dostopna na naslovu: localhost:8081


