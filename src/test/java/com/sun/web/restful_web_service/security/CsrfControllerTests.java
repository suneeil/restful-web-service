// package com.sun.web.restful_web_service.security;

// import com.jayway.jsonpath.JsonPath;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
// import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
// import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
// import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
// import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
// import org.springframework.context.annotation.Import;
// import org.springframework.http.HttpHeaders;
// import org.springframework.mock.web.MockHttpSession;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RestController;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest(controllers = CsrfController.class, properties = {
//         "spring.security.user.name=csrf-test",
//         "spring.security.user.password=test-only-password"
// })
// @Import({SpringSecurityConfiguration.class, CsrfControllerTests.ProbeController.class})
// @ImportAutoConfiguration({SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class,
//         ServletWebSecurityAutoConfiguration.class})
// class CsrfControllerTests {

//     private static final String AUTHORIZATION = "Basic "
//             + HttpHeaders.encodeBasicAuth("csrf-test", "test-only-password", null);

//     @Autowired
//     private MockMvc mockMvc;

//     @Test
//     void requiresAuthenticationToFetchToken() throws Exception {
//         mockMvc.perform(get("/csrf"))
//                 .andExpect(status().isUnauthorized());
//     }

//     @Test
//     void rejectsAuthenticatedPostWithoutToken() throws Exception {
//         mockMvc.perform(post("/csrf-test-probe").header(HttpHeaders.AUTHORIZATION, AUTHORIZATION))
//                 .andExpect(status().isForbidden());
//     }

//     @Test
//     void acceptsTokenReturnedByEndpointWithSameSession() throws Exception {
//         var result = mockMvc.perform(get("/csrf").header(HttpHeaders.AUTHORIZATION, AUTHORIZATION))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.token").isNotEmpty())
//                 .andExpect(jsonPath("$.headerName").value("X-CSRF-TOKEN"))
//                 .andReturn();
//         String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
//         var session = (MockHttpSession) result.getRequest().getSession(false);

//         mockMvc.perform(post("/csrf-test-probe")
//                         .session(session)
//                         .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
//                         .header("X-CSRF-TOKEN", token))
//                 .andExpect(status().isOk())
//                 .andExpect(content().string("accepted"));

//         mockMvc.perform(post("/csrf-test-probe")
//                         .session(session)
//                         .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
//                         .header("X-CSRF-TOKEN", "invalid-token"))
//                 .andExpect(status().isForbidden());
//     }

//     @RestController
//     static class ProbeController {

//         @PostMapping("/csrf-test-probe")
//         String acceptPost() {
//             return "accepted";
//         }
//     }
// }
