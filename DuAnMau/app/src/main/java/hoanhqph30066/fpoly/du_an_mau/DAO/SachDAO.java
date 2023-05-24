package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class SachDAO {
    DbHepler dbHepler;
    public SachDAO(Context context){
        dbHepler = new DbHepler(context);
    }

}
