import javax.swing.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class QuanLyNhanVienGUI extends JFrame {

    List<NhanVien> danhSachNV;
    List<PhongBan> danhSachPB;
    List<DuAn> danhSachDuAn;

    private TabNhanVien tabNhanVien;
    private TabPhongBan tabPhongBan;
    private TabDuAn tabDuAn;
    private TabHieuSuat tabHieuSuat;
    private TabLuong tabLuong;
    private TabBaoCao tabBaoCao;
    
    NumberFormat currencyFormatter;

    public QuanLyNhanVienGUI() {
        setTitle("Phần mềm Quản lý Nhân sự");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        currencyFormatter = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));

        danhSachNV = new ArrayList<>();
        danhSachPB = new ArrayList<>();
        danhSachDuAn = new ArrayList<>();

        loadSampleDataPB();
        loadSampleDataNV();
        loadSampleDataDuAn();
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabNhanVien = new TabNhanVien(this);
        tabPhongBan = new TabPhongBan(this);
        tabDuAn = new TabDuAn(this);
        tabLuong = new TabLuong(this);
        tabHieuSuat = new TabHieuSuat(this);
        tabBaoCao = new TabBaoCao(this);

        tabbedPane.addTab("Quản lý Nhân viên", null, tabNhanVien, "Quản lý thông tin nhân viên");
        tabbedPane.addTab("Xem theo Phòng ban", null, tabPhongBan, "Xem nhân viên theo phòng ban");
        tabbedPane.addTab("Quản lý Dự án", null, tabDuAn, "Quản lý các dự án");
        tabbedPane.addTab("Quản lý Lương", null, tabLuong, "Xem bảng lương nhân viên");
        tabbedPane.addTab("Quản lý Hiệu suất", null, tabHieuSuat, "Quản lý và đánh giá hiệu suất");
        tabbedPane.addTab("Báo cáo", null, tabBaoCao, "Báo cáo và Thống kê"); // MỚI

        add(tabbedPane);

        // first load data
        tabNhanVien.refreshTableNV();
        tabNhanVien.updatePhongBanComboBox();
        
        tabPhongBan.updatePhongBanComboBox();
        tabPhongBan.locNhanVienTheoPhongBan();
        
        tabDuAn.refreshTableDuAn();
        tabDuAn.updateDuAnComboBox();
        
        tabLuong.refreshLuongTable();
        tabBaoCao.refreshBaoCao(); 
    }
    
    public void refreshBaoCaoTab() {
        if (tabBaoCao != null) {
            tabBaoCao.refreshBaoCao();
        }
    }
    
    public void refreshLuongTable() {
        if (tabLuong != null) {
            tabLuong.refreshLuongTable();
        }
    }

    public void locNhanVienTheoPhongBan() {
        if (tabPhongBan != null) {
            tabPhongBan.locNhanVienTheoPhongBan();
        }
    }
    
    public void refreshTableNV() {
        if (tabNhanVien != null) {
            tabNhanVien.refreshTableNV();
        }
    }
    
    public void updateDuAnComboBox() {
         if (tabDuAn != null) {
            tabDuAn.updateDuAnComboBox();
        }
    }
    
    public void updatePhongBanComboBox() {
        if (tabNhanVien != null) {
            tabNhanVien.updatePhongBanComboBox();
        }
        if (tabPhongBan != null) {
            tabPhongBan.updatePhongBanComboBox();
        }
    }
    

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyNhanVienGUI().setVisible(true));
    }
}
