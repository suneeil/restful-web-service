package com.sun.web.restful_web_service.filtering;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController 
public class FilteringController {
    
    @GetMapping("/filtering")
    public SomeBean filtering() {
        return new SomeBean("value1", "value2", "value3");
    }

    @GetMapping("/filtering-list")
    public List<SomeBean> filteringList() {
        return List.of(
            new SomeBean("value1", "value2", "value3"),
            new SomeBean("value4", "value5", "value6")
        );
    }

    @GetMapping("/filtering-with-view")//return field1 and field3
    @JsonView(View.View1.class)
    public DynamicBean dynamicFilteringWithView() {
           return new DynamicBean("value1", "value2", "value3");
       }

    @GetMapping("/filtering-list-with-view")//return field2 and field3
    @JsonView(View.View2.class)
    public List<DynamicBean> dynamicFilteringListView() {
           return List.of(
            new DynamicBean("value1", "value2", "value3"),
            new DynamicBean("value4", "value5", "value6"));
      }

 
}
