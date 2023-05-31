package hoanhqph30066.fpoly.du_an_mau.Fragment.Doi_Mat_Khau;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import hoanhqph30066.fpoly.du_an_mau.Activity.DangNhapActivity;
import hoanhqph30066.fpoly.du_an_mau.DAO.ThuThuDAO;
import hoanhqph30066.fpoly.du_an_mau.R;

public class Doi_MatKhau_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_matkhau, container, false);

        AppCompatEditText edmkcu = view.findViewById(R.id.dmk_ed_mk_cu);
        AppCompatEditText ed_mkmoi = view.findViewById(R.id.dmk_ed_mk_moi);
        AppCompatEditText ednl_mkmoi = view.findViewById(R.id.dmk_ed_nlmk_moi);

        Button btnluu = view.findViewById(R.id.dmk_btnluu);
        Button btnlamlai = view.findViewById(R.id.dmk_btnlamlai);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkcu = edmkcu.getText().toString().trim();
                String mkmoi = ed_mkmoi.getText().toString().trim();
                String nl_mkmoi = ednl_mkmoi.getText().toString().trim();

                boolean check = false;
                if (mkmoi.equals(nl_mkmoi)) {
                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
                    String maThuThu = sharedPreferences.getString("MaTT", null);
                    ThuThuDAO thuThuDAO = new ThuThuDAO(requireContext());
                    check = thuThuDAO.doiMatKhau(maThuThu, mkcu, mkmoi);

                    if (check) {
                        Toast.makeText(requireContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(requireActivity(), DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(requireContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnlamlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edmkcu.setText("");
                ed_mkmoi.setText("");
                ednl_mkmoi.setText("");
            }
        });

        return view;
    }
}
