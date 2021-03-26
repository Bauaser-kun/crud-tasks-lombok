package com.crud.tasks.trello.validator;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TrelloValidatorTestSuite {
    @Mock
    TrelloValidator validator;

    @Test
    void testValidateCard() throws Exception{
        //Given
        TrelloCard card = new TrelloCard("card", "descritpion", "topPosition", "cardList");

        //When
        validator.validateCard(card);

        //Then
        verify(validator, times(1)).validateCard(card);
    }

    @Test
    void testValidateBoards() {
        //Given
        List<TrelloList> lists = new ArrayList<>();
        List<TrelloBoard> trelloBoards = List.of(new TrelloBoard("1", "name", lists),
         new TrelloBoard("2", "test", new ArrayList<>()));
        TrelloValidator trelloValidator = new TrelloValidator();

        //When
        List<TrelloBoard> testBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertEquals(1, testBoards.size());
    }
}
