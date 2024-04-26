package com.hemaoid.nosql.cassandra.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hemaoid.nosql.cassandra.entity.Employee;
import com.hemaoid.nosql.cassandra.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testFindAll() throws Exception {
        List<Employee> expectedList = buildTestData();
        when(employeeService.findAll()).thenReturn(expectedList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee")).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        List<Employee> actualList = mapper.readValue(response, new TypeReference<List<Employee>>() {
        });
        Assertions.assertEquals(expectedList.size(), actualList.size());
        verify(employeeService).findAll();
    }

    @Test
    public void testSave() throws Exception {
        Employee payload = buildTestData().get(0);
        doNothing().when(employeeService).saveEmployee(any(Employee.class));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(payload))).andReturn();
        Assertions.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        verify(employeeService).saveEmployee(any(Employee.class));
    }

    @Test
    public void testSaveAll() throws Exception {
        doNothing().when(employeeService).saveAll(any(List.class));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/employee/all").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapper.writeValueAsString(buildTestData()))).andReturn();
        Assertions.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        verify(employeeService).saveAll(any(List.class));
    }

    @Test
    public void testUpdateById() throws Exception {
        Employee expected = buildTestData().get(0);
        String payload = mapper.writeValueAsString(expected);
        when(employeeService.updateById(anyInt(), any(Employee.class))).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/employee/1").contentType(MediaType.APPLICATION_JSON_VALUE).content(payload)).andReturn();
        Employee actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class);
        Assertions.assertEquals(payload, mapper.writeValueAsString(actual));
        verify(employeeService).updateById(anyInt(), any(Employee.class));
    }

    @Test
    public void testDeleteById() throws Exception {
        Employee expected = buildTestData().get(0);
        String payload = mapper.writeValueAsString(expected);
        doNothing().when(employeeService).deleteById(anyInt());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1")).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        verify(employeeService).deleteById(anyInt());
    }

    private List<Employee> buildTestData() {
        return List.of(new Employee(1, "TEST", 1000));
    }
}
