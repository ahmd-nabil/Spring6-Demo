package nabil.springmvcrest.beer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nabil.springmvcrest.beer.entities.Beer;
import nabil.springmvcrest.beer.model.BeerDTO;
import nabil.springmvcrest.beer.services.BeerService;
import nabil.springmvcrest.beer.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// Test Splices: bring up a targeted segment of Auto-Configured Spring Boot Environment ie, just DB components or just Web components
// @WebMvcTest: is a Spring boot test splice which creates MockMvc environment for the controller/s under test
//              controller's dependencies is not included, we use mocks for that
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;    // this is the mock used when calling tests against constructor

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;
    BeerServiceImpl beerServiceImpl; // this is actual service to get a beer to return

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testGetAllBeersSuccess() throws Exception {
        given(beerService.findAll()).willReturn(beerServiceImpl.findAll());
        mockMvc.perform(get(BeerController.BEER_API).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getBeerByIdSuccessTest() throws Exception {
        BeerDTO testBeer = beerServiceImpl.findAll().get(0);
        given(beerService.findBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        mockMvc.perform(get(BeerController.BEER_API_ID, testBeer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$['beerName']", is(testBeer.getBeerName())));
    }

    @Test
    void testGetBeerByIdInvalidUUID() throws Exception {
        MvcResult result = mockMvc.perform(get(BeerController.BEER_API_ID, "INVALID UUID"))
                .andExpect(status().isBadRequest())
                .andReturn();
        System.out.println(result);
    }

    @Test
    void testAddBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.findAll().get(0);
        given(beerService.addNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.findAll().get(0));
        mockMvc.perform(post(BeerController.BEER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testAddBeerNullProperties() throws Exception {
        given(beerService.addNewBeer(any())).willReturn(BeerDTO.builder().id(UUID.randomUUID()).build());
        MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_API)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(BeerDTO.builder().build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(6)))
                .andReturn();
    }

    @Test
    void testUpdateBeerSuccess() throws Exception {
        BeerDTO beer = beerServiceImpl.findAll().get(0);
        given(beerService.updateBeer(any(UUID.class), any(BeerDTO.class))).willReturn(Optional.ofNullable(beer));
        mockMvc.perform(put(BeerController.BEER_API_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(header().exists("Location"))
                .andExpect(status().isNoContent());
        verify(beerService).updateBeer(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertEquals(beer.getId(), uuidArgumentCaptor.getValue());
        assertEquals(beer, beerArgumentCaptor.getValue());
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.findAll().get(0);
        given(beerService.delete(any())).willReturn(true);
        mockMvc.perform(delete(BeerController.BEER_API_ID, beer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(beerService, times(1)).delete(any(UUID.class));
    }

    @Test
    void testIdInBodyDelete() throws Exception {
        BeerDTO beer = beerServiceImpl.findAll().get(0);
        mockMvc.perform(delete(BeerController.BEER_API)
                .content(beer.getId().toString()))
                .andExpect(status().isNoContent());
        verify(beerService).delete(uuidArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beer.getId());
    }
}
