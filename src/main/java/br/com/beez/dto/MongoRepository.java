package br.com.beez.dto;

import java.util.LinkedList;

public interface MongoRepository<T, I> {

    T find(long id);

    void insert(T data);

    void replace(T data);

    void delete(long id);

    LinkedList<I> query(int maxValues);

}
