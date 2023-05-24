package hoanhqph30066.fpoly.du_an_mau.Model;

public class Sach {
    private int MaSach;
    private String TenSach;
    private int GiaSach;
    private String TenLs;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, int giaSach, String tenLs) {
        MaSach = maSach;
        TenSach = tenSach;
        GiaSach = giaSach;
        TenLs = tenLs;
    }

    public Sach(String tenSach, int giaSach, String tenLs) {
        TenSach = tenSach;
        GiaSach = giaSach;
        TenLs = tenLs;
    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int maSach) {
        MaSach = maSach;
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

    public String getTenLs() {
        return TenLs;
    }

    public void setTenLs(String tenLs) {
        TenLs = tenLs;
    }
}
