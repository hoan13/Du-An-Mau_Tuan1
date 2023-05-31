package hoanhqph30066.fpoly.du_an_mau.Model;

public class LoaiSach {
    private int MaLs;
    private String TenLs;

    public LoaiSach() {
    }

    public int getMaLs() {
        return MaLs;
    }

    public LoaiSach(String tenLs) {
        TenLs = tenLs;
    }

    public LoaiSach(int maLs, String tenLs) {
        MaLs = maLs;
        TenLs = tenLs;
    }

    public void setMaLs(int maLs) {
        MaLs = maLs;
    }

    public String getTenLs() {
        return TenLs;
    }

    public void setTenLs(String tenLs) {
        TenLs = tenLs;
    }
}
