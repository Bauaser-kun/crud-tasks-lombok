package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.appObjects.AttachmentByType;
import com.crud.tasks.domain.appObjects.Badges;
import com.crud.tasks.domain.appObjects.Trello;
import com.crud.tasks.domain.dto.CreatedTrelloCardDto;
import com.crud.tasks.domain.dto.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrelloServiceTestSuite {
    @InjectMocks
    TrelloService service;

    @Mock
    AdminConfig config;

    @Mock
    TrelloClient client;

    @Mock
    SimpleEmailService emailService;

    @Test
    void testFetchTrelloBoards(){

    }

    @Test
    void testCreateTrelloCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("testTask", "TestDescription",
                "topPosition", "testID");
        CreatedTrelloCardDto createdCardDto = new CreatedTrelloCardDto("1",
                new Badges(3, new AttachmentByType(new Trello(2, 1))),
                "TestCard", "TestURL");
        when(client.createNewCard(trelloCardDto)).thenReturn(createdCardDto);
        //when(config.getAdminMail()).thenReturn("test@e.mail");

        //When
        CreatedTrelloCardDto cardDto = service.createTrelloCard(trelloCardDto);

        //Then
        assertEquals("1", cardDto.getId());
        assertEquals("TestCard", cardDto.getName());
        assertEquals("TestURL", cardDto.getShortUrl());
        assertEquals(2, cardDto.getBadges().getAttachments().getTrello().getBoard());
    }
}
