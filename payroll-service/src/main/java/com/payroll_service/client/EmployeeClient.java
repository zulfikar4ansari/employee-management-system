package com.payroll_service.client;

import com.payroll_service.dto.EmployeeDTO;
import com.payroll_service.dto.EmployeeListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${employee.service.url:http://localhost:8082}")
    private String employeeServiceUrl;

    /**
     * Fetch all employees from Employee Service
     */
    public List<EmployeeDTO> getAllEmployees() {
        try {
            WebClient webClient = webClientBuilder
                    .baseUrl(employeeServiceUrl)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getServiceToken())
                    .build();

            EmployeeListResponse response = webClient.get()
                    .uri("/admin/employees")
                    .retrieve()
                    .bodyToMono(EmployeeListResponse.class)
                    .block();

            if (response == null || response.getData() == null) {
                log.warn("Employee service returned empty response");
                return Collections.emptyList();
            }

            return response.getData();

        } catch (Exception ex) {
            log.error("Error while calling Employee Service", ex);
            return Collections.emptyList(); // prevent payroll crash
        }
    }

    /**
     * Replace with real JWT when auth server is available
     */
    private String getServiceToken() {
        return "INTERNAL_SERVICE_TOKEN";
    }
}
