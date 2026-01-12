N.B: Al seguente link https://rosa-co.github.io/INGSW_Backend/ è possibile visionare la documentazione Javadoc del progetto.
Questo è il backend per l'applicazione **BugBoard**, sviluppato come progetto per il corso di Ingegneria del Software.
Il sistema gestisce la logica lato server, le API REST e la persistenza dei dati per la piattaforma di gestione bug/ticket.

Tecnologie utilizzate:
* Linguaggio: Java
* Framework: Spring Boot
* Build Tool: Maven
* Database: Supabase (usato solo ai fini di avere il database online, non sono stati usati altri servizi esterni del software)
* Documentazione: 


Struttura del progetto:
* **`/api` & `/controller`**: Gestiscono le richieste REST ed espongono gli endpoint.
* **`/service`**: Contiene la logica di business dell'applicazione.
* **`/repository`**: Interfaccia con il database (pattern DAO/Repository).
* **`/model`**: Entità del database.
* **`/dto`**: Oggetti per il trasferimento dei dati (Data Transfer Objects).
* **`/mapper`**: Logica di conversione tra Entity e DTO.
* **`/security`**: Configurazione della sicurezza (autenticazione/autorizzazione).
* **`/config`**: Classi di configurazione generale di Spring.

Requisiti: 
* Java JDK
* Maven
