
package com.javaexpress.docker.dockerspringboothello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/docker")
public class DockerHelloWorld {

    @GetMapping
    public String getName() {
        return "Welcome to Project Demo about Spring Boot";
    }

    // Health check endpoint
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now().toString());
        return status;
    }

    // Info endpoint: returns server time and hostname
    @GetMapping("/info")
    public Map<String, Object> info() throws UnknownHostException {
        Map<String, Object> info = new HashMap<>();
        info.put("app", "Docker Spring Boot Example");
        info.put("time", LocalDateTime.now().toString());
        info.put("host", InetAddress.getLocalHost().getHostName());
        return info;
    }

    // Echo endpoint: returns whatever message is sent in the query string
    @GetMapping("/echo")
    public Map<String, String> echo(@RequestParam(defaultValue = "hello") String msg) {
        Map<String, String> response = new HashMap<>();
        response.put("message", msg);
        return response;
    }

    // About endpoint: static info
    @GetMapping("/about")
    public List<String> about() {
        return Arrays.asList(
            "Welcome to DevOps Level 1",
            "Final Project Demo",
            "Welcome to Project Demo about Spring Boot"
        );
    }
}

