package service;

import model.HasId;

import java.util.ArrayList;

public class Service<Id, T extends HasId<Id>> {
    public Service() {
    }

    public void save(T elem) {
        //repository.save(elem);
    }

    public int size() {
        return 0;
        //return repository.size();
    }


    public void delete(T elem) {
        //repository.delete(elem);

    }

    public void put(T elem) {
        //repository.put(elem);

    }

    public ArrayList<T> getAll() {
        return null;
        //return (ArrayList<T>) repository.getAll();
    }

    public T findById(Id id) {
        return null;
        //return repository.findById(id);
    }
}