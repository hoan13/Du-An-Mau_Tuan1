package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Sach;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import hoanhqph30066.fpoly.du_an_mau.DAO.LoaiSachDAO;
import hoanhqph30066.fpoly.du_an_mau.DAO.SachDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.LoaiSach;
import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.R;

public class QuanLy_Sach_Fragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fltb;
    ArrayList<Sach> list;
    SachDAO sachDAO;
    QuanLy_Sach_Adapter adapter_qlSach;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanly_sach,container,false);

        recyclerView = view.findViewById(R.id.recycler_quan_ly_sach);
        fltb = view.findViewById(R.id.fltb_ql_sach);
        list = new ArrayList<>();
        sachDAO = new SachDAO(requireContext());
        adapter_qlSach = new QuanLy_Sach_Adapter(requireContext(),getDsLoaiSach() , sachDAO);
        loadData();

        fltb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater1 = LayoutInflater.from(requireContext());
                View view1 = inflater1.inflate(R.layout.dialog_them_sach, null);
                builder.setView(view1);
                builder.setCancelable(false);
                AppCompatEditText edTenSach = view1.findViewById(R.id.edTenSach);
                AppCompatEditText edGiaThue = view1.findViewById(R.id.edGiaTien);
                Spinner spnLoaiSach = view1.findViewById(R.id.spnLoaiSach);

                SimpleAdapter simpleAdapter = new SimpleAdapter(requireContext(), getDsLoaiSach(), android.R.layout.simple_list_item_1, new String[]{"TenLoai"}, new int[]{android.R.id.text1});
                spnLoaiSach.setAdapter(simpleAdapter);

                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenSach = Objects.requireNonNull(edTenSach.getText()).toString().trim();
                        int giaThue = Integer.parseInt(Objects.requireNonNull(edGiaThue.getText()).toString().trim());
                        HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                        int maLoai = (int) hs.get("MaLoai");
                        boolean check = sachDAO.ThemSach(tenSach, giaThue, maLoai);
                        if (check) {
                            Toast.makeText(requireContext(), "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
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
        list = sachDAO.getDSSach();
        adapter_qlSach.setData(list);
        recyclerView.setAdapter(adapter_qlSach);
    }

    private ArrayList<HashMap<String, Object>> getDsLoaiSach() {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(requireContext());
        ArrayList<LoaiSach> list1 = loaiSachDAO.getDanhSachLoaiSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (LoaiSach loaiSach : list1) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaLoai", loaiSach.getMaLs());
            hs.put("TenLoai", loaiSach.getTenLs());
            listHM.add(hs);
        }
        return listHM;
    }
}
