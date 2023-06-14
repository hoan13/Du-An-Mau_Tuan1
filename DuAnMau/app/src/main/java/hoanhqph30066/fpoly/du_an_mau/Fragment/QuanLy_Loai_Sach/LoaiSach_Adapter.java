package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Loai_Sach;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import hoanhqph30066.fpoly.du_an_mau.DAO.LoaiSachDAO;
import hoanhqph30066.fpoly.du_an_mau.DAO.ThuThuDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.LoaiSach;
import hoanhqph30066.fpoly.du_an_mau.Model.ThuThu;
import hoanhqph30066.fpoly.du_an_mau.R;

public class LoaiSach_Adapter extends RecyclerView.Adapter<LoaiSach_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSach> list;
    private LoaiSachDAO loaiSachDAO;


    // test
    ThuThu thuthulist ;

    // het test
    public LoaiSach_Adapter(Context context) {
        this.context = context;
        loaiSachDAO = new LoaiSachDAO(context);
    }

    public void setData(ArrayList<LoaiSach> list) {
        this.list = list;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_loai_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //test
        SharedPreferences sharedPreferences = context.getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String maThuThu = sharedPreferences.getString("MaTT", "");

        ThuThuDAO thuThuDAO = new ThuThuDAO(context,thuthulist);
        thuthulist = thuThuDAO.getID(maThuThu);

//        ThuThu thuThu =thuThuDAO.getID(maThuThu);

        if(thuthulist.getLoaiTaiKhoan().equals("thành viên")){
            holder.imgSua.setVisibility(View.INVISIBLE);
            holder.imgXoa.setVisibility(View.INVISIBLE);
        }
        //het
        LoaiSach loaiSach = list.get(position);
        if (loaiSach == null) {
            return;
        }
        holder.tvTenLoaiSach.setText(loaiSach.getTenLs());
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Loại Sách :  " +loaiSach.getTenLs());
                builder.setTitle("Bạn có chắc muốn xóa ");
                builder.setCancelable(false);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaiSachDAO loaiSachDAO1 = new LoaiSachDAO(context);
                        int check = loaiSachDAO1.XoaLoaiSachTheoTen(list.get(holder.getAdapterPosition()).getTenLs());
                        switch (check) {
                            case 1:
                                list.clear();
                                list = loaiSachDAO.getDanhSachLoaiSach();
                                notifyItemRemoved(holder.getAdapterPosition());
                                Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Có sách trong thể loại này, không thể xoá", Toast.LENGTH_SHORT).show();
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

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_sua_loaisach, null);
                builder.setView(view);
                builder.setCancelable(false);

                AppCompatEditText edSualoaisach = view.findViewById(R.id.edSuaLoaiSach);
                edSualoaisach.setText(list.get(holder.getAdapterPosition()).getTenLs());

                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaiSach editLoaiSach = list.get(holder.getAdapterPosition());
                        String suaLoaiSach = Objects.requireNonNull(edSualoaisach.getText()).toString().trim();
                        editLoaiSach.setTenLs(suaLoaiSach);
                        boolean check = loaiSachDAO.SuaLoaiSach(editLoaiSach);
                        if (check) {
                            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            notifyItemChanged(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenLoaiSach;
        ImageView imgXoa, imgSua;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenLoaiSach = itemView.findViewById(R.id.tvTenLoaiSach);
            imgXoa = itemView.findViewById(R.id.imgXoa);
            imgSua = itemView.findViewById(R.id.imgSua);
        }
    }


}
