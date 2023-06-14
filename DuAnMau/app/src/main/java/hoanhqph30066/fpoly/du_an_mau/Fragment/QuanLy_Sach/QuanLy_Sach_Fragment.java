package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Sach;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import hoanhqph30066.fpoly.du_an_mau.DAO.ThuThuDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.LoaiSach;
import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.Model.ThuThu;
import hoanhqph30066.fpoly.du_an_mau.R;

public class QuanLy_Sach_Fragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton fltb;
    ArrayList<Sach> list;
    ArrayList<LoaiSach> listLoai;
    SachDAO sachDAO;
    QuanLy_Sach_Adapter adapter_qlSach;
    ThuThu thuthulist;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanly_sach,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recycler_quan_ly_sach);
        fltb = view.findViewById(R.id.fltb_ql_sach);

        sachDAO = new SachDAO(getContext());
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        list = sachDAO.getAllData();
        adapter_qlSach = new QuanLy_Sach_Adapter(requireContext(),list);

        // test 2 trượt hiện add
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy>0 || dx>0){
//                    fltb.show();
//                }else {
//                    fltb.hide();
//                }
//            }
//        });
        // het test 2

        //test
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String maThuThu = sharedPreferences.getString("MaTT", "");

        ThuThuDAO thuThuDAO = new ThuThuDAO(getContext(),thuthulist);
        thuthulist = thuThuDAO.getID(maThuThu);

//        ThuThu thuThu =thuThuDAO.getID(maThuThu);

        if(thuthulist.getLoaiTaiKhoan().equals("thành viên")){
            fltb.setVisibility(View.INVISIBLE);

        }
        //het


        fltb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflater1 = LayoutInflater.from(requireContext());
                View view1 = inflater1.inflate(R.layout.dialog_them_sach, null);
                builder.setView(view1);
                builder.setCancelable(false);

                Sach s = new Sach();

                AppCompatEditText edTenSach = view1.findViewById(R.id.edTenSach);
                AppCompatEditText edGiaThue = view1.findViewById(R.id.edGiaTien);
                Spinner spnLoaiSach = view1.findViewById(R.id.spnLoaiSach);


                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,loaiSachDAO.name());
                spnLoaiSach.setAdapter(adapter1);

                spnLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        listLoai = loaiSachDAO.getAllData();
                        s.setTenLoai(listLoai.get(position).getTenLs());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String tenSach = edTenSach.getText().toString();
                        String giathue = edGiaThue.getText().toString();

                        if (tenSach.equals("") || giathue.trim().equals("")) {
                            Toast.makeText(requireContext(), "không được để trống", Toast.LENGTH_SHORT).show();
                        }else {
                            s.setTenSach(edTenSach.getText().toString());
                            s.setGiaSach(Integer.parseInt(edGiaThue.getText().toString()));
                        }
                        if (sachDAO.insert(s)>0){
                            Toast.makeText(getContext(), "them thanh cong", Toast.LENGTH_LONG).show();
                            list = sachDAO.getAllData();
                            adapter_qlSach.setData(list);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "them that bai!", Toast.LENGTH_LONG).show();
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_qlSach);

        super.onViewCreated(view, savedInstanceState);
    }

//    private void loadData() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
//        recyclerView.setLayoutManager(layoutManager);
//        list = sachDAO.getDSSach();
//        adapter_qlSach.setData(list);
//        recyclerView.setAdapter(adapter_qlSach);
//    }

//    private ArrayList<HashMap<String, Object>> getDsLoaiSach() {
//        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(requireContext());
//        ArrayList<LoaiSach> list1 = loaiSachDAO.getDanhSachLoaiSach();
//        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
//        for (LoaiSach loaiSach : list1) {
//            HashMap<String, Object> hs = new HashMap<>();
//            hs.put("MaLoai", loaiSach.getMaLs());
//            hs.put("TenLoai", loaiSach.getTenLs());
//            listHM.add(hs);
//        }
//        return listHM;
//    }
}
