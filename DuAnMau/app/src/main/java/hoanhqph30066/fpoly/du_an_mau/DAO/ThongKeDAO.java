package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class ThongKeDAO {
    DbHepler dbHelper;
    private SQLiteDatabase sqLite;
    private Context context;

    public ThongKeDAO(Context context) {
        this.context = context;
        dbHelper = new DbHepler(context);
        sqLite = dbHelper.getWritableDatabase();
    }

    public ArrayList<Sach> getTop10MuonSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT TenSach,count(TenSach) as soluong FROM PhieuMuon GROUP BY TenSach ORDER BY soluong DESC LIMIT 10", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getString(0), cursor.getInt(1)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay){
        String sqlDoanhThu = "SELECT SUM(GiaThueSach) as doanhThu FROM PhieuMuon WHERE Ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = sqLite.rawQuery(sqlDoanhThu,new String[]{tuNgay,denNgay});
        while (cursor.moveToNext()){
            try{
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }

        }
        return list.get(0);
    }
}
