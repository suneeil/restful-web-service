package com.sun.web.restful_web_service.filtering;


import com.fasterxml.jackson.annotation.JsonView;

public class DynamicBean {
    String field1;
    String field2;
    String field3;

    @JsonView(View.View1.class)
    public String getField1() {
        return field1;
    }
    @JsonView(View.View2.class)
    public String getField2() {
        return field2;
    }

    @JsonView({View.View1.class, View.View2.class})
    public String getField3() {
        return field3;
    }
    public DynamicBean(String field1, String field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }
}
