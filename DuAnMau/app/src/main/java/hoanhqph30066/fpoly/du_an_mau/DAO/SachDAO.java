package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class SachDAO {
    DbHepler dbHepler;
    public SachDAO(Context context){
        dbHepler = new DbHepler(context);
    }

    public ArrayList<Sach> getDSSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHepler.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT SC.MaSach, SC.TenSach, SC.GiaThueSach, SC.MaLoai, LS.TenLoai FROM Sach SC, LoaiSach LS WHERE SC.MaLoai = LS.MaLoai", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(cursor.getColumnIndexOrThrow("MaSach")));
                sach.setMaLoaiSach(cursor.getInt(cursor.getColumnIndexOrThrow("MaLoai")));
                sach.setGiaSach(cursor.getInt(cursor.getColumnIndexOrThrow("GiaThueSach")));
                sach.setTenSach(cursor.getString(cursor.getColumnIndexOrThrow("TenSach")));
                sach.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("TenLoai")));
                list.add(sach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean ThemSach(String tenSach, int giaThue, int maLoai) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenSach", tenSach);
        values.put("GiaThueSach", giaThue);
        values.put("MaLoai", maLoai);
        long check = db.insert("Sach", null, values);
        return check != -1;
    }

    public boolean SuaSach(int maSach, String tenSach, int giaThue, int maLoai) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenSach", tenSach);
        values.put("GiaThueSach", giaThue);
        values.put("MaLoai", maLoai);
        int check = db.update("Sach", values, "MaSach =?", new String[]{String.valueOf(maSach)});
        return check != -1;
    }

    public int XoaSach(int maSach) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuMuon WHERE MaSach =?", new String[]{String.valueOf(maSach)});
        if (cursor.getCount() != 0) {
            return -1; // Không được xoá vì sách có trong phiếu mượn
        }
        int check = db.delete("Sach", "MaSach =?", new String[]{String.valueOf(maSach)});
        if (check == -1)
            return 0; // xoá thất bại
        return 1; // xoá thành công
    }

}
