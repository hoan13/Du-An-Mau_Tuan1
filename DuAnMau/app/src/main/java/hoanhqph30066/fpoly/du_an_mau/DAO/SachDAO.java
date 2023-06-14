package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class SachDAO {
    DbHepler dbHepler;

    SQLiteDatabase sqLite;
    public SachDAO(Context context){
        dbHepler = new DbHepler(context);
        sqLite = dbHepler.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Sach> getDataSach(String sql, String... SelectAvg){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Sach", SelectAvg);
        while (cursor.moveToNext()){
            Sach s = new Sach();
            s.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaSach"))));
            s.setTenSach(cursor.getString(cursor.getColumnIndex("TenSach")));
            s.setGiaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("GiaThueSach"))));
            s.setTenLoai(cursor.getString(cursor.getColumnIndex("TenLoai")));
            list.add(s);
        }
        return list;
    }
    public ArrayList<Sach> getDSSach(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHepler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Sach",null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getInt(cursor.getColumnIndexOrThrow("MaSach")));
                sach.setGiaSach(cursor.getInt(cursor.getColumnIndexOrThrow("GiaThueSach")));
                sach.setTenSach(cursor.getString(cursor.getColumnIndexOrThrow("TenSach")));
                sach.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("TenLoai")));
                list.add(sach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public long insert(Sach s){

        ContentValues values = new ContentValues();
        values.put("TenSach", s.getTenSach());
        values.put("GiaThueSach", s.getGiaSach());
        values.put("TenLoai", s.getTenLoai());

        return sqLite.insert("Sach", null, values);
    }
    public ArrayList<Sach> getAllData(){
        String sql = "SELECT * FROM PhieuMuon";
        return getDataSach(sql);
    }


    public ArrayList<String> name() {
        String name = "SELECT TenSach FROM Sach";
        return getName(name);
    }


    public ArrayList<String> getName(String sql, String... SelectAvgs) {
        ArrayList<String> lst = new ArrayList<>();
        Cursor cursor = sqLite.rawQuery(sql, SelectAvgs);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("TenSach"));
            lst.add(name);
        }
        return lst;

    }
    public long update(Sach s){
        ContentValues values = new ContentValues();
        values.put("TenSach", s.getTenSach());
        values.put("GiaThueSach", s.getGiaSach());
        values.put("TenLoai", s.getTenLoai());
        return sqLite.update("Sach", values, "MaSach = ?", new String[]{String.valueOf(s.getMaSach())});
    }
    public int delete(int ID) {
        return sqLite.delete("Sach", "MaSach = ?", new String[]{String.valueOf(ID)});
    }

    public int xoaSachSC(String tensach) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PhieuMuon WHERE TenSach =?", new String[]{String.valueOf(tensach)});
        if (cursor.getCount() != 0) {
            return -1; // Không được xoá vì sách có trong phiếu mượn
        }
        int check = db.delete("Sach", "MaSach =?", new String[]{String.valueOf(tensach)});
        if (check == -1)
            return 0; // xoá thất bại
        return 1; // xoá thành công
    }

}
