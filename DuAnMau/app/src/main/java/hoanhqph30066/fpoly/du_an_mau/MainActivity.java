package hoanhqph30066.fpoly.du_an_mau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import hoanhqph30066.fpoly.du_an_mau.Fragment.Doanh_Thu.DoanhThu_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.Doi_Mat_Khau.Doi_MatKhau_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Loai_Sach.QuanLy_LoaiSach_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Phieu_Muon.QuanLy_PhieuMuon_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Sach.QuanLy_Sach_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Thanh_Vien.QuanLy_ThanhVien_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.Sach_Muon_Nhieu_Nhat.SachMuon_NhieuNhat_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.Them_Thanh_Vien.Them_ThanhVien_Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.id_drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, 0, 0);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.id_nav);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_ql_phieumuon) {
            drawerLayout.close();
            replaceFragment(new QuanLy_PhieuMuon_Fragment());
            return true;
        } else if (item.getItemId() == R.id.menu_ql_sach) {
            drawerLayout.close();
            replaceFragment(new QuanLy_Sach_Fragment());
            return true;
        } else if (item.getItemId() == R.id.menu_ql_loai_sach) {
            drawerLayout.close();
            replaceFragment(new QuanLy_LoaiSach_Fragment());
            return true;
        } else if (item.getItemId() == R.id.menu_ql_thanhvien) {
            drawerLayout.close();
            replaceFragment(new QuanLy_ThanhVien_Fragment());
            return true;
        } else if (item.getItemId() == R.id.menu_tk_10_sach_mnn) {
            drawerLayout.close();
            replaceFragment(new SachMuon_NhieuNhat_Fragment());
            return true;
        } else if (item.getItemId() == R.id.menu_tk_danhthu) {
            drawerLayout.close();
            replaceFragment(new DoanhThu_Fragment());
            return true;
        } else if (item.getItemId() == R.id.menu_nd_them_nguoidung) {
            drawerLayout.close();
            replaceFragment(new Them_ThanhVien_Fragment());
            return true;
        } else if (item.getItemId() == R.id.menu_nd_doimk) {
            drawerLayout.close();
            replaceFragment(new Doi_MatKhau_Fragment());
            return true;
        } else if (item.getItemId() == R.id.menu_nd_thoat) {
            finish();
            return true;
        } else {
            return false;
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
        transactions.replace(R.id.layout_content, fragment);
        transactions.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen()) {
            drawerLayout.close();
        } else {
            super.onBackPressed();
        }

    }
}