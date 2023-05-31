package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Thanh_Vien;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import hoanhqph30066.fpoly.du_an_mau.DAO.ThanhVienDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.ThanhVien;
import hoanhqph30066.fpoly.du_an_mau.R;

public class QuanLy_ThanhVien_Fragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton flbt;
    ThanhVienDAO thanhVienDAO;
    ArrayList<ThanhVien> list;
    ThanhVien_Adapter adapterThanhVien;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanly_thanhvien,container,false);

        recyclerView = view.findViewById(R.id.recycview_ql_thanhvien);
        flbt = view.findViewById(R.id.flbt_ql_thanhvien);
        thanhVienDAO = new ThanhVienDAO(requireContext());
        list = new ArrayList<>();
        adapterThanhVien = new ThanhVien_Adapter(requireContext());
        loadData();

        flbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater1 = LayoutInflater.from(requireContext());
                View view1 = inflater1.inflate(R.layout.dialog_them_thanh_vien, null);
                builder.setView(view1);
                builder.setCancelable(false);
                AppCompatEditText edHoTen = view1.findViewById(R.id.edHoTenTV);
                AppCompatEditText edNamSinh = view1.findViewById(R.id.edNamSinhTV);
                edNamSinh.setFocusable(false);
                edNamSinh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowDateDialog(edNamSinh);
                    }
                });
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String hoTen = Objects.requireNonNull(edHoTen.getText()).toString().trim();
                        String namSinh = Objects.requireNonNull(edNamSinh.getText()).toString().trim();
                        boolean check = thanhVienDAO.ThemThanhVien(hoTen, namSinh);
                        if (check) {
                            Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(requireContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        return view;
    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        list = thanhVienDAO.getDanhSachThanhVien();
        adapterThanhVien.setData(list);
        recyclerView.setAdapter(adapterThanhVien);
    }
    private void ShowDateDialog(EditText edNgay) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String thang = "";
                        if(month < 9){
                            thang = "0" +(month+1);
                        }else {
                            thang = String.valueOf(month+1);
                        }
                        String ngay = "";
                        if(dayOfMonth < 10){
                            ngay = "0" +dayOfMonth;
                        }else {
                            ngay = String.valueOf(dayOfMonth);
                        }
                        edNgay.setText(ngay  + "/" +thang+ "/" +year);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }
}
