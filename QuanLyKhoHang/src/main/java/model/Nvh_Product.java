package model;

public class Nvh_Product {
    private int nvh_Product_Id;
    private String nvh_Name;
    private String nvh_Description;
    private double nvh_Price;
    private int nvh_Quantity;
    private int nvh_Supplier_Id;
    private String nvh_Supplier_Name; // Thêm trường này để lưu tên nhà cung cấp

    public Nvh_Product() {
    }

    // Constructor với 6 tham số (không có tên NCC)
    public Nvh_Product(int nvh_Product_Id, String nvh_Name, String nvh_Description, 
                       double nvh_Price, int nvh_Quantity, int nvh_Supplier_Id) {
        this.nvh_Product_Id = nvh_Product_Id;
        this.nvh_Name = nvh_Name;
        this.nvh_Description = nvh_Description;
        this.nvh_Price = nvh_Price;
        this.nvh_Quantity = nvh_Quantity;
        this.nvh_Supplier_Id = nvh_Supplier_Id;
    }

    // Constructor với 7 tham số (bao gồm tên nhà cung cấp)
    public Nvh_Product(int nvh_Product_Id, String nvh_Name, String nvh_Description, 
                       double nvh_Price, int nvh_Quantity, int nvh_Supplier_Id, String nvh_Supplier_Name) {
        this.nvh_Product_Id = nvh_Product_Id;
        this.nvh_Name = nvh_Name;
        this.nvh_Description = nvh_Description;
        this.nvh_Price = nvh_Price;
        this.nvh_Quantity = nvh_Quantity;
        this.nvh_Supplier_Id = nvh_Supplier_Id;
        this.nvh_Supplier_Name = nvh_Supplier_Name;
    }

    // Getters & Setters
    public int getNvhProductId() {
        return nvh_Product_Id;
    }
    public void setNvhProductId(int nvh_Product_Id) {
        this.nvh_Product_Id = nvh_Product_Id;
    }

    public String getNvhName() {
        return nvh_Name;
    }
    public void setNvhName(String nvh_Name) {
        this.nvh_Name = nvh_Name;
    }

    public String getNvhDescription() {
        return nvh_Description;
    }
    public void setNvhDescription(String nvh_Description) {
        this.nvh_Description = nvh_Description;
    }

    public double getNvhPrice() {
        return nvh_Price;
    }
    public void setNvhPrice(double nvh_Price) {
        this.nvh_Price = nvh_Price;
    }

    public int getNvhQuantity() {
        return nvh_Quantity;
    }
    public void setNvhQuantity(int nvh_Quantity) {
        this.nvh_Quantity = nvh_Quantity;
    }

    public int getNvhSupplierId() {
        return nvh_Supplier_Id;
    }
    public void setNvhSupplierId(int nvh_Supplier_Id) {
        this.nvh_Supplier_Id = nvh_Supplier_Id;
    }

    public String getNvhSupplierName() {
        return nvh_Supplier_Name;
    }
    public void setNvhSupplierName(String nvh_Supplier_Name) {
        this.nvh_Supplier_Name = nvh_Supplier_Name;
    }
}
