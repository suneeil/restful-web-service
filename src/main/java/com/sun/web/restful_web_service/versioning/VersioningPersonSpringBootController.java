package com.sun.web.restful_web_service.versioning;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonSpringBootController {
//Note: to make use of Spring Boot versioning in path param we need to add --> spring.mvc.apiversion.use.path-segment=0 <---  in application.properties file

    //============== Versioning of Path Params using spring boot, ============== V
    // @GetMapping(value="/{version}/personsbi", version="1.0.0")
    // public PersonV1 getFirstVersionOfPerson() {
    //     return new PersonV1("Bob Charlie doe");
    // }

    // @GetMapping(value = "/{version}/personsbi", version="2.0.0")
    // public PersonV2 getSecondVersionOfPerson() {
    //     return new PersonV2(new Name("Bob", "Charlie doe"));
    // }

    //============== Versioning of Query Params ============== V
    //Note: to make use of Spring Boot versioning in 'query' param we need to add --> spring.mvc.apiversion.use.query-param=version <---  in application.properties file

    // @GetMapping(path = "/personsbi", version = "1.0.0")
    // public PersonV1 getFirstVersionOfPersonReqParam() {
    //     return new PersonV1("Bob Charlie doe");
    // }

    // @GetMapping(path= "/personsbi", version="2.0.0")
    // public PersonV2 getSecondVersionOfPersonReqParam() {
    //     return new PersonV2(new Name("Bob", "Charlie doe"));
    // }


    //============== Versioning using Request Header ==============
    //Note: to make use of Spring Boot versioning in 'query' param we need to add --> spring.mvc.apiversion.use.header=X-API-VERSION <---  in application.properties file
    //or 
    //Note if you want to pass in Accept in Header as 
    //Accept : application/json;version=1.0.0 then we need to add spring.mvc.apiversion.use.media-type-parameter[application/json]=version/
    //  @GetMapping(path = "/personsbi/header", version = "1.0.0")
    //  public PersonV1 getFirstVersionOfPersonReqHeaders() {
    //      return new PersonV1("Bob Charlie doe");
    //  }

    //   @GetMapping(path= "/personsbi/header", version = "2.0.0")
    //  public PersonV2 getSecondVersionOfPersonReqHeader() {
    //      return new PersonV2(new Name("Bob", "Charlie doe"));
    //  }


}

/**
 * 
 */