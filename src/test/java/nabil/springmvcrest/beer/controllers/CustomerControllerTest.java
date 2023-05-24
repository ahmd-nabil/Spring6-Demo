package nabil.springmvcrest.beer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nabil.springmvcrest.beer.model.CustomerDTO;
import nabil.springmvcrest.beer.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerDTOArgumentCaptor;

    CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        customerDTO = CustomerDTO.builder().id(UUID.randomUUID()).firstName("Ahmed").lastName("Nabil").build();
    }

    @Test
    void testGetAllCustomers() throws Exception {
        given(customerService.findAll(any(), any())).willReturn(new PageImpl<>(Collections.singletonList(customerDTO)));
        mockMvc.perform(get(CustomerController.CUSTOMER_API).accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(1)));
    }

    @Test
    void testGetCustomerById() throws Exception {
        given(customerService.findById(customerDTO.getId())).willReturn(Optional.of(customerDTO));
        mockMvc.perform(get(CustomerController.CUSTOMER_API_ID, customerDTO.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("Ahmed")));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        given(customerService.findById(any())).willReturn(Optional.empty());
        mockMvc.perform(get(CustomerController.CUSTOMER_API_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException));
//        assertThrows(ResourceNotFoundException.class,()->mockMvc.perform(get(CustomerController.CUSTOMER_API_ID, UUID.randomUUID())));
    }

    @Test
    void testSaveCustomer() throws Exception {
        given(customerService.add(any(CustomerDTO.class))).willReturn(customerDTO);
        mockMvc.perform(post(CustomerController.CUSTOMER_API)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
        verify(customerService, times(1)).add(any(CustomerDTO.class));
    }

    @Test
    void testUpdateCustomerSuccess() throws Exception {
        given(customerService.update(any(UUID.class), any(CustomerDTO.class))).willReturn(Optional.of(customerDTO));
        mockMvc.perform(put(CustomerController.CUSTOMER_API_ID, customerDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Location"));

        verify(customerService).update(uuidArgumentCaptor.capture(), customerDTOArgumentCaptor.capture());
        assertEquals(customerDTO.getId(), uuidArgumentCaptor.getValue());
        assertEquals(customerDTO, customerDTOArgumentCaptor.getValue());
    }
}