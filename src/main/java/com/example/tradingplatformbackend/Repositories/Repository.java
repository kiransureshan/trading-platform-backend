package com.example.tradingplatformbackend.Repositories;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    public List<T> getAll();

    public boolean add(T newObj);

    public T getById(UUID id);

    public boolean deleteById(UUID id);

    public boolean existsById(UUID id);
}
