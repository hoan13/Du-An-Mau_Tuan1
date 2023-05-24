package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;

import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public ThongKeDAO(Context context){
        this.context = context;
        DbHepler dbHepler = new DbHepler(context);
        db = dbHepler.getWritableDatabase();
    }
}
