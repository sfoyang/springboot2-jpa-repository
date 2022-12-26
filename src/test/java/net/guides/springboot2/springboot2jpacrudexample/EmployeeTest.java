package net.guides.springboot2.springboot2jpacrudexample;

import net.guides.springboot2.springboot2jpacrudexample.controller.EmployeeController;
import net.guides.springboot2.springboot2jpacrudexample.model.Employee;
import net.guides.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;
import net.guides.springboot2.springboot2jpacrudexample.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(EmployeeController.class)
@ExtendWith(SpringExtension.class)
public class EmployeeTest {

//    @MockBean
//    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private WebApplicationContext webApplicationContext;


//@Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).alwaysExpect(forwardedUrl(null)).build();
    }


    @Test
    public void checkEmployeeExist() throws Exception {
       Employee emp=new Employee();
       emp.setId(1L);
       emp.setEmailId("sfoyang@yahoo.fr");
       emp.setFirstName("Foy");
       emp.setLastName("Silv");
        when(employeeService.getEmployeeByI(1L)).thenReturn(emp);
        mockMvc.perform(get("/api/v1/employees/1")).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.firstName").value("Foy"))
                .andExpect(jsonPath("$.lastName").value("Silv")).andExpect(jsonPath("$.emailId").value("sfoyang@yahoo.fr"))
                .andExpect(jsonPath("$").isNotEmpty());
              //  .andExpect(forwardedUrl(null));

    }


    @Test
    public void testAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(new Employee()));
        mockMvc.perform(get("/api/v1/employees")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$").isArray());
    }
}
