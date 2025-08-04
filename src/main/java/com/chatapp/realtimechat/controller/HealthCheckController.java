package com.chatapp.realtimechat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A simple controller to check the health of the application.
 * This is a common practice to have a basic endpoint to verify that the
 * application has started successfully and is responsive.
 */
@RestController // This annotation marks the class as a REST controller.
// It's a combination of @Controller and @ResponseBody.
// @Controller allows the class to be detected by component-scanning.
// @ResponseBody indicates that the return value of the methods
// should be written directly to the HTTP response body, not rendered as a view.
@RequestMapping("/api") // This maps all requests for this controller to start with the "/api" path.
// It's a good practice to namespace your API endpoints under a common path like "/api".
public class HealthCheckController {

    /**
     * Handles GET requests to the /api/health endpoint.
     * When a user or a service sends a GET request to http://localhost:8080/api/health,
     * this method will be executed.
     *
     * @return A simple string "OK" to indicate that the service is up and running.
     */
    @GetMapping("/health") // This annotation maps HTTP GET requests for "/health" to this method.
    // The full path will be a combination of the class-level @RequestMapping
    // and the method-level @GetMapping: /api/health.
    public String healthCheck() {
        // Because of the @RestController annotation on the class, Spring will take this
        // returned String and write it directly as the response. The HTTP status code
        // will be 200 OK by default.
        return "OK";
    }
}