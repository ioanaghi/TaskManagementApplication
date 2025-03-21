package app.pt2025_30424_ghinet_ioanateodora_assignment_1.PersistanceLayer;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer.TaskManagement;
import java.io.*;

public class SerializationOperations {

    public static void saveData(TaskManagement taskManagement, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(taskManagement);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    public static TaskManagement loadData(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("No existing data found: " + filename + ". Initializing new TaskManagement instance.");
            return new TaskManagement(false);
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("Loading data from: " + filename);
            return (TaskManagement) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("E  rror loading data: " + e.getMessage());
            return new TaskManagement(false);
        }
    }
}