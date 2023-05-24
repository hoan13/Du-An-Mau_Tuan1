package hoanhqph30066.fpoly.du_an_mau.DAO;

import android.content.Context;

import hoanhqph30066.fpoly.du_an_mau.database.DbHepler;

public class PhieuMuonDAO {
    DbHepler dbHepler;
    public PhieuMuonDAO(Context context){
        dbHepler = new DbHepler(context);
    }
}
