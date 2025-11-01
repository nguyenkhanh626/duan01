import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

/**
 * Lớp này là JPanel cho tab "Quản lý Lương" (Chỉ xem)
 */
public class TabLuong extends JPanel {

    // === Tham chiếu đến Controller chính và Dữ liệu ===
    private QuanLyNhanVienGUI parent;
    private List<NhanVien> danhSachNV;
    private NumberFormat currencyFormatter;

    // === Các thành phần UI của tab này ===
    private DefaultTableModel modelLuong;
    private JTable tableLuong;
    
    public TabLuong(QuanLyNhanVienGUI parent) {
        this.parent = parent;
        this.danhSachNV = parent.danhSachNV;
        this.currencyFormatter = parent.currencyFormatter; // Lấy bộ định dạng từ parent

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRefreshLuong = new JButton("Làm mới Bảng lương");
        btnRefreshLuong.addActionListener(e -> refreshLuongTable()); // Gọi hàm cục bộ
        topPanel.add(btnRefreshLuong);
        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {
            "Mã NV", "Họ Tên", "Lương (CB+TN)", 
            "Điểm thưởng DA", "Thưởng Dự án", 
            "Thưởng Chuyên cần", "Điểm Vi phạm", 
            "Tiền Phạt", "Lương Thực nhận"
        };
        modelLuong = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableLuong = new JTable(modelLuong);
        add(new JScrollPane(tableLuong), BorderLayout.CENTER);
    }

    /**
     * Tính toán và làm mới toàn bộ bảng lương (GỌI BỞI PARENT)
     */
    public void refreshLuongTable() {
        if (modelLuong == null) return;

        modelLuong.setRowCount(0);
        
        final long LUONG_CO_BAN = 15_000_000;
        final long THAM_NIEN_BONUS = 5_000_000;
        final long PHAT_VI_PHAM = 500_000;
        final long THUONG_DU_AN_MULTI = 2_000_000;
        final long THUONG_CHUYEN_CAN = 1_000_000;

        for (NhanVien nv : danhSachNV) {
            int soLanTangLuong = nv.getThamNien() / 3;
            long phuCapThamNien = (long) soLanTangLuong * THAM_NIEN_BONUS;
            long luongTruocTru = LUONG_CO_BAN + phuCapThamNien;

            int diemThuongDA = nv.getDiemThuongDuAn();
            long thuongDuAn = (long) diemThuongDA * THUONG_DU_AN_MULTI;

            long thuongChuyenCan = (nv.getDiemViPham() == 0) ? THUONG_CHUYEN_CAN : 0;
            int diemViPham = nv.getDiemViPham();
            long tienPhat = (long) diemViPham * PHAT_VI_PHAM;

            long luongCuoiCung = luongTruocTru + thuongDuAn + thuongChuyenCan - tienPhat;

            modelLuong.addRow(new Object[]{
                nv.getMaNhanVien(),
                nv.getHoTen(),
                currencyFormatter.format(luongTruocTru),
                diemThuongDA,
                currencyFormatter.format(thuongDuAn),
                currencyFormatter.format(thuongChuyenCan),
                diemViPham,
                currencyFormatter.format(tienPhat),
                currencyFormatter.format(luongCuoiCung)
            });
        }
    }
}