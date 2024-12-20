import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for electricity readings.");
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