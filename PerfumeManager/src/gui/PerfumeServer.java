package gui;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import service.PerfumeCRUD;
import service.PerfumeInterface;

public class PerfumeServer {
	
    public static void main(String[] args) {
        try {
        	//create service instance and bind it to registry non specific port notes one didnt work
            PerfumeInterface service = new PerfumeCRUD();
            Registry registry = LocateRegistry.createRegistry(1023);//set up registry on specific port
            registry.rebind("PerfumeService", service);
            System.out.println("running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
