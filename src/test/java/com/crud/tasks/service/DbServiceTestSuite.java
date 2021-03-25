package com.crud.tasks.service;

import com.crud.tasks.domain.appObjects.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DbServiceTestSuite {
    @InjectMocks
    DbService service;

    @Mock
    TaskRepository repository;

    @Test
    void testGetAllTasks(){
        //Given
        List<Task> tasks = List.of(new Task(1L, "test", "testDescription"));
        when(repository.findAll()).thenReturn(tasks);

        //When
        List<Task> testList = service.getAllTasks();

        //Then
        assertNotNull(testList);
        assertEquals(testList.get(0).getId(), tasks.get(0).getId());
        assertEquals(testList.get(0).getTitle(), tasks.get(0).getTitle());
        assertEquals(testList.get(0).getContent(), "testDescription");
    }

    @Test
    void testSaveTask(){
        //Given
        Task task = new Task(1L, "test task", "description");
        when(repository.save(task)).thenReturn(task);
        //When
        Task testTask = service.saveTask(task);
        //Then
        assertEquals(task.getId(), testTask.getId());
        assertEquals(task.getTitle(), testTask.getTitle());
        assertEquals(task.getContent(), testTask.getContent());
    }

    @Test
    void testGetTask(){
        //Given
        List<Task> tasks = List.of(new Task(1L, "test", "testDescription0"),
                new Task(2L, "test1", "testDescription1"));

        //When
       Optional<Task> task = service.getTask(1L);

        //Then
        assertEquals(1L, tasks.get(0).getId());
        assertEquals("test", tasks.get(0).getTitle());
        assertEquals(tasks.get(0).getContent(), "testDescription0");
    }

    @Test
    void testDeleteTask(){
        //Given
        Task task = new Task(1L, "test task", "description");
        when(repository.save(task)).thenReturn(task);

        //When
        Task testTask = service.saveTask(task);

        //Then
        assertEquals(1L, testTask.getId());
        assertEquals("test task", testTask.getTitle());
        assertEquals("description", testTask.getContent());
    }
}
