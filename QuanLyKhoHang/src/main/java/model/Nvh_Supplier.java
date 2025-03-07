package model;

public class Nvh_Supplier {
    private int nvhSupplierId;
    private String nvhName;
    private String nvhContactInfo;
    private String nvhAddress;

    public Nvh_Supplier() {}

    public Nvh_Supplier(int nvhSupplierId, String nvhName, String nvhContactInfo, String nvhAddress) {
        this.nvhSupplierId = nvhSupplierId;
        this.nvhName = nvhName;
        this.nvhContactInfo = nvhContactInfo;
        this.nvhAddress = nvhAddress;
    }

    public int getNvhSupplierId() {
        return nvhSupplierId;
    }
    public void setNvhSupplierId(int nvhSupplierId) {
        this.nvhSupplierId = nvhSupplierId;
    }

    public String getNvhName() {
        return nvhName;
    }
    public void setNvhName(String nvhName) {
        this.nvhName = nvhName;
    }

    public String getNvhContactInfo() {
        return nvhContactInfo;
    }
    public void setNvhContactInfo(String nvhContactInfo) {
        this.nvhContactInfo = nvhContactInfo;
    }

    public String getNvhAddress() {
        return nvhAddress;
    }
    public void setNvhAddress(String nvhAddress) {
        this.nvhAddress = nvhAddress;
    }
}
