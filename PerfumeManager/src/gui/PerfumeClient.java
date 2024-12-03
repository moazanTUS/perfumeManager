package gui;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import service.Perfume;
import service.PerfumeInterface;

public class PerfumeClient {
    private PerfumeInterface service;

    public PerfumeClient() throws Exception {
        this("localhost", 1023);
    }

    // Custom server config
    public PerfumeClient(String host, int port) throws Exception {
        connectToService(host, port);
    }
    // Connect to specific host and port
    private void connectToService(String host, int port) throws Exception {
        Registry registry = LocateRegistry.getRegistry(host, port);
        service = (PerfumeInterface) registry.lookup("PerfumeService");
    }
    // CRUD methods
    // Get list of perfumes from server
    public ArrayList<Perfume> getPerfumesFromServer() throws Exception {
        return service.getPerfumes();
    }
    // Add perfume to server database
    public void addPerfume(String name, String brand, double price, int quantity) throws Exception {
        service.addPerfume(name, brand, price, quantity);
    }
    // Update perfume on server database
    public void updatePerfume(int i, Perfume perfume) throws Exception {
        service.updatePerfume(i, perfume);
    }
    // Delete perfume from server database
    public void deletePerfume(int i) throws Exception {
        service.deletePerfume(i);
    }
    // Mark perfume as luxury on server database
    public void markAsLuxury(int i) throws Exception {
        service.markAsLuxury(i);
    }
}
