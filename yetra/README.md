# Readme

## Test Nutzer
Nutzername: admin \
Passwort: 123

*Das Password wurde nur so kurz gehalten um ein einfaches Einloggen beim Korrigieren zur ermöglichen.*

## Anmerkungen
- Gemäß der Anforderungen wude @Scope("singleton") an jeder Bean gesetzt,
  obwohl dies der Standard ist.
- Statt RestTemplate wurde WebClient benutzt.
  RestTemplate ist deprecated.
  WebClient ist der offizielle Nachfolger.
  WebClient erlaubt außerdem einfachere Authentifizierung über Basic Auth.
- Um ein flüssigen Handel zu ermöglichen, gibt es einen Bot der
  regelmäßig alle Angebote und Nachfragen befriedigt.
- Die Anwendung speichert absichtlich nicht, wer welche Aktien besitzt.
  Die Verantwortung dafür liege bei einem eigenen Projekt,
  einem elektronischen Wertpapierregister.
  Anforderungen dafür werden aktuell von der Bundesregierung erarbeitet.
  
## Partner Projekte
- Sina Amann: `eBank`
  - Stellt Überweisungsdienst zur Verfügung.
  - Nutzt meinen Orderservice.
- Andreas Huber: `ynvest`
  - Nutzt meinen Orderservice.

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
