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

    public ArrayList<LoaiSach> GetDSLoaiSach(){
        ArrayList<LoaiSach> listLS = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =dbHepler.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LoaiSach",null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setMaLs(Integer.parseInt(cursor.getString(0)));
                loaiSach.setTenLs(cursor.getString(1));
                listLS.add(loaiSach);
            }while (cursor.moveToNext());
        }
        return listLS;
    }
    public long ThemLoaiSach(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenLoai",loaiSach.getTenLs());
        return sqLiteDatabase.insert("LoaiSach",null,contentValues);
    }
    public long SuaLS(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenLoai",loaiSach.getTenLs());

        return  sqLiteDatabase.update("LoaiSach",contentValues,"MaLoai = ?",new String[]{String.valueOf(loaiSach.getMaLs())});
    }
    public long XoaLoaiSach(int MaLS){
        SQLiteDatabase sqLiteDatabase = dbHepler.getWritableDatabase();
        return  sqLiteDatabase.delete("LoaiSach","MaLoai = ?",new String[]{String.valueOf(MaLS)});
    }
}
