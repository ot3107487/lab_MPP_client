package service;

import java.util.ArrayList;

public abstract class Service<T>{
    protected String urlApi;
    public Service(String urlApi) {
        this.urlApi=urlApi;
    }

    public abstract void save(T elem);

    public abstract int size();


    public abstract void delete(T elem);

    public abstract void put(T elem);

    public abstract ArrayList<T> getAll();

    public abstract T findById(int id);
}
