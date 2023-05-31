package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Model.PhieuMuon;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class PhieuMuonDAO {
    DbHepler dbHepler;
    public PhieuMuonDAO(Context context){
        dbHepler = new DbHepler(context);
    }

    public ArrayList<PhieuMuon> getDanhSachPhieuMuon() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dbHepler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT PM.MaPM, PM.MaTV, TV.HoTenTV, PM.MaTT, TT.HoTenTT, PM.MaSach, SC.TenSach, PM.Ngay, PM.TraSach, PM.TienThue" +
                " FROM PhieuMuon PM, ThanhVien TV, ThuThu TT, Sach SC " +
                "WHERE PM.MaTV = TV.MaTV AND PM.MaTT = TT.MaTT AND PM.MaSach = SC.MaSach ORDER BY PM.MaPM DESC", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaPhieuMuon(cursor.getInt(cursor.getColumnIndexOrThrow("MaPM")));
                phieuMuon.setMaThanhVien(cursor.getInt(cursor.getColumnIndexOrThrow("MaTV")));
                phieuMuon.setTenThanhVien(cursor.getString(cursor.getColumnIndexOrThrow("HoTenTV")));
                phieuMuon.setMaThuThu(cursor.getString(cursor.getColumnIndexOrThrow("MaTT")));
                phieuMuon.setTenThuThu(cursor.getString(cursor.getColumnIndexOrThrow("HoTenTT")));
                phieuMuon.setMaSach(cursor.getInt(cursor.getColumnIndexOrThrow("MaSach")));
                phieuMuon.setTenSach(cursor.getString(cursor.getColumnIndexOrThrow("TenSach")));
                phieuMuon.setNgay(cursor.getString(cursor.getColumnIndexOrThrow("Ngay")));
                phieuMuon.setTrangThai(cursor.getInt(cursor.getColumnIndexOrThrow("TrangThai")));
                phieuMuon.setTienThue(cursor.getInt(cursor.getColumnIndexOrThrow("TienThue")));
                list.add(phieuMuon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean thayDoiTrangThai(int MaPM) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TrangThai", 1);
        int check = db.update("PhieuMuon", values, "MaPM =?", new String[]{String.valueOf(MaPM)});
        return check != -1;
    }

    public long ThemPhieuMuon(PhieuMuon obj) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTV", obj.getMaThanhVien());
        values.put("MaTT", obj.getMaThuThu());
        values.put("MaSach", obj.getMaSach());
        values.put("Ngay", obj.getNgay());
        values.put("TenSach",obj.getTenSach());
        values.put("TienThue", obj.getTienThue());
        values.put("TrangThai", obj.getTrangThai());

        return db.insert("PhieuMuon", null, values);
    }

    public long SuaPhieuMuon(PhieuMuon obj) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTV", obj.getMaThanhVien());
        values.put("MaTT", obj.getMaThuThu());
        values.put("MaSach", obj.getMaSach());
        values.put("Ngay", obj.getNgay());
        values.put("TenSach",obj.getTenSach());
        values.put("TienThue", obj.getTienThue());
        values.put("TrangThai", obj.getTrangThai());

        return db.update("PhieuMuon",values, "MaPM", new String[]{String.valueOf(obj.getMaPhieuMuon())});
    }

    public long XoaPhieuMuon(int  mapm) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        return db.delete("PhieuMuon", "MaPM = ?", new String[]{String.valueOf(mapm)});
    }
}
