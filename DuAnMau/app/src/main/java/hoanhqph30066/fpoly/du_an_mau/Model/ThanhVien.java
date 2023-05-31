package hoanhqph30066.fpoly.du_an_mau.Model;

public class ThanhVien {
    private int MaTv;
    private String TenTv;
    private String namsinh;

    public ThanhVien() {
    }

    public ThanhVien(int maTv, String tenTv, String namsinh) {
        MaTv = maTv;
        TenTv = tenTv;
        this.namsinh = namsinh;
    }

    public int getMaTv() {
        return MaTv;
    }

    public void setMaTv(int maTv) {
        MaTv = maTv;
    }

    public String getTenTv() {
        return TenTv;
    }

    public void setTenTv(String tenTv) {
        TenTv = tenTv;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }
}
