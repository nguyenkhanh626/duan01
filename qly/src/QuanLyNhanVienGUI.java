import javax.swing.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class QuanLyNhanVienGUI extends JFrame {

    
    List<NhanVien> danhSachNV;
    List<PhongBan> danhSachPB;
    List<DuAn> danhSachDuAn;

    // === CÁC TAB (VIEW) ===
    // (Chúng ta cần tham chiếu đến các tab để gọi phương thức refresh)
    private TabNhanVien tabNhanVien;
    private TabPhongBan tabPhongBan;
    private TabDuAn tabDuAn;
    private TabHieuSuat tabHieuSuat;
    private TabLuong tabLuong;
    
    // Bộ định dạng tiền tệ
    NumberFormat currencyFormatter;

    public QuanLyNhanVienGUI() {
        setTitle("Phần mềm Quản lý Nhân sự");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        currencyFormatter = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));

        // Khởi tạo các danh sách
        danhSachNV = new ArrayList<>();
        danhSachPB = new ArrayList<>();
        danhSachDuAn = new ArrayList<>();

        // Tải dữ liệu mẫu (chỉ tải data, không đụng đến model)
        loadSampleDataPB();
        loadSampleDataNV();
        loadSampleDataDuAn();
        
        // Khởi tạo JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // === KHỞI TẠO CÁC TAB ===
        // "this" = truyền chính JFrame này vào các tab
        tabNhanVien = new TabNhanVien(this);
        tabPhongBan = new TabPhongBan(this);
        tabDuAn = new TabDuAn(this);
        tabLuong = new TabLuong(this);
        tabHieuSuat = new TabHieuSuat(this);

        // Thêm các tab vào
        tabbedPane.addTab("Quản lý Nhân viên", null, tabNhanVien, "Quản lý thông tin nhân viên");
        tabbedPane.addTab("Xem theo Phòng ban", null, tabPhongBan, "Xem nhân viên theo phòng ban");
        tabbedPane.addTab("Quản lý Dự án", null, tabDuAn, "Quản lý các dự án");
        tabbedPane.addTab("Quản lý Lương", null, tabLuong, "Xem bảng lương nhân viên");
        tabbedPane.addTab("Quản lý Hiệu suất", null, tabHieuSuat, "Quản lý và đánh giá hiệu suất");

        add(tabbedPane);

        // === TẢI DỮ LIỆU LẦN ĐẦU CHO CÁC BẢNG ===
        // (Sau khi tất cả đã được khởi tạo)
        tabNhanVien.refreshTableNV();
        tabNhanVien.updatePhongBanComboBox();
        
        tabPhongBan.updatePhongBanComboBox();
        tabPhongBan.locNhanVienTheoPhongBan();
        
        tabDuAn.refreshTableDuAn();
        tabDuAn.updateDuAnComboBox();
        
        tabLuong.refreshLuongTable();
    }
    
    // =========================================================================
    // CÁC PHƯƠNG THỨC REFRESH CHUNG (PUBLIC)
    // (Để các tab gọi khi cần cập nhật lẫn nhau)
    // =========================================================================
    
    /** Làm mới bảng lương (TabLuong) */
    public void refreshLuongTable() {
        if (tabLuong != null) {
            tabLuong.refreshLuongTable();
        }
    }

    /** Làm mới bảng lọc NV của TabPhongBan */
    public void locNhanVienTheoPhongBan() {
        if (tabPhongBan != null) {
            tabPhongBan.locNhanVienTheoPhongBan();
        }
    }
    
    /** Làm mới bảng NV chính (TabNhanVien) */
    public void refreshTableNV() {
        if (tabNhanVien != null) {
            tabNhanVien.refreshTableNV();
        }
    }
    
    /** Cập nhật ComboBox chọn dự án (TabDuAn) */
    public void updateDuAnComboBox() {
         if (tabDuAn != null) {
            tabDuAn.updateDuAnComboBox();
        }
    }
    
    /** Cập nhật ComboBox chọn phòng ban (TabNhanVien và TabPhongBan) */
    public void updatePhongBanComboBox() {
        if (tabNhanVien != null) {
            tabNhanVien.updatePhongBanComboBox();
        }
        if (tabPhongBan != null) {
            tabPhongBan.updatePhongBanComboBox();
        }
    }
    
    // =========================================================================
    // TẢI DỮ LIỆU MẪU (Chỉ danh sách)
    // =========================================================================

    private void loadSampleDataPB() {
        danhSachPB.add(new PhongBan("KT", "Kỹ thuật"));
        danhSachPB.add(new PhongBan("KD", "Kinh doanh"));
        danhSachPB.add(new PhongBan("NS", "Nhân sự"));
    }
    
    private void loadSampleDataNV() {
        danhSachNV.add(new NhanVien("NV001", "Nguyễn Văn A", "Kỹ thuật", "0900111222", "a.nguyen@example.com", "01/01/1990", "123456789", 5));
        danhSachNV.add(new NhanVien("NV002", "Trần Thị B", "Kinh doanh", "0900333444", "b.tran@example.com", "02/02/1992", "987654321", 3));
        danhSachNV.add(new NhanVien("NV003", "Lê Văn C", "Nhân sự", "0900555666", "c.le@example.com", "03/03/1995", "111222333", 1));
        danhSachNV.add(new NhanVien("NV004", "Phạm Văn D", "Kỹ thuật", "0900777888", "d.pham@example.com", "04/04/1998", "444555666", 2));
    }
    
    private void loadSampleDataDuAn() {
        danhSachDuAn.add(new DuAn("DA01", "Website Thương mại điện tử", 3));
        danhSachDuAn.add(new DuAn("DA02", "Hệ thống CRM nội bộ", 2));
    }

    // Phương thức main để chạy ứng dụng
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyNhanVienGUI().setVisible(true));
    }
}