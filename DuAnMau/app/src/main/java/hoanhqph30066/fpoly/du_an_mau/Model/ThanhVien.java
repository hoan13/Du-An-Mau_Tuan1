package hoanhqph30066.fpoly.du_an_mau.Model;

public class ThanhVien {
    private int MaTv;
    private String TenTv;
    private String Cccd;

    public ThanhVien() {
    }

    public ThanhVien(int maTv, String tenTv, String cccd) {
        MaTv = maTv;
        TenTv = tenTv;
        Cccd = cccd;
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

    public String getCccd() {
        return Cccd;
    }

    public void setCccd(String cccd) {
        Cccd = cccd;
    }
}
