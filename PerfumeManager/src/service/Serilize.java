package service;

import java.io.*;
import java.util.ArrayList;

public class Serilize {
    private static final String File= "perfumes.ser";  

    
    //save perfumes to file

    public static void savePerfumes(ArrayList<Perfume> perfumes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(File))) {
            oos.writeObject(perfumes);  //save
        } catch (IOException e) {
            System.err.println("error file not saved");
            e.printStackTrace();
        }
    }

//load perfume list from perfumes.ser 
    public static ArrayList<Perfume> loadPerfumes() {
        File file = new File(File);
        if (!file.exists()) {
            System.out.println(File + " does not exist");
            return new ArrayList<>();  // Return an empty list if the file does not exist
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(File))) {
            ArrayList<Perfume> perfumes = (ArrayList<Perfume>) ois.readObject();  // Read the perfumes list from the file
            return perfumes;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("error not saved.");
            e.printStackTrace();
            return new ArrayList<>();  // Return an empty list if an error occurs during loading
        }
    }


}
