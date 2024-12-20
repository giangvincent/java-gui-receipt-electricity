public class BienLai {
    private KhachHang customer;
    private int newElectricityNumber;
    private int oldElectricityNumber;
    private int receiptAmount;

    public BienLai(KhachHang customer, int newElectricityNumber, int oldElectricityNumber) {
        this.customer = customer;
        this.newElectricityNumber = newElectricityNumber;
        this.oldElectricityNumber = oldElectricityNumber;
        calculateReceiptAmount();
    }

    // Method to calculate receipt amount
    public void calculateReceiptAmount() {
        this.receiptAmount = (newElectricityNumber - oldElectricityNumber) * 5;
    }

    // Getters and setters
    public KhachHang getCustomer() { return customer; }
    public void setCustomer(KhachHang customer) { this.customer = customer; }

    public int getNewElectricityNumber() { return newElectricityNumber; }
    public void setNewElectricityNumber(int newElectricityNumber) {
        this.newElectricityNumber = newElectricityNumber;
        calculateReceiptAmount();
    }

    public int getOldElectricityNumber() { return oldElectricityNumber; }
    public void setOldElectricityNumber(int oldElectricityNumber) {
        this.oldElectricityNumber = oldElectricityNumber;
        calculateReceiptAmount();
    }

    public int getReceiptAmount() { return receiptAmount; }
}