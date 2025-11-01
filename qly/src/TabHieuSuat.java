import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Lớp này là JPanel cho tab "Quản lý Hiệu suất"
 * PHIÊN BẢN CẬP NHẬT: Gọi refreshBaoCaoTab()
 */
public class TabHieuSuat extends JPanel {

    // === Tham chiếu đến Controller chính và Dữ liệu ===
    private QuanLyNhanVienGUI parent;
    private List<NhanVien> danhSachNV;

    // === Các thành phần UI của tab này ===
    private JPanel cardPanelHieuSuat;
    private CardLayout cardLayoutHieuSuat;
    private JTextField txtMaNVDiemDanh;
    private JRadioButton radioDiMuon;
    private JRadioButton radioVangMat;
    private ButtonGroup groupDiemDanh;
    private DefaultTableModel modelViPham;
    private JTable tableViPham;
    
    public TabHieuSuat(QuanLyNhanVienGUI parent) {
        this.parent = parent;
        this.danhSachNV = parent.danhSachNV;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Panel trên cùng chứa ComboBox
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Chọn chức năng:"));
        String[] modes = {"Điểm danh", "Đánh giá KPI"};
        JComboBox<String> modeSelector = new JComboBox<>(modes);
        
        add(topPanel, BorderLayout.NORTH);

        // 2. Panel trung tâm chứa các "thẻ"
        cardLayoutHieuSuat = new CardLayout();
        cardPanelHieuSuat = new JPanel(cardLayoutHieuSuat);

        // 3. Tạo các thẻ (panel) con
        JPanel diemDanhPanel = createDiemDanhPanel(); // Tạo thẻ Điểm danh
        JPanel kpiPanel = createPlaceholderPanel("Chức năng Đánh giá KPI sẽ được xây dựng ở đây");
        
        // 4. Thêm các thẻ vào CardLayout
        cardPanelHieuSuat.add(diemDanhPanel, "Điểm danh");
        cardPanelHieuSuat.add(kpiPanel, "Đánh giá KPI");

        add(cardPanelHieuSuat, BorderLayout.CENTER);

        // 5. Thêm sự kiện cho ComboBox để chuyển thẻ
        modeSelector.addActionListener(e -> {
            String selectedMode = (String) modeSelector.getSelectedItem();
            cardLayoutHieuSuat.show(cardPanelHieuSuat, selectedMode);
        });
    }
    
    /**
     * Tạo panel con cho chức năng Điểm danh
     */
    private JPanel createDiemDanhPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Form
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nhập Mã nhân viên:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtMaNVDiemDanh = new JTextField(15);
        panel.add(txtMaNVDiemDanh, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Lỗi vi phạm:"), gbc);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radioDiMuon = new JRadioButton("Đi muộn (+1 điểm)");
        radioVangMat = new JRadioButton("Vắng mặt (+2 điểm)");
        radioDiMuon.setSelected(true);
        groupDiemDanh = new ButtonGroup();
        groupDiemDanh.add(radioDiMuon);
        groupDiemDanh.add(radioVangMat);
        radioPanel.add(radioDiMuon);
        radioPanel.add(radioVangMat);
        gbc.gridx = 1;
        panel.add(radioPanel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.EAST;
        JButton btnGhiNhanDiemDanh = new JButton("Ghi nhận");
        btnGhiNhanDiemDanh.addActionListener(e -> xuLyGhiNhanDiemDanh());
        panel.add(btnGhiNhanDiemDanh, gbc);
        
        // Bảng log
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0; 
        
        String[] columnNames = {"Mã NV", "Họ Tên", "Lỗi vi phạm"};
        modelViPham = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableViPham = new JTable(modelViPham);
        
        JScrollPane scrollPane = new JScrollPane(tableViPham);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Vi phạm Đã ghi nhận (trong phiên)"));
        panel.add(scrollPane, gbc);

        return panel;
    }
    
    /**
     * Xử lý logic khi nhấn nút "Ghi nhận" điểm danh
     */
    private void xuLyGhiNhanDiemDanh() {
        String maNV = txtMaNVDiemDanh.getText().trim();
        if (maNV.isEmpty()) { /* ... báo lỗi ... */ return; }

        NhanVien nvFound = null;
        for (NhanVien nv : danhSachNV) {
            if (nv.getMaNhanVien().equals(maNV)) {
                nvFound = nv;
                break;
            }
        }
        if (nvFound == null) { /* ... báo lỗi ... */ return; }

        int pointsToAdd = 0;
        String violationType = "";
        if (radioDiMuon.isSelected()) {
            pointsToAdd = 1;
            violationType = "Đi muộn";
        } else if (radioVangMat.isSelected()) {
            pointsToAdd = 2;
            violationType = "Vắng mặt";
        }

        nvFound.addDiemViPham(pointsToAdd);

        JOptionPane.showMessageDialog(this,
                "Đã ghi nhận " + violationType + " cho nhân viên " + nvFound.getHoTen() + ".\n"
                + "Tổng điểm vi phạm mới: " + nvFound.getDiemViPham(),
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
                
        modelViPham.addRow(new Object[]{
            nvFound.getMaNhanVien(),
            nvFound.getHoTen(),
            violationType
        });

        txtMaNVDiemDanh.setText("");
        radioDiMuon.setSelected(true);
        
        // === GỌI HÀM REFRESH CỦA PARENT ===
        parent.refreshLuongTable();
        parent.refreshBaoCaoTab(); // MỚI
    }

    // Hàm tiện ích tạo placeholder
    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.ITALIC, 18));
        panel.add(label);
        return panel;
    }
}