package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class PerfumeCRUD extends UnicastRemoteObject implements PerfumeInterface {
    private final ArrayList<Perfume> perfumes;

    public PerfumeCRUD() throws RemoteException {
        this.perfumes = loadPerfumesData();
    }
//load perfumes from file
    private ArrayList<Perfume> loadPerfumesData() {
        ArrayList<Perfume> loadedPerfumes = Serilize.loadPerfumes();
        return (loadedPerfumes != null) ? loadedPerfumes : new ArrayList<>();//if not found empty list
    }
//crud methods
    @Override
    public ArrayList<Perfume> getPerfumes() throws RemoteException {
        return new ArrayList<>(perfumes);
    }
//	add perfume and save data
    @Override
    public void addPerfume(String name, String brand, double price, int quantity) throws RemoteException {
        Perfume newPerfume = new Perfume(name, brand, price, quantity);
        perfumes.add(newPerfume);
        savePerfumesData();
    }
//update perfume by index and save data
    @Override
    public void updatePerfume(int index, Perfume updatedPerfume) throws RemoteException {
        if (isValidIndex(index)) {
            perfumes.set(index, updatedPerfume);
            savePerfumesData();
        } else {
            throw new RemoteException("error");
        }
    }
//delete perfume by index and save data
    @Override
    public void deletePerfume(int index) throws RemoteException {
        if (isValidIndex(index)) {
            perfumes.remove(index);
            savePerfumesData();
        } else {
            throw new RemoteException("error");
        }
    }
//mark as luxury by index and save data
    @Override
    public void markAsLuxury(int index) throws RemoteException {
        if (isValidIndex(index)) {
            Perfume originalPerfume = perfumes.get(index);
            perfumes.set(index, new LuxuryPerfume(originalPerfume));
            savePerfumesData();
        } else {
            throw new RemoteException("error");
        }
    }
//check if index is valid
    private boolean isValidIndex(int index) {
        return index >= 0 && index < perfumes.size();
    }

    private void savePerfumesData() {
        Serilize.savePerfumes(perfumes);
    }
}
