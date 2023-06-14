package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Model.PhieuMuon;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class PhieuMuonDAO {
    DbHepler dbHepler;

    public PhieuMuonDAO(Context context) {
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
                phieuMuon.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow("TrangThai")));
                phieuMuon.setTienThue(cursor.getInt(cursor.getColumnIndexOrThrow("TienThue")));
                list.add(phieuMuon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public long ThemPhieuMuon(PhieuMuon obj) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTV", obj.getMaThanhVien());
        values.put("MaTT", obj.getMaThuThu());
        values.put("MaSach", obj.getMaSach());
        values.put("Ngay", obj.getNgay());
        values.put("TenSach", obj.getTenSach());
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
        values.put("TenSach", obj.getTenSach());
        values.put("TienThue", obj.getTienThue());
        values.put("TrangThai", obj.getTrangThai());

        return db.update("PhieuMuon", values, "MaPM", new String[]{String.valueOf(obj.getMaPhieuMuon())});
    }

    public long XoaPhieuMuon(int mapm) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        return db.delete("PhieuMuon", "MaPM = ?", new String[]{String.valueOf(mapm)});
    }

    public long ThemPhieuMuonSC(PhieuMuon obj) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE MaTT =? AND loaiTaiKhoan IN('Admin','thuthu')", new String[]{obj.getMaThuThu()});
        if (cursor.moveToFirst()) {
            values.put("HoTenTV", obj.getTenThanhVien());
            values.put("MaTT", obj.getMaThuThu());
            values.put("Ngay", obj.getNgay());
            values.put("TenSach", obj.getTenSach());
            values.put("GiaThueSach", obj.getTienThue());
            values.put("TrangThai", obj.getTrangThai());

            long reult = db.insert("PhieuMuon", null, values);
            cursor.close();
            return reult;
        } else {
            cursor.close();
            return -1;
        }

    }

    public long SuaPhieuMuonSCNC(PhieuMuon obj) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE MaTT =? AND loaiTaiKhoan IN('Admin','thuthu')", new String[]{obj.getMaThuThu()});
       if(cursor.getCount()==0){
           return 0;
       }
        if (cursor.moveToFirst()) {
            values.put("HoTenTV", obj.getTenThanhVien());
            values.put("MaTT", obj.getMaThuThu());
            values.put("Ngay", obj.getNgay());
            values.put("TenSach", obj.getTenSach());
            values.put("GiaThueSach", obj.getTienThue());
            values.put("TrangThai", obj.getTrangThai());

            long reult = db.update("PhieuMuon",values,"MaPM =?", new String[]{String.valueOf(obj.getMaPhieuMuon())});
            cursor.close();
            return reult;
        } else {
            cursor.close();
            return -1;
        }

    }
    // đc thêm phiếu mượn nếu là admin và thuthu
    public long ThemPhieuMuonSCNC(PhieuMuon obj) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE MaTT =? AND loaiTaiKhoan IN('Admin','thuthu')", new String[]{obj.getMaThuThu()});
        if(cursor.getCount()==0){
            return 0;
        }
        if (cursor.moveToFirst()) {
            values.put("HoTenTV", obj.getTenThanhVien());
            values.put("MaTT", obj.getMaThuThu());
            values.put("Ngay", obj.getNgay());
            values.put("TenSach", obj.getTenSach());
            values.put("GiaThueSach", obj.getTienThue());
            values.put("TrangThai", obj.getTrangThai());

            long reult = db.insert("PhieuMuon", null, values);
            cursor.close();
            return reult;
        } else {
            cursor.close();
            return -1;
        }

    }

    public ArrayList<PhieuMuon> getAllData() {
        String sql = "SELECT * FROM PhieuMuon";
        return getDataPhieuMuon(sql);
    }

    @SuppressLint("Range")
    public ArrayList<PhieuMuon> getDataPhieuMuon(String sql, String... SelectAvg){
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ArrayList<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuMuon", SelectAvg);
        while (cursor.moveToNext()){
            PhieuMuon pm = new PhieuMuon();
            pm.setMaPhieuMuon(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaPM"))));
            pm.setTenThanhVien(cursor.getString(cursor.getColumnIndex("HoTenTV")));
            pm.setMaThuThu(cursor.getString(cursor.getColumnIndex("MaTT")));
            pm.setTenSach(cursor.getString(cursor.getColumnIndex("TenSach")));
            pm.setNgay(cursor.getString(cursor.getColumnIndex("Ngay")));
            pm.setTienThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("GiaThueSach"))));
            pm.setTrangThai((cursor.getString(cursor.getColumnIndex("TrangThai"))));
            list.add(pm);
        }
        return list;
    }
}
