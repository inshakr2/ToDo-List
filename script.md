# Scripts

Here, I take notes on the scripts I used during development testing.

## Postgres

### Run Postgres Container

```
docker run --name rest -p 5432:5432 -e POSTGRES_PASSWORD=chany1234 -d postgres

```

This cmdlet will create Postgres instance so that you can connect to a database with:
* database: postgres
* username: postgres
* password: chany1234
* post: 5432

### Getting into the Postgres container

```
docker exec -it rest bash
```

Then you will see the containers bash as a root user.

### Connect to a database

```
psql -d postgres -U postgres
```
or you can also connect like this
```
select -d postgres -W 
```
And enter the "POSTGRES_PASSWORD" you entered when running the container.


### Query Databases

```
\l
```

### Query Tables

```
\dt
```

### Quit

```
\q
```

## application.yaml


### Test Database

```yaml
datasource:
  driver-class-name: org.postgresql.Driver
  url: jdbc:postgresql://localhost:5432/postgres
  username: postgres
  password: chany1234
```