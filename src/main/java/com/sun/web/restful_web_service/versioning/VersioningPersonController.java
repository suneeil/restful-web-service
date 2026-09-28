package com.sun.web.restful_web_service.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    //versioning of Path Params
    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson() {
        return new PersonV1("Bob Charlie doe");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson() {
        return new PersonV2(new Name("Bob", "Charlie doe"));
    }

    //Versioning of Query Params
    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getFirstVersionOfPersonReqParam() {
        return new PersonV1("Bob Charlie doe");
    }

    @GetMapping(path= "/person", params="version=2")
    public PersonV2 getSecondVersionOfPersonReqParam() {
        return new PersonV2(new Name("Bob", "Charlie doe"));
    }


    //Versioning using Request Header
    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonReqHeaders() {
        return new PersonV1("Bob Charlie doe");
    }

     @GetMapping(path= "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPersonReqHeader() {
        return new PersonV2(new Name("Bob", "Charlie doe"));
    }


    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getFirstVersionOfPersonAcceptHeader() {
        return new PersonV1("Bob Charlie doe");
    }
}

/**
 * 
 */