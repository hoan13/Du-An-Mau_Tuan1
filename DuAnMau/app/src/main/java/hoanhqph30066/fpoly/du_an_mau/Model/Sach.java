package hoanhqph30066.fpoly.du_an_mau.Model;

public class Sach {
    private int MaSach;
    private int MaLoaiSach;
    private String TenSach;
    private int GiaSach;
    private int SoluongSachMuon;
    private String TenLoai;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, int soluongSachMuon) {
        MaSach = maSach;
        TenSach = tenSach;
        SoluongSachMuon = soluongSachMuon;
    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int maSach) {
        MaSach = maSach;
    }

    public int getMaLoaiSach() {
        return MaLoaiSach;
    }

    public void setMaLoaiSach(int maLoaiSach) {
        MaLoaiSach = maLoaiSach;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public int getGiaSach() {
        return GiaSach;
    }

    public void setGiaSach(int giaSach) {
        GiaSach = giaSach;
    }

    public int getSoluongSachMuon() {
        return SoluongSachMuon;
    }

    public void setSoluongSachMuon(int soluongSachMuon) {
        SoluongSachMuon = soluongSachMuon;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }
}
