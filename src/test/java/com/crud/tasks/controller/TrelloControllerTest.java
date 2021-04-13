package com.crud.tasks.controller;

import com.crud.tasks.domain.dto.CreatedTrelloCardDto;
import com.crud.tasks.domain.dto.TrelloBoardDto;
import com.crud.tasks.domain.dto.TrelloCardDto;
import com.crud.tasks.domain.dto.TrelloListDto;
import com.crud.tasks.trello.mapper.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(TrelloController.class)
class TrelloControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        when(trelloFacade.fetchTrelloBoards()).thenReturn(List.of());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
            .get("/v1/trello/getTrelloBoards")
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTrelloBoards() throws Exception {
        //Given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "Test list", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1", "Test Task", trelloLists));
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/trello/getTrelloBoards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Test Task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].name", Matchers.is("Test list")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].closed", Matchers.is(false)));
    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test", "Test description", "top", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("232", "Test", "http://test.com");

        when(trelloFacade.createCard(any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);

        //When & Then
         mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/trello/createTrelloCard")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("232")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl", Matchers.is("http://test.com")));
    }
}