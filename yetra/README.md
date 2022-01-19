# Readme

## Test Nutzer
Nutzername: admin \
Passwort: 123

*Das Password wurde nur so kurz gehalten um ein einfaches Einloggen beim Korrigieren zur ermöglichen.*
  
## Partner Projekte
- Sina Amann: `eBank`
  - Stellt Überweisungsdienst zur Verfügung.
  - Nutzt meinen Orderservice.
- Andreas Huber: `ynvest`
  - Nutzt meinen Orderservice.

## Build
### Fat Jar
```shell
mvn -Pall package
```
### Bibliothek für Partnerprojekte
```shell
mvn -Pexternal package
```

### Aktualisieren der Dependencies
```shell
mvn install:install-file \
  -Dfile=./eBank-0.0.1-SNAPSHOT-external.jar \
  -DgroupId=eBank \
  -DartifactId=eBank \
  -Dversion=0.0.1-SNAPSHOT \
  -Dpackaging=jar \
  -DgeneratePom=true \
  -DlocalRepositoryPath=./lib
```