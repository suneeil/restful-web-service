# Local MySQL

1. Copy `.env.example` to `.env` and fill in both passwords.
2. Run `docker compose up -d --wait` from the project directory.
3. Run `docker compose ps` to check the service.

MySQL is available at `127.0.0.1:3306`, with database `restful_web_service`
and application user `restful_app`. Set `MYSQL_PORT` in `.env` if port 3306
is already in use. The named volume keeps data across container replacement
and `docker compose down`. `docker compose down -v` deletes that data.
The image's database/user/password initialization only applies to an empty
data directory; changing `.env` does not update existing database credentials.

## Who creates the tables?

Compose starts the database server, and the MySQL image creates the empty
database and application account. Think of this as preparing a building;
the application's schema defines the rooms inside it.

The application owns `user_details`, `post`, and their relationship. For local
learning, Hibernate can create these from the existing `User` and `Post`
entities using `spring.jpa.hibernate.ddl-auto=update`. For production, manage
schema changes with Flyway or Liquibase migrations and use Hibernate validation.
There is no need to duplicate these table definitions in Compose initialization
scripts. The existing `data.sql` inserts sample records; it does not create tables.

## Connecting the application later

The application currently uses H2, and starting Compose does not switch it
to MySQL. To connect it, add the runtime Maven dependency
`com.mysql:mysql-connector-j`, then configure a MySQL Spring profile with:

```properties
spring.datasource.url=jdbc:mysql://localhost:${MYSQL_PORT:3306}/restful_web_service
spring.datasource.username=restful_app
spring.datasource.password=${MYSQL_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=false
spring.sql.init.mode=never
```

Export `MYSQL_PASSWORD` (and `MYSQL_PORT` if changed) for the Java process;
Compose's `.env` file is not automatically read by Spring Boot.
Override the existing `create-drop` setting when switching to persistent MySQL:
it would recreate the schema on startup and drop it on shutdown. The example
disables `data.sql` initialization so its fixed sample IDs are not reinserted
on each startup.

References: [MySQL image](https://hub.docker.com/_/mysql) and
[Spring Boot database initialization](https://docs.spring.io/spring-boot/how-to/data-initialization.html).
