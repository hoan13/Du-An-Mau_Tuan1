package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Model.ThanhVien;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class ThanhVienDAO {
    DbHepler dbHepler;

    public ThanhVienDAO(Context context){
        dbHepler = new DbHepler(context);
    }

    public ArrayList<ThanhVien> getDanhSachThanhVien() {
        ArrayList<ThanhVien> listtv = new ArrayList<>();
        SQLiteDatabase db = dbHepler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThanhVien", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setMaTv(cursor.getInt(cursor.getColumnIndexOrThrow("MaTV")));
                thanhVien.setTenTv(cursor.getString(cursor.getColumnIndexOrThrow("HoTenTV")));
                thanhVien.setNamsinh(cursor.getString(cursor.getColumnIndexOrThrow("NamSinhTV")));
                listtv.add(thanhVien);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return listtv;

    }

    public boolean ThemThanhVien(String hoten, String namsinh) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTenTV", hoten);
        values.put("NamSinhTV", namsinh);
        long check = db.insert("ThanhVien", null, values);
        return check != -1;
    }

    public boolean SuaThanhVien(int matv ,String hoten, String namsinh) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTenTV", hoten);
        values.put("NamSinhTV", namsinh);
        long check = db.update("ThanhVien",values,"MaTV =?", new String[]{String.valueOf(matv)});
        return check != -1;
    }
    public int XoaThanhVien(String tentv ){
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuMuon WHERE HoTenTV =?", new String[]{String.valueOf(tentv)});
        if (cursor.getCount() != 0) {
            return -1; // Thành viên đang có phiếu mượn
        }
        int check = db.delete("ThanhVien", "MaTV =?", new String[]{String.valueOf(tentv)});
        if (check == -1)
            return 0; // Xoá thất bại
        return 1; // Xoá thành công
    }

    public ArrayList<String> name() {
        String name = "SELECT HoTenTV FROM ThanhVien";
        return getName(name);
    }


    public ArrayList<String> getName(String sql, String... SelectAvgs) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ArrayList<String> lst = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, SelectAvgs);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("HoTenTV"));
            lst.add(name);
        }
        return lst;

    }




    @SuppressLint("Range")
    public ArrayList<ThanhVien> getDataThanhVien(String sql, String... SelectAvg){
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM ThanhVien", SelectAvg);
        while (cursor.moveToNext()){
            ThanhVien tv = new ThanhVien();
            tv.setMaTv(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaTV"))));
            tv.setTenTv(cursor.getString(cursor.getColumnIndex("HoTenTV")));
            tv.setNamsinh(cursor.getString(cursor.getColumnIndex("NamSinhTV")));
            list.add(tv);
        }
        return list;
    }

    public ArrayList<ThanhVien> getAllData(){
        String sql = "SELECT * FROM ThanhVien";
        return getDataThanhVien(sql);
    }

}
