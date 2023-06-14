package hoanhqph30066.fpoly.du_an_mau.Fragment.Them_Thanh_Vien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.DAO.ThuThuDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.ThuThu;
import hoanhqph30066.fpoly.du_an_mau.R;

public class DanhSachThemTv extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterDanhSach_Them_tv adapter;
    ArrayList<ThuThu> list = new ArrayList<>();
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_them_tv);

        recyclerView = findViewById(R.id.rcl_danhsachtk);
        adapter = new AdapterDanhSach_Them_tv(DanhSachThemTv.this);
        thuThuDAO = new ThuThuDAO(DanhSachThemTv.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhSachThemTv.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = thuThuDAO.getDanhSachThanhVien();
        adapter.setData(list);
    }
}