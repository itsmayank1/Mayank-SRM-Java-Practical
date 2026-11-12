package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HospitalWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testIndexDashboardEndpoint() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("totalDepartments"))
                .andExpect(model().attributeExists("totalDoctors"))
                .andExpect(model().attributeExists("totalPatients"))
                .andExpect(model().attributeExists("totalAppointments"));
    }

    @Test
    void testDepartmentsEndpoint() throws Exception {
        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(view().name("departments"))
                .andExpect(model().attributeExists("departments"));
    }

    @Test
    void testDoctorsEndpointWithFiltering() throws Exception {
        mockMvc.perform(get("/doctors").param("keyword", "Cardio"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctors"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(model().attributeExists("departments"));
    }

    @Test
    void testPatientsEndpoint() throws Exception {
        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patients"));
    }

    @Test
    void testAppointmentsEndpointWithStatusFilter() throws Exception {
        mockMvc.perform(get("/appointments").param("status", "Confirmed"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments"))
                .andExpect(model().attributeExists("appointments"));
    }

    @Test
    void testApiStatsReturnsValidJson() throws Exception {
        mockMvc.perform(get("/api/stats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalDepartments", greaterThanOrEqualTo(5)))
                .andExpect(jsonPath("$.totalDoctors", greaterThanOrEqualTo(6)));
    }

    @Test
    void testApiDepartmentsReturnsJsonList() throws Exception {
        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))))
                .andExpect(jsonPath("$[0].name").exists());
    }
}
