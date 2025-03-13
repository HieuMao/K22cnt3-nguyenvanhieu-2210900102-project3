package model;

import java.sql.Timestamp;

public class Nvh_RequestExport {
    private int nvhRequestId;
    private int nvhProductId;
    private int nvhQuantity;
    private Timestamp nvhRequestDate;
    private String nvhStatus;
    public Nvh_RequestExport() {
    }


    // Constructor đầy đủ
    public Nvh_RequestExport(int nvhRequestId, int nvhProductId, int nvhQuantity, Timestamp nvhRequestDate, String nvhStatus) {
        this.nvhRequestId = nvhRequestId;
        this.nvhProductId = nvhProductId;
        this.nvhQuantity = nvhQuantity;
        this.nvhRequestDate = nvhRequestDate;
        this.nvhStatus = nvhStatus;
    }

    // Constructor khi chưa có ID (dùng cho INSERT)
    public Nvh_RequestExport(int nvhProductId, int nvhQuantity, String nvhStatus) {
        this.nvhProductId = nvhProductId;
        this.nvhQuantity = nvhQuantity;
        this.nvhStatus = nvhStatus;
    }

    // Getters và Setters
    public int getNvhRequestId() {
        return nvhRequestId;
    }

    public void setNvhRequestId(int nvhRequestId) {
        this.nvhRequestId = nvhRequestId;
    }

    public int getNvhProductId() {
        return nvhProductId;
    }

    public void setNvhProductId(int nvhProductId) {
        this.nvhProductId = nvhProductId;
    }

    public int getNvhQuantity() {
        return nvhQuantity;
    }

    public void setNvhQuantity(int nvhQuantity) {
        this.nvhQuantity = nvhQuantity;
    }

    public Timestamp getNvhRequestDate() {
        return nvhRequestDate;
    }

    public void setNvhRequestDate(Timestamp nvhRequestDate) {
        this.nvhRequestDate = nvhRequestDate;
    }

    public String getNvhStatus() {
        return nvhStatus;
    }

    public void setNvhStatus(String nvhStatus) {
        this.nvhStatus = nvhStatus;
    }
}
