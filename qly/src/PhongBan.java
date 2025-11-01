/**
 * Lớp (model) đại diện cho đối tượng Phòng Ban.
 */
public class PhongBan {
    private String maPhongBan;
    private String tenPhongBan;

    // Constructor
    public PhongBan(String maPhongBan, String tenPhongBan) {
        this.maPhongBan = maPhongBan;
        this.tenPhongBan = tenPhongBan;
    }

    // Getters
    public String getMaPhongBan() {
        return maPhongBan;
    }

    public String getTenPhongBan() {
        return tenPhongBan;
    }

    // Setters
    public void setMaPhongBan(String maPhongBan) {
        this.maPhongBan = maPhongBan;
    }

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }

    @Override
    public String toString() {
        // Dùng để hiển thị tên trong JComboBox
        return tenPhongBan; 
    }
}