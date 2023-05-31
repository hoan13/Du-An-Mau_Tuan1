package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Model.LoaiSach;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class LoaiSachDAO {
    DbHepler dbHepler;
    public LoaiSachDAO(Context context){
        dbHepler = new DbHepler(context);
    }

    public ArrayList<LoaiSach> getDanhSachLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LoaiSach", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean ThemLoaiSach(String themLoaiSach) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenLoai", themLoaiSach);
        long check = db.insert("LoaiSach", null, values);
        return check != -1;
    }

    public boolean SuaLoaiSach(LoaiSach loaiSach) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenLoai", loaiSach.getTenLs());
        int check = db.update("LoaiSach", values, "MaLoai =?", new String[]{String.valueOf(loaiSach.getMaLs())});
        return check != -1;
    }

    public int XoaLoaiSach(int idLoaiSach) {
        SQLiteDatabase db = dbHepler.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Sach WHERE MaLoai =?", new String[]{String.valueOf(idLoaiSach)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        int check = db.delete("LoaiSach", "MaLoai =?", new String[]{String.valueOf(idLoaiSach)});
        if (check == -1)
            return 0;
        return 1;
    }


}
