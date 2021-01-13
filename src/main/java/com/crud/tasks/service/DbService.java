package com.crud.tasks.service;

import com.crud.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class DbService {
private final TaskRepository repository;

    public DbService(TaskRepository repository) {
        this.repository = repository;
    }
}
