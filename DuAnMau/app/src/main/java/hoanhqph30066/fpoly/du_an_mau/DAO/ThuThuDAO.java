package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Model.ThuThu;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class ThuThuDAO {
    DbHepler dbHelper;
    SharedPreferences sharedPreferences;
    ThuThu thuThu;
    public ThuThuDAO(Context context){
        dbHelper = new DbHepler(context);
        sharedPreferences =context.getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
    }

    public ThuThuDAO(Context context, ThuThu thuThu) {
        dbHelper = new DbHepler(context);
        this.thuThu = thuThu;
    }

    public boolean checkLogin(String userName, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE MaTT =? AND MatKhauTT =?", new String[]{userName, password});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("MaTT", cursor.getString(cursor.getColumnIndexOrThrow("MaTT")));
            editor.putString("loaiTaiKhoan", cursor.getString(cursor.getColumnIndexOrThrow("loaiTaiKhoan")));
            editor.putString("MatKhauTT",cursor.getString(cursor.getColumnIndexOrThrow("MatKhauTT")));
            editor.putString("HoTenTT", cursor.getString(cursor.getColumnIndexOrThrow("HoTenTT")));
            editor.apply();
            return true;
        } else {
            return false;
        }
    }

    public boolean doiMatKhau(String userName, String oldPass, String newPass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE MaTT =? AND MatKhauTT =?", new String[]{userName, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("MatKhauTT", newPass);
            int check = db.update("ThuThu", values, "MaTT =?", new String[]{userName});
            if (check == -1)
                return false;
            return true;
        }
        return false;
    }

    public long ThemTT(ThuThu thuThu){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaTT",thuThu.getMaTt());
        contentValues.put("HoTenTT",thuThu.getTenTt());
        contentValues.put("MatKhauTT",thuThu.getMK());
        contentValues.put("loaiTaiKhoan", thuThu.getLoaiTaiKhoan());
        return db.insert("ThuThu",null,contentValues);
    }

    public ArrayList<ThuThu> getDanhSachThanhVien() {
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ThuThu tt = new ThuThu();
                tt.setMaTt(cursor.getString(cursor.getColumnIndexOrThrow("MaTT")));
                tt.setTenTt(cursor.getString(cursor.getColumnIndexOrThrow("HoTenTT")));
                tt.setMK(cursor.getString(cursor.getColumnIndexOrThrow("MatKhauTT")));
                tt.setLoaiTaiKhoan(cursor.getString(cursor.getColumnIndexOrThrow("loaiTaiKhoan")));
                list.add(tt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ThuThu getID(String id){
        ArrayList<ThuThu> list = get_LTK("SELECT * FROM ThuThu WHERE MaTT =?",id);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            // Handle the case where the list is empty
            return null;
        }
    }

    public ThuThu getLoaiTK(String ltk){
        ArrayList<ThuThu> list = getData("SELECT * FROM ThuThu WHERE loaiTaiKhoan =?",ltk);
        return list.get(0);
    }

    public ArrayList<ThuThu> getData(String sql, String...selectionArgs) {
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu", selectionArgs);
        while (cursor.moveToNext()){
                ThuThu tt = new ThuThu();
                tt.setMaTt(cursor.getString(cursor.getColumnIndexOrThrow("MaTT")));
                tt.setTenTt(cursor.getString(cursor.getColumnIndexOrThrow("HoTenTT")));
                tt.setMK(cursor.getString(cursor.getColumnIndexOrThrow("MatKhauTT")));
                tt.setLoaiTaiKhoan(cursor.getString(cursor.getColumnIndexOrThrow("loaiTaiKhoan")));
                list.add(tt);
            }

        cursor.close();
        return list;
    }

//    public ArrayList<ThuThu> getMaTT_LTK(String sql, String...selectionArgs) {
//        ArrayList<ThuThu> list = new ArrayList<>();
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT MaTT , loaiTaiKhoan FROM ThuThu", selectionArgs);
//        while (cursor.moveToNext()){
//            ThuThu tt = new ThuThu();
//            tt.setMaTt(cursor.getString(cursor.getColumnIndexOrThrow("MaTT")));
//            tt.setLoaiTaiKhoan(cursor.getString(cursor.getColumnIndexOrThrow("loaiTaiKhoan")));
//            list.add(tt);
//        }
//
//        cursor.close();
//        return list;
//    }

    public ArrayList<ThuThu> get_LTK(String sql, String...selectionArgs) {
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT loaiTaiKhoan , loaiTaiKhoan FROM ThuThu WHERE MaTT =?", selectionArgs);
        while (cursor.moveToNext()){
            ThuThu tt = new ThuThu();
            tt.setLoaiTaiKhoan(cursor.getString(cursor.getColumnIndexOrThrow("loaiTaiKhoan")));
            list.add(tt);
        }

        cursor.close();
        return list;
    }


}
