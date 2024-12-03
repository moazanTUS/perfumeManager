package service;

import java.rmi.Remote;

import java.rmi.RemoteException;
import java.util.ArrayList;

//get list of perfumes from db
public interface PerfumeInterface extends Remote {
    ArrayList<Perfume> getPerfumes() throws RemoteException;
    
    //add perfume to db
    void addPerfume(String name, String brand, double price, int quantity) throws RemoteException;
    //update perfume in db
    void updatePerfume(int index, Perfume perfume) throws RemoteException;
    //delete perfume to db
    void deletePerfume(int index) throws RemoteException;
    
    //mark as luxury on selected perfume index on db
    void markAsLuxury(int index) throws RemoteException;
}
