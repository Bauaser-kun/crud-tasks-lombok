package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.dto.TrelloBoardDto;
import com.crud.tasks.domain.dto.TrelloCardDto;
import com.crud.tasks.domain.dto.TrelloListDto;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {
    @Autowired
    private TrelloMapper mapper;

    @Test
    void testMapToBoards(){
        //Given
        List<TrelloBoardDto> dtos = new ArrayList<>();
        List<TrelloListDto> lists = new ArrayList<>();
        lists.add(new TrelloListDto("1", "List", true));
        dtos.add(new TrelloBoardDto("1", "Board", lists));

        //When
        List<TrelloBoard> testBoard = mapper.mapToBoards(dtos);

        //Then
        assertEquals(dtos.get(0).getName(), testBoard.get(0).getName());
        assertEquals(dtos.get(0).getId(), testBoard.get(0).getId());
        assertEquals(dtos.get(0).getLists().size(), testBoard.get(0).getLists().size());
    }

    @Test
    void testMapToBoardsDto(){
        //Given
        List<TrelloBoard> boards = new ArrayList<>();
        List<TrelloList> lists = new ArrayList<>();
        lists.add(new TrelloList("1", "List", true));
        boards.add(new TrelloBoard("1", "Board", lists));

        //When
        List<TrelloBoardDto> testBoard = mapper.mapToBoardsDto(boards);

        //Then
        assertEquals(boards.get(0).getName(), testBoard.get(0).getName());
        assertEquals(boards.get(0).getId(), testBoard.get(0).getId());
        assertEquals(boards.get(0).getLists().size(), testBoard.get(0).getLists().size());
    }

    @Test
    void testMapToList(){
        //Given
        List<TrelloListDto> lists = new ArrayList<>();
        lists.add(new TrelloListDto("1", "List", true));

        //When
        List<TrelloList> testList = mapper.mapToList(lists);

        //Then
        assertEquals(lists.get(0).getName(), testList.get(0).getName());
        assertEquals(lists.get(0).getId(), testList.get(0).getId());
        assertEquals(lists.get(0).isClosed(), testList.get(0).isClosed());
    }

    @Test
    void testMapToListDto(){
        //Given
        List<TrelloList> lists = new ArrayList<>();
        lists.add(new TrelloList("1", "List", true));

        //When
        List<TrelloListDto> testList = mapper.mapToListDto(lists);

        //Then
        assertEquals(lists.get(0).getName(), testList.get(0).getName());
        assertEquals(lists.get(0).getId(), testList.get(0).getId());
        assertEquals(lists.get(0).isClosed(), testList.get(0).isClosed());
    }

    @Test
    void testMapToCard(){
        //Given
        TrelloCardDto card = new TrelloCardDto("card", "description", "top", "1");

        //When
        TrelloCard testCard = mapper.mapToCard(card);

        //Then
        assertEquals(card.getName(), testCard.getName());
        assertEquals(card.getDescription(), testCard.getDescription());
        assertEquals(card.getPosition(), testCard.getPos());
        assertEquals(card.getListId(), testCard.getListId());

    }

    @Test
    void testMapToCardDto(){
        //Given
        TrelloCard card = new TrelloCard("card", "description", "top", "1");

        //When
        TrelloCardDto testCard = mapper.mapToCardDto(card);

        //Then
        assertEquals(card.getName(), testCard.getName());
        assertEquals(card.getDescription(), testCard.getDescription());
        assertEquals(card.getPos(), testCard.getPosition());
        assertEquals(card.getListId(), testCard.getListId());

    }
}
