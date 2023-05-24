package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Model.ThuThu;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class ThuThuDAO {
    DbHepler dbHelper;
    public ThuThuDAO(Context context){
        dbHelper = new DbHepler(context);
    }

    public ArrayList<ThuThu> GetDSTT(){
        ArrayList<ThuThu> listtt = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ThuThu",null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTt(Integer.parseInt(cursor.getString(0)));
                thuThu.setTenTt(cursor.getString(1));
                thuThu.setMK(cursor.getString(2));
                listtt.add(thuThu);
            }while (cursor.moveToNext());
        }
        return  listtt;
    }
    public long ThemTT(ThuThu thuThu){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaTT",thuThu.getMaTt());
        contentValues.put("TenTT",thuThu.getTenTt());
        contentValues.put("MatKhauTT",thuThu.getMK());

        return sqLiteDatabase.insert("ThuThu",null,contentValues);
    }
    public long SuaTT(ThuThu thuThu){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaTT",thuThu.getMaTt());
        contentValues.put("TenTT",thuThu.getTenTt());
        contentValues.put("MatKhauTT",thuThu.getMK());
        return sqLiteDatabase.update("ThuThu",contentValues,"MaTT = ?",new String[]{String.valueOf(thuThu.getMaTt())});
    }
    public long XoaTT(ThuThu thuThu){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        return sqLiteDatabase.delete("ThuThu","MaTT = ?",new String[]{String.valueOf(thuThu.getMaTt())});
    }
}
