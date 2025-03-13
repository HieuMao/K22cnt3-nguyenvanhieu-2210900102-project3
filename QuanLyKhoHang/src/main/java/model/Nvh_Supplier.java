package model;

public class Nvh_Supplier {
    private int nvh_Supplier_Id;
    private String nvh_Name;
    private String nvh_Contact_Info;
    private String nvh_Address;
    private int displayIndex;

    public Nvh_Supplier() {}

    public Nvh_Supplier(int nvh_Supplier_Id, String nvh_Name, String nvh_Contact_Info, String nvh_Address) {
        this.nvh_Supplier_Id = nvh_Supplier_Id;
        this.nvh_Name = nvh_Name;
        this.nvh_Contact_Info = nvh_Contact_Info;
        this.nvh_Address = nvh_Address;
    }

    public int getnvh_Supplier_Id() {
        return nvh_Supplier_Id;
    }
    public void setnvh_Supplier_Id(int nvh_Supplier_Id) {
        this.nvh_Supplier_Id = nvh_Supplier_Id;
    }

    public String getnvh_Name() {
        return nvh_Name;
    }
    public void setnvh_Name(String nvh_Name) {
        this.nvh_Name = nvh_Name;
    }

    public String getnvh_Contact_Info() {
        return nvh_Contact_Info;
    }
    public void setnvh_Contact_Info(String nvh_Contact_Info) {
        this.nvh_Contact_Info = nvh_Contact_Info;
    }

    public String getnvh_Address() {
        return nvh_Address;
    }
    public void setnvh_Address(String nvh_Address) {
        this.nvh_Address = nvh_Address;
    }
    public int getDisplayIndex() {
        return displayIndex;
    }
    public void setDisplayIndex(int displayIndex) {
        this.displayIndex = displayIndex;
    }
}

