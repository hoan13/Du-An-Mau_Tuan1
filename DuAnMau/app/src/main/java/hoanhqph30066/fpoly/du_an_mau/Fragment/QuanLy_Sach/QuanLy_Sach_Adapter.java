package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Sach;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

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

public class QuanLy_Sach_Adapter extends RecyclerView.Adapter<QuanLy_Sach_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Sach> list;

    private SachDAO sachDAO;

    ThuThu thuthulist;

    public QuanLy_Sach_Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(ArrayList<Sach> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,@SuppressLint("RecyclerView") int position) {
        //test
        SharedPreferences sharedPreferences = context.getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String maThuThu = sharedPreferences.getString("MaTT", "");

        ThuThuDAO thuThuDAO = new ThuThuDAO(context,thuthulist);
        thuthulist = thuThuDAO.getID(maThuThu);

//        ThuThu thuThu =thuThuDAO.getID(maThuThu);

        if(thuthulist.getLoaiTaiKhoan().equals("thành viên")){
            holder.imgSuaSach.setVisibility(View.INVISIBLE);
            holder.imgXoaSach.setVisibility(View.INVISIBLE);
        }
        //het
        Sach sach = list.get(position);
        if (sach == null) {
            return;
        }
        holder.tvTenSach.setText(sach.getTenSach());
        holder.tvGiaThueSach.setText(String.valueOf(sach.getGiaSach()));
        holder.tvTenLoaiSach.setText(sach.getTenLoai());

        holder.imgXoaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Sách :" +sach.getTenSach());
                builder.setTitle("Bạn có chắc muốn xóa ");
                builder.setCancelable(false);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            sachDAO = new SachDAO(context);
//                        if(sachDAO.delete(sach.getMaSach())>0){
//                            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
//                            list.clear();
//                            list = sachDAO.getDSSach();
//                            notifyDataSetChanged();
//                        }
//                        else {
//                            Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
//                        }

                        int check = sachDAO.xoaSachSC(list.get(position).getTenSach());
                        switch (check) {
                            case 1:
                                list.clear();
                                list = sachDAO.getDSSach();
                                notifyItemRemoved(holder.getAdapterPosition());
                                Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "có sách này trong phiếu mượn, không thể xoá", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }

                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.imgSuaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    updateDia(list.get(position),position);
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



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvGiaThueSach, tvTenSach, tvTenLoaiSach;
        ImageView imgXoaSach, imgSuaSach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSachQLS);
            tvGiaThueSach = itemView.findViewById(R.id.tvGiaThueQLS);
            tvTenLoaiSach = itemView.findViewById(R.id.tvTenLoaiSachQLS);

            imgXoaSach = itemView.findViewById(R.id.imgXoaSach);
            imgSuaSach = itemView.findViewById(R.id.imgSuaSach);
        }
    }

    private void updateDia(Sach s, int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view1 = inflater.inflate(R.layout.dialog_sua_sach, null);

        LoaiSachDAO loaiDao = new LoaiSachDAO(context);
        builder.setView(view1);
        builder.setCancelable(false);


        AppCompatEditText edTs = view1.findViewById(R.id.edTenSachEdit);
        AppCompatEditText edGiat = view1.findViewById(R.id.edGiaTienEdit);
        Spinner spnLoaiSach = view1.findViewById(R.id.spnLoaiSachEdit);

        edTs.setText(String.valueOf(list.get(id).getTenSach()));
        edGiat.setText(String.valueOf(list.get(id).getGiaSach()));

        ArrayList<LoaiSach> lsList = loaiDao.getAllData();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, loaiDao.name());
        spnLoaiSach.setAdapter(adapter1);



        int spIndex = 0;
        for (LoaiSach ls : lsList) {
            if (ls.getTenLs().equals(s.getTenLoai())) {
                spnLoaiSach.setSelection(spIndex);
                break;
            }
            spIndex++;
        }
        spnLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String i = lsList.get(position).getTenLs();
                s.setTenLoai(i);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SachDAO sachDao = new SachDAO(context);
                String ten = edTs.getText().toString();
                String giatien = edGiat.getText().toString();

                if (ten.trim().equals("") || giatien.trim().equals("")) {
                    Toast.makeText(context, "ko dc de trong", Toast.LENGTH_SHORT).show();
                }
                else {
                    s.setTenSach(edTs.getText().toString());
                    s.setGiaSach(Integer.parseInt(edGiat.getText().toString()));
                if (sachDao.update(s) > 0) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                    notifyDataSetChanged();
                    list = sachDao.getAllData();
                    setData(list);
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                }
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
}
