package hoanhqph30066.fpoly.du_an_mau.DAO;

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
    public ThuThuDAO(Context context){
        dbHelper = new DbHepler(context);
        sharedPreferences =context.getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
    }

    public boolean checkLogin(String userName, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ThuThu WHERE MaTT =? AND MatKhauTT =?", new String[]{userName, password});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("MaTT", cursor.getString(cursor.getColumnIndexOrThrow("MaTT")));
            editor.putString("loaiTaiKhoan", cursor.getString(cursor.getColumnIndexOrThrow("loaiTaiKhoan")));
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
}
