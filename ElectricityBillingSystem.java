import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class ElectricityBillingSystem extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ElectricityBillingSystem";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Patna@1234";

    private JTextField nameField, addressField, meterField, unitsField, meterSearchField;
    private JLabel billAmountLabel;

    public ElectricityBillingSystem() {
        setTitle("Electricity Billing System");
        setSize(450, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        panel.add(new JLabel("Customer Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Meter Number:"));
        meterField = new JTextField();
        panel.add(meterField);

        panel.add(new JLabel("Units Consumed:"));
        unitsField = new JTextField();
        panel.add(unitsField);

        panel.add(new JLabel("Bill Amount:"));
        billAmountLabel = new JLabel();
        panel.add(billAmountLabel);

        JButton calculateButton = new JButton("Calculate Bill");
        calculateButton.addActionListener(e -> calculateBill());
        panel.add(calculateButton);

        JButton saveButton = new JButton("Save Bill");
        saveButton.addActionListener(e -> saveBill());
        panel.add(saveButton);

        // Field and button for searching bill by meter number
        panel.add(new JLabel("Meter Number to View Bill:"));
        meterSearchField = new JTextField();
        panel.add(meterSearchField);

        JButton displayBillButton = new JButton("Display Bill");
        displayBillButton.addActionListener(e -> displayBill());
        panel.add(displayBillButton);

        JButton viewAllBillsButton = new JButton("View All Bills");
        viewAllBillsButton.addActionListener(e -> viewAllBills());
        panel.add(viewAllBillsButton);

        add(panel);
        setVisible(true);
    }

    private void calculateBill() {
        try {
            int units = Integer.parseInt(unitsField.getText());
            double ratePerUnit = 1.5;
            double billAmount = units * ratePerUnit;
            billAmountLabel.setText("₹ " + billAmount);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid units entered.");
        }
    }

    private void saveBill() {
        String customerName = nameField.getText();
        String address = addressField.getText();
        String meterNumber = meterField.getText();
        int units = Integer.parseInt(unitsField.getText());
        double billAmount = units * 1.5;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement("INSERT INTO bills (customer_name, address, meter_number, units_consumed, bill_amount, billing_date) VALUES (?, ?, ?, ?, ?, NOW())")) {

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, meterNumber);
            ps.setInt(4, units);
            ps.setDouble(5, billAmount);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Bill saved successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving bill: " + e.getMessage());
        }
    }

    private void displayBill() {
        String meterNumber = meterSearchField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM bills WHERE meter_number = ?")) {
            ps.setString(1, meterNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String customerName = rs.getString("customer_name");
                String address = rs.getString("address");
                int units = rs.getInt("units_consumed");
                double billAmount = rs.getDouble("bill_amount");
                String billingDate = rs.getDate("billing_date").toString();

                JOptionPane.showMessageDialog(this, "Customer: " + customerName + "\nAddress: " + address +
                        "\nUnits Consumed: " + units + "\nBill Amount: ₹" + billAmount + "\nBilling Date: " + billingDate);
            } else {
                JOptionPane.showMessageDialog(this, "No bill found for Meter Number: " + meterNumber);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving bill: " + e.getMessage());
        }
    }

    private void viewAllBills() {
        ArrayList<String> bills = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM bills")) {

            while (rs.next()) {
                String billInfo = "ID: " + rs.getInt("id") + ", Customer: " + rs.getString("customer_name") +
                        ", Meter: " + rs.getString("meter_number") + ", Units: " + rs.getInt("units_consumed") +
                        ", Amount: ₹" + rs.getDouble("bill_amount") + ", Date: " + rs.getDate("billing_date");
                bills.add(billInfo);
            }

            if (bills.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No bills found.");
            } else {
                // Display all bills in a new window
                JTextArea billArea = new JTextArea(15, 30);
                billArea.setText(String.join("\n", bills));
                billArea.setEditable(false);
                JOptionPane.showMessageDialog(this, new JScrollPane(billArea), "All Bills", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving all bills: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ElectricityBillingSystem::new);
    }
}
