package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Phieu_Muon;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import hoanhqph30066.fpoly.du_an_mau.DAO.PhieuMuonDAO;
import hoanhqph30066.fpoly.du_an_mau.DAO.SachDAO;
import hoanhqph30066.fpoly.du_an_mau.DAO.ThanhVienDAO;
import hoanhqph30066.fpoly.du_an_mau.DAO.ThuThuDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.PhieuMuon;
import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.Model.ThanhVien;
import hoanhqph30066.fpoly.du_an_mau.Model.ThuThu;
import hoanhqph30066.fpoly.du_an_mau.R;

public class QuanLy_PhieuMuon_Fragment extends Fragment {
    private ArrayList<ThanhVien> thanhVienlist;
    private ArrayList<Sach> sachlist;
    private ArrayList<PhieuMuon> pmlist;
    private PhieuMuon_Adapter phieuMuon_adapter;

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ThuThu thuthulist;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanly_phieumuon_, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycview_ql_phieumuon);
        floatingActionButton = view.findViewById(R.id.flbt_ql_phieumuon);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy>0 || dx>0){
//                    floatingActionButton.show();
//                }else {
//                    floatingActionButton.hide();
//                }
//            }
//        });

        //test
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String maThuThu = sharedPreferences.getString("MaTT", "");

        ThuThuDAO thuThuDAO = new ThuThuDAO(getContext(),thuthulist);
        thuthulist = thuThuDAO.getID(maThuThu);

//        ThuThu thuThu =thuThuDAO.getID(maThuThu);

        if(thuthulist.getLoaiTaiKhoan().equals("thành viên")){
            floatingActionButton.setVisibility(View.INVISIBLE);
        }
        //het

        //test 2

        // het test 2

        SachDAO sachDao = new SachDAO(getContext());
        ThanhVienDAO tvDao = new ThanhVienDAO(getContext());
        PhieuMuonDAO pmDao = new PhieuMuonDAO(getContext());

        pmlist = pmDao.getAllData();
        phieuMuon_adapter = new PhieuMuon_Adapter(getContext(),pmlist );

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();

                View view1 = inflater.inflate(R.layout.dialogpm_themphieumuon, null);

                builder.setView(view1);

                Spinner spn_themtentv = view1.findViewById(R.id.spn_pmthem_tentv);
                Spinner spn_themsach = view1.findViewById(R.id.spn_pmthem_tensach);
                TextView themgiasach = view1.findViewById(R.id.ed_pmthem_giathue);

                AppCompatEditText themngaythue = view1.findViewById(R.id.ed_pmthem_ngaythue);

                CheckBox themtrangthai = view1.findViewById(R.id.ckb_pmthem_trangthai);
                Button themphieumuon = view1.findViewById(R.id.btn_pm_thempm);
                Button thoatphieumuon = view1.findViewById(R.id.btn_pmthem_thoatsuapm);



                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
                String maThuThu = sharedPreferences.getString("MaTT", "");

                PhieuMuon obj_pm = new PhieuMuon();

                themngaythue.setFocusable(false);
                themngaythue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowDateDialog(themngaythue);
                    }

                });

                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, tvDao.name());
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, sachDao.name());
                spn_themtentv.setAdapter(adapter1);
                spn_themsach.setAdapter(adapter2);

                spn_themtentv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        thanhVienlist = tvDao.getAllData();
                        obj_pm.setTenThanhVien(thanhVienlist.get(position).getTenTv());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spn_themsach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sachlist = sachDao.getAllData();
                        obj_pm.setTenSach(sachlist.get(position).getTenSach());
                        themgiasach.setText(String.valueOf(sachlist.get(position).getGiaSach()));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();


                thoatphieumuon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                themphieumuon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trangthai = "";
                        if (themtrangthai.isChecked() == true) {
                            trangthai = "đã trả sách";
                        } else if(themtrangthai.isChecked() == false) {
                            trangthai ="chưa trả sách";
                        }
                        String ten = themngaythue.getText().toString();
                        if (ten.trim().equals("")){
                            Toast.makeText(getContext(), "không được để trống ngày", Toast.LENGTH_SHORT).show();
                        }else {
                            obj_pm.setNgay(ten);
                            obj_pm.setTrangThai(trangthai);
                            obj_pm.setTienThue(Integer.parseInt(themgiasach.getText().toString()));
                            obj_pm.setMaThuThu(maThuThu);

                        if (pmDao.ThemPhieuMuonSC(obj_pm)>=0){
                            Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_LONG).show();
                            pmlist = pmDao.getAllData();
                            phieuMuon_adapter.setData(pmlist);
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getContext(), "them that bai!", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }

//                            if (pmDao.ThemPhieuMuonSCNC(obj_pm)==-1) {
//                                Toast.makeText(getContext(), "them that bai!", Toast.LENGTH_LONG).show();
//                                alertDialog.dismiss();
//                            } else if (pmDao.ThemPhieuMuonSCNC(obj_pm)==0) {
//                                Toast.makeText(getContext(), "bạn không có quyền thêm phiếu mượn", Toast.LENGTH_LONG).show();
//                                alertDialog.dismiss();
//                            }else {
//
//                            Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_LONG).show();
//                                pmlist = pmDao.getAllData();
//                                phieuMuon_adapter.setData(pmlist);
//                                alertDialog.dismiss();
//                            }


                        }
                    }
                });
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(phieuMuon_adapter);
        super.onViewCreated(view, savedInstanceState);
    }

    private void ShowDateDialog(EditText ngaythue) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String thang = "";
                        if (month < 9) {
                            thang = "0" + (month + 1);
                        } else {
                            thang = String.valueOf(month + 1);
                        }
                        String ngay = "";
                        if (dayOfMonth < 10) {
                            ngay = "0" + dayOfMonth;
                        } else {
                            ngay = String.valueOf(dayOfMonth);
                        }
                        String data = ngay + "/" + thang + "/" + year;
                        ngaythue.setText(data);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }


}
