package com.auth.client;

import com.auth.dto.EmployeeMobileLookupResponse;

import com.common_lib.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EmployeeClient {

    private final WebClient webClient;

    public EmployeeClient(@Value("${employee-service.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public EmployeeMobileLookupResponse getEmployeeByMobile(String mobile) {

        ApiResponse<EmployeeMobileLookupResponse> response =
                webClient.get()
                        .uri("/employee/by-mobile/{mobile}", mobile)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<EmployeeMobileLookupResponse>>() {})
                        .block();

        if (response == null || response.data() == null) {
            throw new RuntimeException("Employee lookup failed for mobile: " + mobile);
        }

        return response.data();
    }
}
