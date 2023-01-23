[![Minerva CI/CD](https://github.com/Matteo29-mar/Minervarepo/actions/workflows/books.yaml/badge.svg)](https://github.com/Matteo29-mar/Minervarepo/actions/workflows/books.yaml)

# Repository Gruppo progetto Minerva Books
    Exam project // ITS Cloud Specialist 2021-23

I microservizi possono essere sviluppati per qualsiasi tecnologia 
=======
## Finalità del progetto
Sviluppare un servizio di prestiti bibloteca, per il prestito di libri.

## Tecnologie utilizzate:
- Ambiente di sviluppo utilizzato: Intellij IDEA 
- JavaSE JDK v17.0.5
- Framework/runtime utilizzati: Spring Boot
- Database utilizzati: MariaDB versione  10.10.2 e MongoDB 
- Message broker ( Rabbit MQ, Prometheus, Grafana) per comunicazioni asincrone( chiama il servizio notifiche)


## Criteri:

 Microservices (0 to 5 points)
    Design patterns (0 to 5 points)
    Testing (0 to 5 points)
    Logging and tracing (0 to 5 points)
    CI/CD (0 to 5 points)
    Docker and Kubernetes (0 to 5 points)

## Utenti all'interno del progetto:
- [Matteo Marziano](https://github.com/Matteo29-mar)
- [Elisa Cat-Genova](https://github.com/Cat-Genova)
- [assia Samime](https://github.com/AssiaSam)
- [Paolo Carena](https://github.com/GeassVi)



## Architecture

![Architecture](https://github.com/Matteo29-mar/Minervarepo/blob/main/design.png?raw=true)

## Tech Stack
### Books Microservices

**Programming Language:** Java con Spring boot

**Database:** Mariadb

**Logging:** slf4j 

### Customers Microservices

**Programming Language:** Java con Spring boot

**Database:** Mariadb

**Logging:** Slf4j

### Borrowing Microservices

**Programming Language:** Java con Spring boot

**Database:** MongoDB

**Logging:** Slf4j
### Notifications Microservices

**Programming Language:** Java con Spring boot

**Logging:** Slf4j

**Notification:** Rabbit MQ
## Project Tree

To deploy this project run

```bash
Minervarepo-main
├───.idea
├───books
│   ├───docker
│   ├───src
│   │   ├───main
│   │   │   ├───java
│   │   │   │   └───com
│   │   │   │       └───minerva
│   │   │   │           └───books
│   │   │   │               ├───config
│   │   │   │               ├───controllers
│   │   │   │               ├───entities
│   │   │   │               ├───repo
│   │   │   │               └───services
│   │   │   └───resources
│   │   │       └───static
│   │   │           └───banner
│   │   └───test
│   │       └───java
│   │           └───com
│   │               └───minerva
│   │                   └───books
│   └───target
│       ├───classes
│       │   ├───com
│       │   │   └───minerva
│       │   │       └───books
│       │   │           ├───config
│       │   │           ├───controllers
│       │   │           ├───entities
│       │   │           ├───repo
│       │   │           └───services
│       │   └───static
│       │       └───banner
│       └───test-classes
│           └───com
│               └───minerva
│                   └───books
├───Borrowing
│   ├───src
│   │   ├───main
│   │   │   ├───java
│   │   │   │   └───com
│   │   │   │       └───minerva
│   │   │   │           └───borrowing
│   │   │   │               ├───config
│   │   │   │               ├───entities
│   │   │   │               ├───integration
│   │   │   │               ├───repos
│   │   │   │               └───service
│   │   │   └───resources
│   │   └───test
│   │       └───java
│   │           └───com
│   │               └───minerva
│   │                   └───borrowing
│   └───target
│       ├───classes
│       │   └───com
│       │       └───minerva
│       │           └───borrowing
│       │               ├───config
│       │               ├───entities
│       │               ├───integration
│       │               ├───repos
│       │               └───service
│       └───test-classes
│           └───com
│               └───minerva
│                   └───borrowing
├───Customers
│   └───src
│       ├───main
│       │   ├───java
│       │   │   └───com
│       │   │       └───minerva
│       │   │           └───customers
│       │   │               ├───config
│       │   │               ├───entities
│       │   │               ├───integration
│       │   │               ├───repo
│       │   │               └───services
│       │   └───resources
│       │       └───static
│       │           └───banner
│       └───test
│           └───java
│               └───com
│                   └───minerva
│                       └───customers
│                           └───integration
└───notification
    └───src
        ├───main
        │   ├───java
        │   │   └───com
        │   │       └───minerva
        │   │           └───notificationservice
        │   │               ├───config
        │   │               └───services
        │   └───resources
        └───test
            └───java
                └───com
                    └───minerva
                        └───notificationservice
```
