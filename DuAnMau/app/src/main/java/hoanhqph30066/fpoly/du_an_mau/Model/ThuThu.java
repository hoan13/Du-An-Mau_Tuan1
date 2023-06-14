package hoanhqph30066.fpoly.du_an_mau.Model;

public class ThuThu {
    private String MaTt;
    private String TenTt;
    private String MK;

    private String LoaiTaiKhoan;

    public ThuThu() {
    }

    public ThuThu(String maTt, String tenTt, String MK, String loaiTaiKhoan) {
        MaTt = maTt;
        TenTt = tenTt;
        this.MK = MK;
        LoaiTaiKhoan = loaiTaiKhoan;
    }

    public ThuThu(String maTt, String loaiTaiKhoan) {
        MaTt = maTt;
        LoaiTaiKhoan = loaiTaiKhoan;
    }

    public ThuThu(String maTt) {
        MaTt = maTt;
    }

    public String getMaTt() {
        return MaTt;
    }

    public void setMaTt(String maTt) {
        MaTt = maTt;
    }

    public String getTenTt() {
        return TenTt;
    }

    public void setTenTt(String tenTt) {
        TenTt = tenTt;
    }

    public String getMK() {
        return MK;
    }

    public void setMK(String MK) {
        this.MK = MK;
    }

    public String getLoaiTaiKhoan() {
        return LoaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        LoaiTaiKhoan = loaiTaiKhoan;
    }
}
