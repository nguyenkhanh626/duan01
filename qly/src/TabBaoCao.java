import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lớp này là JPanel cho tab "Báo cáo & Thống kê" (MỚI)
 */
public class TabBaoCao extends JPanel {

    // === Tham chiếu đến Controller chính và Dữ liệu ===
    //private QuanLyNhanVienGUI parent;
    private List<NhanVien> danhSachNV;

    // === Các thành phần UI của tab này ===
    private DefaultTableModel modelTopThuong;
    private JTable tableTopThuong;
    private DefaultTableModel modelTopPhat;
    private JTable tableTopPhat;

    public TabBaoCao(QuanLyNhanVienGUI parent) {
        //this.parent = parent;
        this.danhSachNV = parent.danhSachNV;

        // Sử dụng JSplitPane để chia đôi màn hình
        setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5); // Chia 50/50

        // === Panel Top Thưởng (Bên trái) ===
        JPanel panelThuong = new JPanel(new BorderLayout(5, 5));
        panelThuong.setBorder(BorderFactory.createTitledBorder("Top 5 Nhân viên - Điểm thưởng Dự án cao nhất"));

        String[] columnsThuong = {"Hạng", "Mã NV", "Họ Tên", "Điểm thưởng DA"};
        modelTopThuong = new DefaultTableModel(columnsThuong, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableTopThuong = new JTable(modelTopThuong);
        panelThuong.add(new JScrollPane(tableTopThuong), BorderLayout.CENTER);

        // === Panel Top Phạt (Bên phải) ===
        JPanel panelPhat = new JPanel(new BorderLayout(5, 5));
        panelPhat.setBorder(BorderFactory.createTitledBorder("Top 5 Nhân viên - Điểm vi phạm cao nhất"));

        String[] columnsPhat = {"Hạng", "Mã NV", "Họ Tên", "Điểm vi phạm"};
        modelTopPhat = new DefaultTableModel(columnsPhat, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableTopPhat = new JTable(modelTopPhat);
        panelPhat.add(new JScrollPane(tableTopPhat), BorderLayout.CENTER);

        // Thêm 2 panel vào JSplitPane
        splitPane.setLeftComponent(panelThuong);
        splitPane.setRightComponent(panelPhat);

        add(splitPane, BorderLayout.CENTER);
        
        // Nút Refresh
        JButton btnRefresh = new JButton("Làm mới Thống kê");
        btnRefresh.addActionListener(e -> refreshBaoCao());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btnRefresh);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Tính toán và làm mới cả hai bảng thống kê (GỌI BỞI PARENT)
     */
    public void refreshBaoCao() {
        if (modelTopThuong == null || modelTopPhat == null) return;

        // 1. Làm mới Top Thưởng
        modelTopThuong.setRowCount(0);
        List<NhanVien> topThuong = danhSachNV.stream()
                .filter(nv -> nv.getDiemThuongDuAn() > 0) // Chỉ lấy người có điểm
                .sorted(Comparator.comparingInt(NhanVien::getDiemThuongDuAn).reversed()) // Sắp xếp giảm dần
                .limit(5) // Chỉ lấy 5 người
                .collect(Collectors.toList());

        int hangThuong = 1;
        for (NhanVien nv : topThuong) {
            modelTopThuong.addRow(new Object[]{
                hangThuong++,
                nv.getMaNhanVien(),
                nv.getHoTen(),
                nv.getDiemThuongDuAn()
            });
        }

        // 2. Làm mới Top Phạt
        modelTopPhat.setRowCount(0);
        List<NhanVien> topPhat = danhSachNV.stream()
                .filter(nv -> nv.getDiemViPham() > 0) // Chỉ lấy người có điểm
                .sorted(Comparator.comparingInt(NhanVien::getDiemViPham).reversed()) // Sắp xếp giảm dần
                .limit(5) // Chỉ lấy 5 người
                .collect(Collectors.toList());

        int hangPhat = 1;
        for (NhanVien nv : topPhat) {
            modelTopPhat.addRow(new Object[]{
                hangPhat++,
                nv.getMaNhanVien(),
                nv.getHoTen(),
                nv.getDiemViPham()
            });
        }
    }
}