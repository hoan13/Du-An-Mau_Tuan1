package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Phieu_Muon;

import static hoanhqph30066.fpoly.du_an_mau.R.drawable.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import hoanhqph30066.fpoly.du_an_mau.DAO.PhieuMuonDAO;
import hoanhqph30066.fpoly.du_an_mau.DAO.SachDAO;
import hoanhqph30066.fpoly.du_an_mau.DAO.ThanhVienDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.PhieuMuon;
import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.Model.ThanhVien;
import hoanhqph30066.fpoly.du_an_mau.R;

public class PhieuMuon_Adapter extends RecyclerView.Adapter<PhieuMuon_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<PhieuMuon> list;


    public PhieuMuon_Adapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(ArrayList<PhieuMuon> lst) {
        this.list = lst;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.items_phieu_muon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvTenThanhVien.setText(list.get(position).getTenThanhVien());
        holder.tvTenThuThu.setText(list.get(position).getMaThuThu());
        holder.tvTenSach.setText(list.get(position).getTenSach());
        holder.tvNgay.setText(list.get(position).getNgay());
        holder.tvTien.setText(String.valueOf(list.get(position).getTienThue()));
        holder.tvTenTrangThai.setText(list.get(position).getTrangThai());

        if(list.get(position).getTrangThai().equals("đã trả sách")){
            holder.tvTenTrangThai.setBackground(ContextCompat.getDrawable(context, button_background_mauxanh));
        } else if (list.get(position).getTrangThai().equals("chưa trả sách")) {
            holder.tvTenTrangThai.setBackground(ContextCompat.getDrawable(context, button_background_maudo));
        }


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onLongClick(View v) {
                @SuppressLint("RestrictedApi") MenuBuilder builder = new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.menu_popup_edot_delete, builder);
                @SuppressLint("RestrictedApi") MenuPopupHelper optionmenu = new MenuPopupHelper(context, builder, v);

                builder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        if (item.getItemId() == R.id.option_edit) {
                            updateDia(list.get(position), position);
                            return true;
                        } else if (item.getItemId() == R.id.option_delete) {
                            showDele(list.get(position).getMaPhieuMuon());
                            return true;
                        } else {
                            return false;
                        }
                    }


                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                optionmenu.show();
                return true;
            }
        });



    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTenThanhVien, tvTenThuThu, tvTenSach, tvNgay, tvTenTrangThai, tvTien;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenThanhVien = itemView.findViewById(R.id.tvTenThanhVien);
            tvTenThuThu = itemView.findViewById(R.id.tvTenThuThu);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvNgay = itemView.findViewById(R.id.tvNgay);
            tvTenTrangThai = itemView.findViewById(R.id.tvTrangThai);
            tvTien = itemView.findViewById(R.id.tvTien);

        }
    }

    private void updateDia(PhieuMuon pm, int position) {
        SachDAO sachDao = new SachDAO(context);
        ThanhVienDAO tvDao = new ThanhVienDAO(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suaphieumuon, null);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        Spinner spn_suatentv = view.findViewById(R.id.dialogpm_suatentv);
        Spinner spn_suatensach = view.findViewById(R.id.dialogpm_suatensach);
        TextView suagiathue = view.findViewById(R.id.edSuagiathue);
        AppCompatEditText suangaythue = view.findViewById(R.id.edSuangaythue);
        CheckBox suatrangthai = view.findViewById(R.id.ckb_suatrangthai);
        Button suaphieumuon = view.findViewById(R.id.btnsuapm);
        Button thoatphieumuon = view.findViewById(R.id.btnthoatsuapm);


        String checktt = String.valueOf(list.get(position).getTrangThai());
        if(checktt.equals("đã trả sách")){
            suatrangthai.setChecked(true);
        }else {
            suatrangthai.setChecked(false);
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String maThuThu = sharedPreferences.getString("MaTT", "");

        suangaythue.setText(list.get(position).getNgay());
        suangaythue.setFocusable(false);

//        suangaythue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShowDateDialog(suangaythue);
//            }
//        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        suangaythue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        suangaythue.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        ArrayList<ThanhVien> tvList = tvDao.getAllData();
        ArrayList<Sach> sList = sachDao.getAllData();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, tvDao.name());
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, sachDao.name());
        spn_suatentv.setAdapter(adapter1);
        spn_suatensach.setAdapter(adapter2);

        int spIndex = 0;
        for (Sach sach : sList) {
            if (sach.getTenSach().equals(pm.getTenSach())) {
                spn_suatensach.setSelection(spIndex);
                break;
            }
            spIndex++;
        }
        int spIndex1 = 0;
        for (ThanhVien tv : tvList) {
            if (tv.getTenTv().equals(pm.getTenThanhVien())) {
                spn_suatentv.setSelection(spIndex1);
                break;
            }
            spIndex1++;
        }
        // click item của spn tên
        spn_suatentv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String i = tvList.get(position).getTenTv();
                pm.setTenThanhVien(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // click item của spn sách
        spn_suatensach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String i = sList.get(position).getTenSach();
                pm.setTenSach(i);
                suagiathue.setText(String.valueOf(sList.get(position).getGiaSach()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // click cancle
        thoatphieumuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        //click them
        suaphieumuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDAO pmDao = new PhieuMuonDAO(context);
                String trangthai = "";
                if (suatrangthai.isChecked() == true) {
                    trangthai = "đã trả sách";
                } else if(suatrangthai.isChecked() == false) {
                    trangthai ="chưa trả sách";
                }
                String ten = suangaythue.getText().toString();
                if (ten.trim().equals("")){
                    Toast.makeText(context, "không được để trống ngày", Toast.LENGTH_SHORT).show();
                }else {
                    pm.setNgay(ten);
                    pm.setTrangThai(trangthai);
                    pm.setTienThue(Integer.parseInt(suagiathue.getText().toString()));
                    pm.setMaThuThu(maThuThu);

//                        if (pmDao.ThemPhieuMuonSC(obj_pm)>=0){
//                            Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_LONG).show();
//                            pmlist = pmDao.getAllData();
//                            phieuMuon_adapter.setData(pmlist);
//                            alertDialog.dismiss();
//                        }else {
//                            Toast.makeText(getContext(), "them that bai!", Toast.LENGTH_LONG).show();
//                            alertDialog.dismiss();
//                        }

                    if (pmDao.SuaPhieuMuonSCNC(pm)==-1) {
                        Toast.makeText(context, "sửa thất bại!", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();

                    } else if (pmDao.SuaPhieuMuonSCNC(pm)==0) {
                        Toast.makeText(context, "bạn không có quyền sửa phiếu mượn", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    }else {

                        Toast.makeText(context, "sửa thành công", Toast.LENGTH_LONG).show();
                        list = pmDao.getAllData();
                        setData(list);
                        alertDialog.dismiss();
                    }
                }
            }
        });

        alertDialog.show();

    }

    private void ShowDateDialog(AppCompatEditText ngaythue) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
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
                        ngaythue.setText(ngay + "/" + thang + "/" + year);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }
    public void showDele(int id){
        AlertDialog.Builder dialogDL = new AlertDialog.Builder(context);
        dialogDL.setMessage("Bạn có muốn xóa không?");
        dialogDL.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDL.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PhieuMuonDAO pmDao = new PhieuMuonDAO(context);
                if (pmDao.XoaPhieuMuon(id) > 0) {
                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    list = pmDao.getAllData();
                    setData(list);
                } else {
                    Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();

            }
        });
        dialogDL.show();
    }
}
