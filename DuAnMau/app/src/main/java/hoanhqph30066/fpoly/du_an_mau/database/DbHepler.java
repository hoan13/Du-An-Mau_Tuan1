package hoanhqph30066.fpoly.du_an_mau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHepler extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DB_NAME = "DATA";
    public DbHepler(Context context){
        super(context,DB_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Loại Sách
        String createTableLoaiSach = "CREATE TABLE LoaiSach(" +
                "MaLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenLoai TEXT NOT NULL UNIQUE" +
                ")";
        db.execSQL(createTableLoaiSach);

        // Tạo bảng Thành Viên
        String createTableThanhVien = "CREATE TABLE ThanhVien(" +
                "MaTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "HoTenTV TEXT NOT NULL UNIQUE, " +
                "NamSinhTV TEXT NOT NULL" +
                ")";
        db.execSQL(createTableThanhVien);

        // Tạo bảng Thủ Thư
        String createTableThuThu = "CREATE TABLE ThuThu(" +
                "MaTT TEXT PRIMARY KEY , " +
                "HoTenTT TEXT NOT NULL UNIQUE, " +
                "MatKhauTT TEXT NOT NULL," +
                "loaiTaiKhoan TEXT)";
        db.execSQL(createTableThuThu);

        // Tạo bảng Sách với khoá ngoại MaLoai tham chiếu đến bảng LoaiSach
        String createTableSach = "CREATE TABLE Sach(" +
                "MaSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenSach TEXT NOT NULL UNIQUE, " +
                "GiaThueSach INTEGER NOT NULL," +
                "TenLoai TEXT  REFERENCES LoaiSach(TenLoai) )";
        db.execSQL(createTableSach);

        // Tạo bảng Phiếu Mượn với các khoá ngoại MaTV, MaSach, MaTT tham chiếu đến bảng ThanhVien và ThuThu
        String createTablePhieuMuon = "CREATE TABLE PhieuMuon(" +
                "MaPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "HoTenTV INTEGER REFERENCES ThanhVien(HoTenTV), " +
                "TenSach INTEGER REFERENCES Sach(TenSach), " +
                "MaTT INTEGER REFERENCES ThuThu(MaTT)," +
                "Ngay TEXT NOT NULL," +
                "GiaThueSach INTEGER REFERENCES Sach(GiaThueSach) ," +
                "TrangThai TEXT NOT NULL )";
        db.execSQL(createTablePhieuMuon);

        db.execSQL("INSERT INTO ThuThu VALUES ('admin','Hoàng Quốc Hoàn','admin','Admin'),('thuthu1','Nguyễn Văn A','1234','thuthu')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LoaiSach");
        db.execSQL("DROP TABLE IF EXISTS ThanhVien");
        db.execSQL("DROP TABLE IF EXISTS ThuThu");
        db.execSQL("DROP TABLE IF EXISTS Sach");
        db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
        onCreate(db);
    }
}
