package fr.polytech.codev.backend.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DaoServices<T> {

    public T get(Serializable id);

    public List<T> getAll();

    public List<T> filter(Map<String, String> parameters);

    public int count();

    public void insert(T object);

    public void update(T object);

    public void delete(T object);
}