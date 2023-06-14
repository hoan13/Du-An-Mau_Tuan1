package hoanhqph30066.fpoly.du_an_mau.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import hoanhqph30066.fpoly.du_an_mau.Fragment.Doanh_Thu.DoanhThu_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.Doi_Mat_Khau.Doi_MatKhau_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Loai_Sach.QuanLy_LoaiSach_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Phieu_Muon.QuanLy_PhieuMuon_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Sach.QuanLy_Sach_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Thanh_Vien.QuanLy_ThanhVien_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.Sach_Top_Muon.SachMuon_NhieuNhat_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Fragment.Them_Thanh_Vien.Them_ThanhVien_Fragment;
import hoanhqph30066.fpoly.du_an_mau.Model.ThuThu;
import hoanhqph30066.fpoly.du_an_mau.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    ThuThu thuThu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.id_drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, 0, 0);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.id_nav);
        navigationView.setNavigationItemSelectedListener(this);

        View HeaderView = navigationView.getHeaderView(0);
        SharedPreferences shared_HoTen = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String thuThu_hoTen = shared_HoTen.getString("HoTenTT", "");
        TextView edUser =HeaderView.findViewById(R.id.welcome_tv);

        edUser.setText("Welcome " + thuThu_hoTen+ " !");

        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan", MODE_PRIVATE);
        String role = sharedPreferences.getString("MaTT", "");
        if (!role.equals("admin")){
            Menu menu = navigationView.getMenu();
//            menu.findItem(R.id.menu_tk_10_sach_mnn).setVisible(false);
            menu.findItem(R.id.menu_tk_danhthu).setVisible(false);
            menu.findItem(R.id.menu_nd_them_nguoidung).setVisible(false);
        }

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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có muốn thoát không?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        toolbar.setTitle(item.getTitle().toString());
            return true;

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