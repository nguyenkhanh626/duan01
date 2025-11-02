import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class TabNhanVien extends JPanel {


    private QuanLyNhanVienGUI parent;
    private List<NhanVien> danhSachNV;
    private List<PhongBan> danhSachPB;


    private JComboBox<PhongBan> cmbPhongBanNV;
    private DefaultTableModel modelNV;
    private JTable tableNV;
    private JTextField txtMaNV, txtTenNV, txtSdt, txtEmail, txtNgaySinh, txtCccd, txtThamNien;


    public TabNhanVien(QuanLyNhanVienGUI parent) {
        this.parent = parent;
        this.danhSachNV = parent.danhSachNV; 
        this.danhSachPB = parent.danhSachPB; 


        setLayout(new BorderLayout(10, 10));
        

        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        JPanel formPanel = new JPanel(new GridLayout(0, 4, 10, 5));
        
        formPanel.add(new JLabel("Mã NV:"));
        txtMaNV = new JTextField();
        formPanel.add(txtMaNV);
        
        formPanel.add(new JLabel("Tên NV:"));
        txtTenNV = new JTextField();
        formPanel.add(txtTenNV);
        
        formPanel.add(new JLabel("Phòng ban:"));
        cmbPhongBanNV = new JComboBox<>();
        formPanel.add(cmbPhongBanNV);
        
        formPanel.add(new JLabel("SĐT:"));
        txtSdt = new JTextField();
        formPanel.add(txtSdt);
        
        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);
        
        formPanel.add(new JLabel("Ngày sinh (dd/mm/yyyy):"));
        txtNgaySinh = new JTextField();
        formPanel.add(txtNgaySinh);
        
        formPanel.add(new JLabel("CCCD:"));
        txtCccd = new JTextField();
        formPanel.add(txtCccd);
        
        formPanel.add(new JLabel("Thâm niên (năm):"));
        txtThamNien = new JTextField();
        formPanel.add(txtThamNien);
        
        topPanel.add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnThemNV = new JButton("Thêm");
        JButton btnSuaNV = new JButton("Sửa");
        JButton btnXoaNV = new JButton("Xóa");
        JButton btnLamMoiNV = new JButton("Làm mới");


        btnThemNV.addActionListener(e -> themNhanVien());
        btnSuaNV.addActionListener(e -> suaNhanVien());
        btnXoaNV.addActionListener(e -> xoaNhanVien());
        btnLamMoiNV.addActionListener(e -> lamMoiFormNV());

        buttonPanel.add(btnThemNV);
        buttonPanel.add(btnSuaNV);
        buttonPanel.add(btnXoaNV);
        buttonPanel.add(btnLamMoiNV);
        
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã NV", "Họ Tên", "Phòng ban", "SĐT", "Email", "Ngày sinh", "CCCD", "Thâm niên (năm)"};
        modelNV = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableNV = new JTable(modelNV);
        

        tableNV.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiThongTinLenFormNV();
            }
        });
        
        add(new JScrollPane(tableNV), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    

    
    private void hienThiThongTinLenFormNV() {
        int r = tableNV.getSelectedRow();
        if (r == -1) return;
        txtMaNV.setText(modelNV.getValueAt(r, 0).toString());
        txtTenNV.setText(modelNV.getValueAt(r, 1).toString());
        String tenPhongBan = modelNV.getValueAt(r, 2).toString();
        txtSdt.setText(modelNV.getValueAt(r, 3).toString());
        txtEmail.setText(modelNV.getValueAt(r, 4).toString());
        txtNgaySinh.setText(modelNV.getValueAt(r, 5).toString());
        txtCccd.setText(modelNV.getValueAt(r, 6).toString());
        txtThamNien.setText(String.valueOf(modelNV.getValueAt(r, 7)));
        for (int i = 0; i < cmbPhongBanNV.getItemCount(); i++) {
            if (cmbPhongBanNV.getItemAt(i).getTenPhongBan().equals(tenPhongBan)) {
                cmbPhongBanNV.setSelectedIndex(i);
                break;
            }
        }
        txtMaNV.setEditable(false);
    }

    private void themNhanVien() {
        // ... (code kiểm tra dữ liệu) ...
        String maNV = txtMaNV.getText();
        String tenNV = txtTenNV.getText();
        PhongBan pb = (PhongBan) cmbPhongBanNV.getSelectedItem();
        String sdt = txtSdt.getText();
        String email = txtEmail.getText();
        String ngaySinh = txtNgaySinh.getText();
        String cccd = txtCccd.getText();
        String thamNienStr = txtThamNien.getText();
        if (maNV.isEmpty() || tenNV.isEmpty() || pb == null) {
            JOptionPane.showMessageDialog(this, "Mã NV, Tên NV và Phòng ban là bắt buộc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (danhSachNV.stream().anyMatch(nv -> nv.getMaNhanVien().equals(maNV))) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int thamNienInt = 0;
        if (!thamNienStr.isEmpty()) {
            try {
                thamNienInt = Integer.parseInt(thamNienStr);
                if (thamNienInt < 0) {
                    JOptionPane.showMessageDialog(this, "Thâm niên phải là một số không âm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Thâm niên phải là một con số (ví dụ: 5).", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        NhanVien nv = new NhanVien(maNV, tenNV, pb.getTenPhongBan(), sdt, email, ngaySinh, cccd, thamNienInt);
        danhSachNV.add(nv);
        
        modelNV.addRow(new Object[]{
                nv.getMaNhanVien(), nv.getHoTen(), nv.getPhongBan(),
                nv.getSdt(), nv.getEmail(), nv.getNgaySinh(),
                nv.getCccd(), nv.getThamNien()
        });
        
        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
        lamMoiFormNV();
        

        parent.locNhanVienTheoPhongBan();
        parent.refreshLuongTable();
        parent.refreshBaoCaoTab();
    }

    private void suaNhanVien() {
        int r = tableNV.getSelectedRow();
        if (r == -1) { /* ... báo lỗi ... */ return; }
        
        // ... (code lấy dữ liệu từ form) ...
        String maNV = txtMaNV.getText();
        String tenMoi = txtTenNV.getText();
        PhongBan pbMoi = (PhongBan) cmbPhongBanNV.getSelectedItem();
        String sdtMoi = txtSdt.getText();
        String emailMoi = txtEmail.getText();
        String ngaySinhMoi = txtNgaySinh.getText();
        String cccdMoi = txtCccd.getText();
        String thamNienMoiStr = txtThamNien.getText();
        if (tenMoi.isEmpty() || pbMoi == null) { /* ... báo lỗi ... */ return; }
        int validatedThamNien = 0;
        if (!thamNienMoiStr.isEmpty()) {
            try {
                validatedThamNien = Integer.parseInt(thamNienMoiStr);
                if (validatedThamNien < 0) { /* ... báo lỗi ... */ return; }
            } catch (NumberFormatException e) { /* ... báo lỗi ... */ return; }
        }
        final int thamNienMoiInt = validatedThamNien;
        

        modelNV.setValueAt(tenMoi, r, 1);
        modelNV.setValueAt(pbMoi.getTenPhongBan(), r, 2);
        modelNV.setValueAt(sdtMoi, r, 3);
        modelNV.setValueAt(emailMoi, r, 4);
        modelNV.setValueAt(ngaySinhMoi, r, 5);
        modelNV.setValueAt(cccdMoi, r, 6);
        modelNV.setValueAt(thamNienMoiInt, r, 7);

        for(NhanVien nv : danhSachNV) {
            if(nv.getMaNhanVien().equals(maNV)) {
                nv.setHoTen(tenMoi);
                nv.setPhongBan(pbMoi.getTenPhongBan());
                nv.setSdt(sdtMoi);
                nv.setEmail(emailMoi);
                nv.setNgaySinh(ngaySinhMoi);
                nv.setCccd(cccdMoi);
                nv.setThamNien(thamNienMoiInt);
                break;
            }
        }

        JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");
        lamMoiFormNV();
        

        parent.locNhanVienTheoPhongBan();
        parent.refreshLuongTable();
        parent.refreshBaoCaoTab();
    }

    private void xoaNhanVien() {
        int r = tableNV.getSelectedRow();
        if (r == -1) { /* ... báo lỗi ... */ return; }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String maNV = modelNV.getValueAt(r, 0).toString();
            danhSachNV.removeIf(nv -> nv.getMaNhanVien().equals(maNV));
            modelNV.removeRow(r);
            
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
            lamMoiFormNV();
            

            parent.locNhanVienTheoPhongBan();
            parent.refreshLuongTable();
            parent.refreshBaoCaoTab();
        }
    }

    private void lamMoiFormNV() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        if(cmbPhongBanNV.getItemCount() > 0) cmbPhongBanNV.setSelectedIndex(0);
        txtSdt.setText("");
        txtEmail.setText("");
        txtNgaySinh.setText("");
        txtCccd.setText("");
        txtThamNien.setText("");
        txtMaNV.setEditable(true);
        tableNV.clearSelection();
    }
    

    
    public void refreshTableNV() {
        modelNV.setRowCount(0);
        for (NhanVien nv : danhSachNV) {
            modelNV.addRow(new Object[]{
                nv.getMaNhanVien(), nv.getHoTen(), nv.getPhongBan(),
                nv.getSdt(), nv.getEmail(), nv.getNgaySinh(),
                nv.getCccd(), nv.getThamNien()
            });
        }
    }
    
    public void updatePhongBanComboBox() {
        cmbPhongBanNV.removeAllItems();
        for (PhongBan pb : danhSachPB) {
            cmbPhongBanNV.addItem(pb);
        }
    }
}
