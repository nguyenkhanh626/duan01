import java.util.ArrayList;
import java.util.List;

/**
 * Lớp (model) mới đại diện cho đối tượng Dự án.
 */
public class DuAn {

    private String maDuAn;
    private String tenDuAn;
    private int doPhucTap; // 1, 2, hoặc 3

    // Danh sách nhân viên tham gia dự án này (quan hệ Many-to-Many)
    private List<NhanVien> danhSachThanhVien;

    // Constructor
    public DuAn(String maDuAn, String tenDuAn, int doPhucTap) {
        this.maDuAn = maDuAn;
        this.tenDuAn = tenDuAn;
        this.doPhucTap = doPhucTap;
        this.danhSachThanhVien = new ArrayList<>(); // Khởi tạo list
    }

    // Getters
    public String getMaDuAn() {
        return maDuAn;
    }

    public String getTenDuAn() {
        return tenDuAn;
    }

    public int getDoPhucTap() {
        return doPhucTap;
    }

    public List<NhanVien> getDanhSachThanhVien() {
        return danhSachThanhVien;
    }

    // Setters
    public void setTenDuAn(String tenDuAn) {
        this.tenDuAn = tenDuAn;
    }

    public void setDoPhucTap(int doPhucTap) {
        this.doPhucTap = doPhucTap;
    }

    // Phương thức logic để thêm thành viên
    public void addThanhVien(NhanVien nv) {
        this.danhSachThanhVien.add(nv);
    }
    
    // Kiểm tra xem nhân viên đã có trong dự án chưa
    public boolean hasThanhVien(NhanVien nv) {
        return this.danhSachThanhVien.contains(nv);
    }

    // Dùng để hiển thị tên trong JComboBox
    @Override
    public String toString() {
        return this.tenDuAn;
    }
}