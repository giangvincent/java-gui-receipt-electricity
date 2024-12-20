import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;

public class ElectricityReceiptGUI extends JFrame {
    private JTextField firstNameField, lastNameField, houseNumberField, indicatorIdField;
    private JTextField newElectricityNumberField, oldElectricityNumberField;
    private JTextArea receiptArea;
    private JButton createCustomerButton, createReceiptButton, updateCustomerButton;

    private KhachHang currentCustomer;
    private BienLai currentReceipt;

    public ElectricityReceiptGUI() {
        // Initialize components
        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        houseNumberField = new JTextField(10);
        indicatorIdField = new JTextField(10);
        newElectricityNumberField = new JTextField(10);
        oldElectricityNumberField = new JTextField(10);
        receiptArea = new JTextArea(20, 30);
        createCustomerButton = new JButton("Thêm khách hàng");
        createReceiptButton = new JButton("Tạo hoá đơn");
        updateCustomerButton = new JButton("Cập nhật khách hàng");

        // Set up the layout
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new GridLayout(5, 2));
        customerPanel.add(new JLabel("Tên:"));
        customerPanel.add(firstNameField);
        customerPanel.add(new JLabel("Họ:"));
        customerPanel.add(lastNameField);
        customerPanel.add(new JLabel("Số Nhà:"));
        customerPanel.add(houseNumberField);
        customerPanel.add(new JLabel("Chỉ số công tơ:"));
        customerPanel.add(indicatorIdField);
        customerPanel.add(createCustomerButton);
        customerPanel.add(updateCustomerButton);

        JPanel receiptPanel = new JPanel();
        receiptPanel.setLayout(new GridLayout(3, 2));
        receiptPanel.add(new JLabel("Số điện mới:"));
        receiptPanel.add(newElectricityNumberField);
        receiptPanel.add(new JLabel("Số điện cũ:"));
        receiptPanel.add(oldElectricityNumberField);
        receiptPanel.add(createReceiptButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(customerPanel, BorderLayout.NORTH);
        mainPanel.add(receiptPanel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(receiptArea), BorderLayout.SOUTH);

        // Add action listeners
        createCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCustomer();
            }
        });

        createReceiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReceipt();
            }
        });

        updateCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
            }
        });

        // Frame settings
        setTitle("Electricity Receipt Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void createCustomer() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String houseNumber = houseNumberField.getText();
        String indicatorId = indicatorIdField.getText();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !houseNumber.isEmpty() && !indicatorId.isEmpty()) {
            currentCustomer = new KhachHang(firstName, lastName, houseNumber, indicatorId);
            receiptArea.append("Khách hàng: " + firstName + " " + lastName + "\n");
            saveDataToFile(indicatorId + ".txt");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all customer fields.");
        }
    }

    private void updateCustomer() {
        if (currentCustomer == null) {
            JOptionPane.showMessageDialog(this, "Please create a customer first.");
            return;
        }

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String houseNumber = houseNumberField.getText();
        String indicatorId = indicatorIdField.getText();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !houseNumber.isEmpty() && !indicatorId.isEmpty()) {
            currentCustomer.setFirstName(firstName);
            currentCustomer.setLastName(lastName);
            currentCustomer.setHouseNumber(houseNumber);
            currentCustomer.setIndicatorId(indicatorId);
            receiptArea.append("Customer Updated: " + firstName + " " + lastName + "\n");
            updateDataInFile(indicatorId + ".txt");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all customer fields.");
        }
    }

    private void createReceipt() {
        if (currentCustomer == null) {
            JOptionPane.showMessageDialog(this, "Please create a customer first.");
            return;
        }

        try {
            int newElectricityNumber = Integer.parseInt(newElectricityNumberField.getText());
            int oldElectricityNumber = Integer.parseInt(oldElectricityNumberField.getText());

            currentReceipt = new BienLai(currentCustomer, newElectricityNumber, oldElectricityNumber);
            receiptArea.append("Hoá đơn:\n");
            receiptArea.append("Khách hàng: " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + "\n");
            receiptArea.append("Số nhà: " + currentCustomer.getHouseNumber() + "\n");
            receiptArea.append("Công tơ điện: " + currentCustomer.getIndicatorId() + "\n");
            receiptArea.append("Chỉ số mới: " + newElectricityNumber + "\n");
            receiptArea.append("Chỉ số cũ: " + oldElectricityNumber + "\n");
            receiptArea.append("Số tiền phải trả: " + currentReceipt.getReceiptAmount() + "\n\n");
            saveDataToFile(currentCustomer.getIndicatorId() + ".txt");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for electricity readings.");
        }
    }

    private void saveDataToFile(String filePath) {
        StringBuilder data = new StringBuilder();
        String realPath = "data/" + filePath;

        // Add customer data if available
        if (currentCustomer != null) {
            data.append("Khách hàng: ").append(currentCustomer.getFirstName()).append(" ").append(currentCustomer.getLastName()).append("\n");
            data.append("Số nhà: ").append(currentCustomer.getHouseNumber()).append("\n");
            data.append("Công tơ điện: ").append(currentCustomer.getIndicatorId()).append("\n");
        }

        // Add receipt data if available
        if (currentReceipt != null) {
            data.append("Hoá đơn:\n");
            data.append("Khách hàng: ").append(currentCustomer.getFirstName()).append(" ").append(currentCustomer.getLastName()).append("\n");
            data.append("Số nhà: ").append(currentCustomer.getHouseNumber()).append("\n");
            data.append("Công tơ điện: ").append(currentCustomer.getIndicatorId()).append("\n");
            data.append("Chỉ số mới: ").append(currentReceipt.getNewElectricityNumber()).append("\n");
            data.append("Chỉ số cũ: ").append(currentReceipt.getOldElectricityNumber()).append("\n");
            data.append("Số tiền phải trả: ").append(currentReceipt.getReceiptAmount()).append("\n\n");
        }

        // Write data to file
        // Ensure the file exists, create if it doesn't
        File file = new File(realPath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to create file");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(realPath, true))) {
            writer.write(data.toString());
            JOptionPane.showMessageDialog(this, "Data saved to " + realPath);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save data");
        }
    }

    private void updateDataInFile(String filePath) {
        StringBuilder data = new StringBuilder();
        List<String> lines = new ArrayList<>();
        String realPath = "data/" + filePath;

        // Ensure the file exists, create if it doesn't
        File file = new File(realPath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to create file");
            return;
        }

        // Read existing data from the file
        try {
            lines = Files.readAllLines(Paths.get(realPath));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to read data from file");
            return;
        }

        // Update customer data if available
        boolean customerUpdated = false;
        if (currentCustomer != null) {
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains("Khách hàng: " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName())) {
                    // Update the customer info
                    lines.set(i, "Khách hàng: " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName());
                    lines.set(i + 1, "Số nhà: " + currentCustomer.getHouseNumber());
                    lines.set(i + 2, "Công tơ điện: " + currentCustomer.getIndicatorId());
                    customerUpdated = true;
                    break;
                }
            }
            if (!customerUpdated) {
                // Append new customer data if not found
                data.append("Khách hàng: ").append(currentCustomer.getFirstName()).append(" ").append(currentCustomer.getLastName()).append("\n");
                data.append("Số nhà: ").append(currentCustomer.getHouseNumber()).append("\n");
                data.append("Công tơ điện: ").append(currentCustomer.getIndicatorId()).append("\n");
            }
        }

        // Update receipt data if available
        if (currentReceipt != null) {
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains("Khách hàng: " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName())) {
                    // Update the receipt info
                    lines.add(i + 3, "Hoá đơn:");
                    lines.add(i + 4, "Khách hàng: " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName());
                    lines.add(i + 5, "Số nhà: " + currentCustomer.getHouseNumber());
                    lines.add(i + 6, "Công tơ điện: " + currentCustomer.getIndicatorId());
                    lines.add(i + 7, "Chỉ số mới: " + currentReceipt.getNewElectricityNumber());
                    lines.add(i + 8, "Chỉ số cũ: " + currentReceipt.getOldElectricityNumber());
                    lines.add(i + 9, "Số tiền phải trả: " + currentReceipt.getReceiptAmount() + "\n");
                    break;
                }
            }
            if (!customerUpdated) {
                // Append new receipt data if not found
                data.append("Hoá đơn:\n");
                data.append("Khách hàng: ").append(currentCustomer.getFirstName()).append(" ").append(currentCustomer.getLastName()).append("\n");
                data.append("Số nhà: ").append(currentCustomer.getHouseNumber()).append("\n");
                data.append("Công tơ điện: ").append(currentCustomer.getIndicatorId()).append("\n");
                data.append("Chỉ số mới: ").append(currentReceipt.getNewElectricityNumber()).append("\n");
                data.append("Chỉ số cũ: ").append(currentReceipt.getOldElectricityNumber()).append("\n");
                data.append("Số tiền phải trả: ").append(currentReceipt.getReceiptAmount()).append("\n\n");
            }
        }

        // Write updated data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(realPath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.write(data.toString());
            JOptionPane.showMessageDialog(this, "Data updated in " + realPath);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update data");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ElectricityReceiptGUI();
            }
        });
    }
}