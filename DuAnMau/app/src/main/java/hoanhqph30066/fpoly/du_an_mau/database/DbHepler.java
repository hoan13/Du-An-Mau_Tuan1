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
        String createTableLoaiSach = "CREATE TABLE IF NOT EXISTS LoaiSach(" +
                "MaLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenLoai TEXT NOT NULL" +
                ")";
        db.execSQL(createTableLoaiSach);

        // Tạo bảng Thành Viên
        String createTableThanhVien = "CREATE TABLE IF NOT EXISTS ThanhVien(" +
                "MaTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenTV TEXT NOT NULL, " +
                "CccdTV TEXT NOT NULL" +
                ")";
        db.execSQL(createTableThanhVien);

        // Tạo bảng Thủ Thư
        String createTableThuThu = "CREATE TABLE IF NOT EXISTS ThuThu(" +
                "MaTT INTERGER PRIMARY KEY AUTOINCREMENT, " +
                "TenTT TEXT NOT NULL, " +
                "MatKhauTT TEXT NOT NULL" +
                ")";
        db.execSQL(createTableThuThu);

        // Tạo bảng Sách với khoá ngoại MaLoai tham chiếu đến bảng LoaiSach
        String createTableSach = "CREATE TABLE IF NOT EXISTS Sach(" +
                "MaSach INTERGER PRIMARY KEY AUTOINCREMENT, " +
                "MaLoai INTERGER NOT NULL, " +
                "TenSach TEXT NOT NULL, " +
                "GiaThueSach TEXT NOT NULL, " +
                "CONSTRAINT FK_MaLoai FOREIGN KEY (MaLoai) REFERENCES LoaiSach (MaLoai)" +
                ")";
        db.execSQL(createTableSach);

        // Tạo bảng Phiếu Mượn với các khoá ngoại MaTV, MaSach, MaTT tham chiếu đến bảng ThanhVien và ThuThu
        String createTablePhieuMuon = "CREATE TABLE IF NOT EXISTS PhieuMuon(" +
                "MaPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MaTV INTEGER NOT NULL, MaSach INTEGER NOT NULL, " +
                "MaTT INTEGER NOT NULL, " +
                "CONSTRAINT FK_MaSach FOREIGN KEY (MaSach) REFERENCES Sach (MaSach), " +
                "CONSTRAINT FK_MaTT FOREIGN KEY (MaTT) REFERENCES ThuThu (MaTT), " +
                "CONSTRAINT FK_MaTV FOREIGN KEY (MaTV) REFERENCES ThanhVien (MaTV)" +
                ")";
        db.execSQL(createTablePhieuMuon);
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
