package gui;

import javax.swing.*;

import service.Perfume;

import java.awt.*;
import java.util.ArrayList;

public class PerfumeGUIApp extends JFrame {
    private PerfumeClient perfumeClient; // The RMI client to interact with the server
    private DefaultListModel<String> listModel; // List model to store perfume data
    private JList<String> perfumeList; // List component to display perfumes

    public PerfumeGUIApp() {
        // Initialize the RMI client
        try {
            perfumeClient = new PerfumeClient();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to server", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1); // Exit if the server connection fails
        }

        // GUI setup
        setTitle("Perfume Manager");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(250, 250, 250));
        add(mainPanel, BorderLayout.CENTER);

        // Initialize list model and load data from server
        listModel = new DefaultListModel<>();
        updateListModel();
        
        perfumeList = new JList<>(listModel);
        perfumeList.setFont(new Font("Arial", Font.PLAIN, 16));
        perfumeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(perfumeList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Available Perfumes"));

        // Buttons 
        JButton addButton = new JButton("Add Perfume");
        JButton editButton = new JButton("Edit Perfume");
        JButton deleteButton = new JButton("Delete Perfume");
        JButton luxuryButton = new JButton("Mark as Luxury");

        // button style
        styleButton(addButton);
        styleButton(editButton);
        styleButton(deleteButton);
        styleButton(luxuryButton);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(250, 250, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // spacing
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(luxuryButton);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        // Disable buttons 
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        luxuryButton.setEnabled(false);

        // Enable button
        perfumeList.addListSelectionListener(e -> {
            boolean isItemSelected = perfumeList.getSelectedIndex() != -1;
            editButton.setEnabled(isItemSelected);
            deleteButton.setEnabled(isItemSelected);
            luxuryButton.setEnabled(isItemSelected);
        });

        // Button actions
        addButton.addActionListener(e -> addPerfume());
        editButton.addActionListener(e -> editPerfume());
        deleteButton.addActionListener(e -> deletePerfume());
        luxuryButton.addActionListener(e -> markAsLuxury());
    }
//crud
    private void addPerfume() {
    	//enter perfume details
        String name = JOptionPane.showInputDialog("Enter name:");
        if (name == null || name.isEmpty()) return;

        String brand = JOptionPane.showInputDialog("Enter brand:");
        if (brand == null || brand.isEmpty()) return;

        String priceInput = JOptionPane.showInputDialog("Enter price:");
        double price;
        try {
        	
        	//check if price is valid
            price = Double.parseDouble(priceInput);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price entered", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String quantityInput = JOptionPane.showInputDialog("Enter quantity in stock:");
        int quantity;
        try {
            quantity = Integer.parseInt(quantityInput);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity entered", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
        	//call add perfume method
            perfumeClient.addPerfume(name, brand, price, quantity);
            updateListModel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//edit perfume
    private void editPerfume() {
        int selectedIndex = perfumeList.getSelectedIndex();
        if (selectedIndex == -1) return;

        try {
        	//get perfume
            ArrayList<Perfume> perfumes = perfumeClient.getPerfumesFromServer();
            Perfume selectedPerfume = perfumes.get(selectedIndex);
//enter new details
            String name = JOptionPane.showInputDialog("Enter new name:", selectedPerfume.getName());
            if (name == null || name.isEmpty()) return;

            String brand = JOptionPane.showInputDialog("Enter new brand:", selectedPerfume.getBrand());
            if (brand == null || brand.isEmpty()) return;

            String priceInput = JOptionPane.showInputDialog("Enter new price:", selectedPerfume.getPrice());
            double price;
            try {
            	//check if price is valid
                price = Double.parseDouble(priceInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price entered", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String quantityInput = JOptionPane.showInputDialog("Enter new quantity in stock:", selectedPerfume.getQuantity());
            int quantity;
            try {
                quantity = Integer.parseInt(quantityInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity entered", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Perfume updatedPerfume = new Perfume(name, brand, price, quantity);
            perfumeClient.updatePerfume(selectedIndex, updatedPerfume);
            updateListModel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//delete perfume
    private void deletePerfume() {
    	//get selected
        int selectedIndex = perfumeList.getSelectedIndex();
        if (selectedIndex == -1) return;

        try {
        	//call delete perfume method
            perfumeClient.deletePerfume(selectedIndex);
            updateListModel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // Mark the selected perfume as luxury
    private void markAsLuxury() {
        int selectedIndex = perfumeList.getSelectedIndex();
        if (selectedIndex == -1) return;

        try {
            perfumeClient.markAsLuxury(selectedIndex);
            updateListModel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // Update the list model with the latest data from the server
    private void updateListModel() {
        listModel.clear();
        try {
            ArrayList<Perfume> perfumes = perfumeClient.getPerfumesFromServer();
            for (Perfume perfume : perfumes) {
                listModel.addElement(perfume.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load perfumes from server", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
//style buttons
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
    }
    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PerfumeGUIApp().setVisible(true));
    }
}
