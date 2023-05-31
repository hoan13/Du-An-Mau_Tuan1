package hoanhqph30066.fpoly.du_an_mau.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import hoanhqph30066.fpoly.du_an_mau.DAO.ThanhVienDAO;
import hoanhqph30066.fpoly.du_an_mau.DAO.ThuThuDAO;
import hoanhqph30066.fpoly.du_an_mau.R;

public class DangNhapActivity extends AppCompatActivity {
    Button dangnhap , thoat;
    AppCompatEditText tendangnhap , matkhau;
    CheckBox luutaikhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        dangnhap = findViewById(R.id.btnDangNhap_Dn);
        thoat = findViewById(R.id.btnHuy_Dn);

        tendangnhap = findViewById(R.id.ed_UserName_Dn);
        matkhau = findViewById(R.id.ed_Password_Dn);

        luutaikhoan = findViewById(R.id.ckb_Nho_mk);

        ThuThuDAO thuThuDAO = new ThuThuDAO(DangNhapActivity.this);

        SharedPreferences sharedPreferences = getSharedPreferences("LuuTaiKhoan", MODE_PRIVATE);
        boolean isAccountSaved = sharedPreferences.getBoolean("IsAccountSaved", false);

        String saveUserName = sharedPreferences.getString("MaTT", null);
        String savePassword = sharedPreferences.getString("MatKhauTT", null);

        luutaikhoan.setChecked(isAccountSaved);
        if (isAccountSaved && saveUserName != null && savePassword != null){
            tendangnhap.setText(saveUserName);
            matkhau.setText(savePassword);
        }

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = tendangnhap.getText().toString().trim();
                String mk = matkhau.getText().toString().trim();

                if(thuThuDAO.checkLogin(ten,mk)){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (luutaikhoan.isChecked()) {
                        editor.putBoolean("IsAccountSaved", true);
                        editor.putString("MaTT", ten);
                        editor.putString("MatKhauTT", mk);
                    } else {
                        editor.putBoolean("IsAccountSaved", false);
                        editor.remove("MaTT");
                        editor.remove("MatKhauTT");
                    }
                    editor.apply();
                    Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(DangNhapActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
//                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
//                startActivity(intent);


            }
        });
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}