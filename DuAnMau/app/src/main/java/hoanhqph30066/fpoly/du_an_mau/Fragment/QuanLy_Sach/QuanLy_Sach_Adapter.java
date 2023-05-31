package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Sach;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import hoanhqph30066.fpoly.du_an_mau.DAO.SachDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.R;

public class QuanLy_Sach_Adapter extends RecyclerView.Adapter<QuanLy_Sach_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Sach> list;
    private ArrayList<HashMap<String, Object>> listHM;
    private SachDAO sachDAO;

    public QuanLy_Sach_Adapter(Context context, ArrayList<HashMap<String, Object>> listHM, SachDAO sachDAO) {
        this.context = context;
        this.listHM = listHM;
        this.sachDAO = sachDAO;
    }

    public void setData(ArrayList<Sach> list) {
        this.list = list;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
                        int check = sachDAO.XoaSach(list.get(holder.getAdapterPosition()).getMaSach());
                        switch (check) {
                            case 1:
                                Toast.makeText(context, "Xoá thành công!", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = sachDAO.getDSSach();
                                notifyItemRemoved(holder.getAdapterPosition());
                                break;
                            case 0:
                                Toast.makeText(context, "Xoá thất bại!", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Sách có trong phiếu mượn, không xoá", Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_sua_sach, null);
                builder.setView(view);
                builder.setCancelable(false);

                AppCompatEditText edTenSach = view.findViewById(R.id.edTenSachEdit);
                AppCompatEditText edGiaTien = view.findViewById(R.id.edGiaTienEdit);
                Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSachEdit);
                SimpleAdapter simpleAdapter = new SimpleAdapter(context, listHM, android.R.layout.simple_list_item_1, new String[]{"TenLoai"}, new int[]{android.R.id.text1});
                spnLoaiSach.setAdapter(simpleAdapter);

                int index = 0;
                int position = -1;
                for (HashMap<String, Object> item : listHM) {
                    if ((int) item.get("MaLoai") == sach.getMaLoaiSach()) {
                        position = index;
                    }
                    index++;
                }

                spnLoaiSach.setSelection(position);
                edTenSach.setText(list.get(holder.getAdapterPosition()).getTenSach());
                edGiaTien.setText(String.valueOf(list.get(holder.getAdapterPosition()).getGiaSach()));

                builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tenSach = Objects.requireNonNull(edTenSach.getText()).toString().trim();
                        int giaThue = Integer.parseInt(Objects.requireNonNull(edGiaTien.getText()).toString().trim());
                        HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                        int maLoai = (int) hs.get("MaLoai");
                        boolean check = sachDAO.SuaSach(list.get(holder.getAdapterPosition()).getMaSach(), tenSach, giaThue, maLoai);
                        if (check) {
                            Toast.makeText(context, "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = sachDAO.getDSSach();
                            notifyItemChanged(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(context, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
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
}
