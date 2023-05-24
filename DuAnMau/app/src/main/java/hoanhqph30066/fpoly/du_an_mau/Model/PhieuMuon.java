package hoanhqph30066.fpoly.du_an_mau.Model;

public class PhieuMuon {
    private int MaPm;
    private String NgayThue;
    private String TrangThai;
    private String TenTv;
    private String TenS;
    private int GiaThue;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPm, String ngayThue, String trangThai, String tenTv, String tenS, int giaThue) {
        MaPm = maPm;
        NgayThue = ngayThue;
        TrangThai = trangThai;
        TenTv = tenTv;
        TenS = tenS;
        GiaThue = giaThue;
    }

    public PhieuMuon(String ngayThue, String trangThai, String tenTv, String tenS, int giaThue) {
        NgayThue = ngayThue;
        TrangThai = trangThai;
        TenTv = tenTv;
        TenS = tenS;
        GiaThue = giaThue;
    }

    public int getMaPm() {
        return MaPm;
    }

    public void setMaPm(int maPm) {
        MaPm = maPm;
    }

    public String getNgayThue() {
        return NgayThue;
    }

    public void setNgayThue(String ngayThue) {
        NgayThue = ngayThue;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getTenTv() {
        return TenTv;
    }

    public void setTenTv(String tenTv) {
        TenTv = tenTv;
    }

    public String getTenS() {
        return TenS;
    }

    public void setTenS(String tenS) {
        TenS = tenS;
    }

    public int getGiaThue() {
        return GiaThue;
    }

    public void setGiaThue(int giaThue) {
        GiaThue = giaThue;
    }
}
