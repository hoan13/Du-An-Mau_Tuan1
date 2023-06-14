package hoanhqph30066.fpoly.du_an_mau.Fragment.Them_Thanh_Vien;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Activity.DangNhapActivity;
import hoanhqph30066.fpoly.du_an_mau.Activity.MainActivity;
import hoanhqph30066.fpoly.du_an_mau.DAO.ThuThuDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.ThuThu;
import hoanhqph30066.fpoly.du_an_mau.R;

public class Them_ThanhVien_Fragment extends Fragment {
    AppCompatEditText tendntvmoi, mktvmoi, nhaplaimktvmoi, hotentvmoi;
    Button btndangkytk, btndanhsach;
    final ArrayList<String> list_loaitk = new ArrayList<>();
    Spinner spnDsLoaiTk;

    ThuThu thuThu = new ThuThu();
    ThuThuDAO thuThuDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_thanhvien,container,false);

        tendntvmoi = view.findViewById(R.id.themtv_ed_tendangnhap);
        hotentvmoi = view.findViewById(R.id.themtv_ed_hoten);
        mktvmoi = view.findViewById(R.id.themtv_ed_mk);
        nhaplaimktvmoi = view.findViewById(R.id.themtv_ed_nlmk);

        btndangkytk = view.findViewById(R.id.themtv_btn_dangky);
        btndanhsach= view.findViewById(R.id.themtv_btn_danhsach);

        spnDsLoaiTk = view.findViewById(R.id.spn_loai_taikhoan);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list_loaitk.add("thuthu");
        list_loaitk.add("thành viên");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,list_loaitk);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spnDsLoaiTk.setAdapter(adapter);

        btndangkytk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thuThuDAO = new ThuThuDAO(getContext());
                Themtv();
            }
        });

        btndanhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhSachThemTv.class);
                startActivity(intent);
            }
        });
    }

    public void Themtv() {
        if (tendntvmoi.length() == 0) {
            tendntvmoi.requestFocus();
            tendntvmoi.setError("Không được để trống phần tên đăng nhập");
        } else if (hotentvmoi.length() == 0) {
            hotentvmoi.requestFocus();
            hotentvmoi.setError("Không được để trống tên thành viên");
        } else if (mktvmoi.length() == 0) {
            mktvmoi.requestFocus();
            mktvmoi.setError("Không để trống mật khẩu");
        } else if (nhaplaimktvmoi.length() == 0) {
            nhaplaimktvmoi.requestFocus();
            nhaplaimktvmoi.setError("Không được để trống");
        } else if (!mktvmoi.getText().toString().equals(nhaplaimktvmoi.getText().toString())) {
            nhaplaimktvmoi.requestFocus();
            nhaplaimktvmoi.setError("Mật khẩu và nhập lại mật khẩu không trùng nhau");
        } else {

            String ltk = spnDsLoaiTk.getSelectedItem().toString();
            String tendn = tendntvmoi.getText().toString();
            String hoten = hotentvmoi.getText().toString();
            String mk = mktvmoi.getText().toString();

            ThuThu tt1 = new ThuThu(tendn,hoten,mk,ltk);
            if (thuThuDAO.ThemTT(tt1) > 0) {
                Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                clear();

            } else {
                Toast.makeText(getContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void clear(){
        tendntvmoi.setText("");
        hotentvmoi.setText("");
        mktvmoi.setText("");
        nhaplaimktvmoi.setText("");
        spnDsLoaiTk.setSelection(0);
    }
}
