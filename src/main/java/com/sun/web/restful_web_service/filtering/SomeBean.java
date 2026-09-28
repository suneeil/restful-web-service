package com.sun.web.restful_web_service.filtering;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class SomeBean {
String field1;
String field2;
String field3;
public String getField1() {
    return field1;
}

@JsonIgnore 
public String getField2() {
    return field2;
}
public String getField3() {
    return field3;
}
public SomeBean(String field1, String field2, String field3) {
    this.field1 = field1;
    this.field2 = field2;
    this.field3 = field3;
}

}
