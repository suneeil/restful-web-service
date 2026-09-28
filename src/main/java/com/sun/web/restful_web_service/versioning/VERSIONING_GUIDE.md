# Choosing an API versioning approach

This guide uses the mappings in `VersioningPersonController.java`. For a new API with no existing team convention, my recommendation is **URL path versioning**: the version is easy for clients to discover, test, and troubleshoot. Choose another approach when its benefits fit your clients or an established API contract.

## Why version this API?

Your V1 response represents the name as a string:

```json
{"name": "Bob Charlie doe"}
```

V2 represents it as an object:

```json
{"name": {"firstName": "Bob", "lasttName": "Charlie doe"}}
```

The spelling `lasttName` reflects the current field in `Name.java`.

A client expecting a string can break when it receives an object. Separate versions let existing clients keep using V1 while others migrate to V2. A version is usually justified by a breaking contract change, such as removing a field, changing its type, or changing required input. An internal refactor alone does not require a new API version.

## Comparison

These are design recommendations, not Spring requirements.

| Approach | Example in this controller | When to use it | Main tradeoff |
|---|---|---|---|
| URL path | `/v1/person` | Public APIs, clients with varied tooling, or teams that want obvious versions in URLs and logs | Each version has a different URL; clients must update it when migrating |
| Query parameter | `/person?version=1` | A stable path is useful, but you still want a version that is easy to enter in a browser or share as a link | Every caller must preserve the parameter; define what happens when it is absent |
| Custom header | `/person/header` with `X-API-VERSION: 1` | Controlled clients, internal services, or SDKs that consistently set headers | The URL alone does not show the version; debugging and infrastructure must account for the header |
| Accept header / media type | `/person/accept` with `Accept: application/vnd.company.app-v1+json` | APIs already using content negotiation, where clients explicitly choose a representation | More complex client configuration; wildcard Accept values do not demand a particular version |

The `/header` and `/accept` suffixes separate the demonstrations in this project. Header-based strategies can both use a resource path such as `/person` in a real API. Prefer one consistent public strategy unless you have a migration requirement.

## 1. URL path versioning

```java
@GetMapping("/v1/person")
@GetMapping("/v2/person")
```

Each annotation belongs to a separate method in your controller. The URL chooses the version.

```bash
curl -i 'http://localhost:8080/v1/person'
curl -i 'http://localhost:8080/v2/person'
```

Use this when simplicity for consumers matters most. It is easy to show both versions in documentation and identify the version from an access log. Plan how long the older URL will remain supported.

Your comment calls this “Path Params,” but these are fixed URL paths. A path variable would use syntax such as `/{version}/person`.

## 2. Query parameter versioning

```java
@GetMapping(path = "/person", params = "version=1")
@GetMapping(path = "/person", params = "version=2")
```

```bash
curl -i 'http://localhost:8080/person?version=1'
curl -i 'http://localhost:8080/person?version=2'
```

Use this when you want to keep `/person` as the path while allowing clients to select a version through the URL. It also fits an existing API that already uses this convention.

The `params` condition selects the handler. Adding `@RequestParam` to its arguments only reads a value after selection; it is unnecessary when the method does not use that value. In particular, `@RequestParam(defaultValue = "2")` cannot make a request without a version match `params = "version=1"`.

## 3. Custom request header versioning

```java
@GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
@GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
```

```bash
curl -i 'http://localhost:8080/person/header' -H 'X-API-VERSION: 1'
curl -i 'http://localhost:8080/person/header' -H 'X-API-VERSION: 2'
```

Use this when you control the consumers and can configure an SDK or shared HTTP client to send the version on every request. It keeps the resource URL stable.

Sending only `Accept: application/vnd.company.app-v1+json` does not satisfy either custom-header condition. Spring does not translate a media type into `X-API-VERSION` automatically.

## 4. Accept header (media type) versioning

Your controller currently has only V1 for this approach:

```java
@GetMapping(path = "/person/accept",
            produces = "application/vnd.company.app-v1+json")
```

```bash
curl -i 'http://localhost:8080/person/accept' \
  -H 'Accept: application/vnd.company.app-v1+json'
```

Use this when representation selection is already part of your API design and consumers understand content negotiation. `Accept` describes the response types the client can receive. `Content-Type` on a request describes the body being sent; it does not replace `Accept` for this mapping.

To support V2 here, you would add a separate method returning `PersonV2` with `produces = "application/vnd.company.app-v2+json"`. That mapping is not present in the current controller.

### Why your two earlier requests both returned V1

| Request to `/person/accept` | What happens with the current mapping |
|---|---|
| `X-API-VERSION: 2`, with curl's default `Accept: */*` | V1 is acceptable; this method has no custom-header condition |
| `Accept: application/vnd.company.app-v1+json` | V1 explicitly matches |
| `Accept: application/vnd.company.app-v2+json` | No supported representation matches |

`produces` checks compatibility with acceptable media types; it does not require the client to send that exact header text. If you later add multiple representations, define and test the behavior for missing or wildcard Accept values instead of assuming they mean “latest.”

Spring documents the path, parameter, header, and media-type conditions in [Mapping Requests](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html). See also the [`RequestParam` default-value contract](https://docs.spring.io/spring-framework/docs/6.0.3/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html).

## Operational decisions for any strategy

- Document the supported versions, missing-version behavior, unsupported-version behavior, and retirement policy. Avoid silently moving existing clients to a breaking new version.
- Keep shared business rules in services. Version-specific controllers or DTOs can adapt the input and output contract without copying business logic.
- If responses are cached, make the cache distinguish versions. Preserve the query parameter in cache keys for query versioning; configure header-based variants using the appropriate cache key and `Vary` response header, such as `Vary: X-API-VERSION` or `Vary: Accept`. Verify gateway/CDN behavior. The HTTP rules are described in [RFC 9111, cache keys and Vary](https://www.rfc-editor.org/rfc/rfc9111.html#section-4.1).
- Test each supported response shape and requests with missing, unsupported, or conflicting version information.

In this repository, `GlobalExceptionHandler.handleUnexpectedException` catches `Exception` and returns 500. That can hide the intended status of framework request errors. An unsupported Accept media type normally leads to 406, but the current catch-all can change the observed response. Inspect the actual exception before diagnosing a versioning failure from the status alone.

## Practice

Using the existing mappings, predict which method handles each request, then verify with curl:

1. `/person?version=2`
2. `/person/header` with only `Accept: application/vnd.company.app-v1+json`
3. `/person/accept` with `X-API-VERSION: 2` and `Accept: */*`

Expected results: (1) query V2 method; (2) neither custom-header method matches; (3) Accept V1 method.
