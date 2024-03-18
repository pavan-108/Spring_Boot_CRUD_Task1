package com.example.demo.serviceTests;

import com.example.demo.controller.Controller;
import com.example.demo.model.Department;
import com.example.demo.respository.IDepartmentRepository;
import com.example.demo.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class DepartmentServiceTest {

    @Mock
    private IDepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    // Test for creating a department
    @Test
    public void testCreateDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setName("John Doe");

        when(departmentRepository.save(department)).thenReturn(department);

        Department createdDepartment = departmentService.createDepartment(department);

        assertNotNull(createdDepartment);
        assertEquals(department.getName(), createdDepartment.getName());
    }

    // Test for getting a department by ID
    @Test
    public void testGetDepartmentById() {
        Department department = new Department();
        department.setId(1L);
        department.setName("John Doe");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Department retrievedDepartment = departmentService.getDepartmentById(1L);

        assertNotNull(retrievedDepartment);
        assertEquals(department.getName(), retrievedDepartment.getName());
    }

    // Test for updating a department
    @Test
    public void testUpdateDepartment() {
        Department existingDepartment = new Department();
        existingDepartment.setId(1L);
        existingDepartment.setName("John Doe");

        Department updatedDepartment = new Department();
        updatedDepartment.setId(1L);
        updatedDepartment.setName("Jane Doe");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(existingDepartment));
        when(departmentRepository.save(existingDepartment)).thenReturn(updatedDepartment);

        Department result = departmentService.updateDepartment(1L, updatedDepartment);

        assertNotNull(result);
        assertEquals(updatedDepartment.getName(), result.getName());
    }

    // Test for deleting a department
    @Test
    public void testDeleteDepartment() {
        Department department = new Department();
        department.setId(1L);
        department.setName("John Doe");

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        departmentService.deleteDepartment(1L);

        verify(departmentRepository, times(1)).delete(department);
    }
}
