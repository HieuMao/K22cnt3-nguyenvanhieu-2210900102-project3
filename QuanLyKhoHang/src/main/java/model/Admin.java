package model;

import java.sql.Timestamp;

public class Admin {
    private int nvh_admin_id;
    private String nvh_username;
    private String nvh_password;
    private Timestamp created_at; // Có thể sử dụng nếu cần hiển thị thời gian tạo tài khoản

    public Admin() {
    }

    public Admin(int nvh_admin_id, String nvh_username, String nvh_password, Timestamp created_at) {
        this.nvh_admin_id = nvh_admin_id;
        this.nvh_username = nvh_username;
        this.nvh_password = nvh_password;
        this.created_at = created_at;
    }

    public int getNvh_admin_id() {
        return nvh_admin_id;
    }
    public void setNvh_admin_id(int nvh_admin_id) {
        this.nvh_admin_id = nvh_admin_id;
    }

    public String getNvh_username() {
        return nvh_username;
    }
    public void setNvh_username(String nvh_username) {
        this.nvh_username = nvh_username;
    }

    public String getNvh_password() {
        return nvh_password;
    }
    public void setNvh_password(String nvh_password) {
        this.nvh_password = nvh_password;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
