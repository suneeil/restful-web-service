# Repository Guidelines

## Project Structure & Module Organization

This is a Java 21 Spring Boot REST service built with Maven. Production code lives under `src/main/java/com/sun/web/restful_web_service/`; keep controllers, services, repositories, entities, and configuration in focused subpackages beneath this base package so component scanning continues to work. Runtime configuration belongs in `src/main/resources/application.properties`. Tests mirror the production package hierarchy under `src/test/java`. Maven writes compiled classes, reports, and packaged artifacts to `target/`; do not commit that directory.

## Build, Test, and Development Commands

Use the checked-in Maven wrapper so contributors run the repository's configured Maven version:

- `./mvnw spring-boot:run` starts the application locally with DevTools support.
- `./mvnw test` compiles the project and runs the test suite.
- `./mvnw clean verify` performs a clean build and all verification phases.
- `./mvnw clean package` creates the executable JAR in `target/`.

On Windows, use `mvnw.cmd` in place of `./mvnw`.

## Coding Style & Naming Conventions

Follow standard Java and Spring conventions: four-space indentation, braces on the same line, and one public top-level type per file. Use `UpperCamelCase` for classes, `lowerCamelCase` for methods and fields, and lowercase package names. Prefer descriptive Spring suffixes such as `UserController`, `UserService`, and `UserRepository`. Keep HTTP controllers thin and move business rules into services. Lombok is available, but use it deliberately and avoid obscuring important behavior. No formatter or linter is currently configured; match surrounding code and organize imports before committing.

## Testing Guidelines

Tests use JUnit 5 and Spring Boot's test support. Name test classes `*Tests` and test methods after observable behavior, for example `returnsNotFoundForUnknownUser`. Use focused MVC or JPA slice tests where possible; reserve `@SpringBootTest` for integration behavior requiring the full context. Run `./mvnw test` before every pull request. No coverage threshold is configured, so add tests for each new endpoint, validation rule, and bug fix.

## Commit & Pull Request Guidelines

Git history is not included in this checkout. Use concise, imperative commit subjects such as `Add user lookup endpoint`, keeping unrelated changes separate. Pull requests should explain the purpose and approach, list verification commands, and link relevant issues. Document API or configuration changes with example requests/responses; include screenshots only for changes with a visual effect. Never commit credentials. Use environment variables or profile-specific, untracked configuration for secrets.
