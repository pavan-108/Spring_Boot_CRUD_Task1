package com.example.demo.controllerTests;

import com.example.demo.controller.Controller;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    public void testCreateDepartment_ValidInput() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("John Doe");

        when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(department)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }


    @Test
    public void testGetDepartmentById_DepartmentExists() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("John Doe");

        when(departmentService.getDepartmentById(1L)).thenReturn(department);

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void testGetDepartmentById_DepartmentNotExists() throws Exception {
        when(departmentService.getDepartmentById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateDepartment_ValidInput() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("John Doe");

        when(departmentService.updateDepartment(eq(1L), any(Department.class))).thenReturn(department);

        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(department)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }


    @Test
    public void testDeleteDepartment_DepartmentExists() throws Exception {
        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isNoContent());
    }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
