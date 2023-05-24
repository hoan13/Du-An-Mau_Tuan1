package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hoanhqph30066.fpoly.du_an_mau.Model.ThanhVien;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class ThanhVienDAO {
    DbHepler dbHepler;

    public ThanhVienDAO(Context context){
        dbHepler = new DbHepler(context);
    }
    public ArrayList<ThanhVien> GetDSTV() {
        ArrayList<ThanhVien> listtv = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ThanhVien", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setMaTv(Integer.parseInt(cursor.getString(0)));
                thanhVien.setTenTv(cursor.getString(1));
                thanhVien.setCccd(cursor.getString(2));
                listtv.add(thanhVien);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return listtv;

    }

    public long ThemThanhVien(ThanhVien thanhVien) {
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenTV", thanhVien.getTenTv());
        contentValues.put("CccdTV", thanhVien.getCccd());
        return sqLiteDatabase.insert("ThanhVien", null, contentValues);
    }

    public long SuaThanhVien(ThanhVien thanhVien) {
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenTV", thanhVien.getTenTv());
        contentValues.put("CccdTV", thanhVien.getCccd());

        return sqLiteDatabase.update("ThanhVien", contentValues, "MaTV = ?", new String[]{String.valueOf(thanhVien.getMaTv())});
    }
    public long XoaThanhVien(int matv ){
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        return sqLiteDatabase.delete("ThanhVien","MaTV = ?",new String[]{String.valueOf(matv)});
    }

}
