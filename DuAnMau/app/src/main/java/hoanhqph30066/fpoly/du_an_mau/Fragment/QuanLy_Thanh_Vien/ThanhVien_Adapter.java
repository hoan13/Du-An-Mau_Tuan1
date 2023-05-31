package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Thanh_Vien;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import hoanhqph30066.fpoly.du_an_mau.DAO.ThanhVienDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.ThanhVien;
import hoanhqph30066.fpoly.du_an_mau.R;

public class ThanhVien_Adapter extends RecyclerView.Adapter<ThanhVien_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVien_Adapter(Context context) {
        this.context = context;
        thanhVienDAO = new ThanhVienDAO(context);
    }

    public void setData(ArrayList<ThanhVien> list) {
        this.list = list;
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_thanh_vien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        if (thanhVien == null) {
            return;
        }
        holder.tvHoTen.setText(thanhVien.getTenTv());
        holder.tvNgaySinh.setText(thanhVien.getNamsinh());

        holder.imgXoaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Thành viên :" +thanhVien.getTenTv());
                builder.setTitle("Bạn có chắc muốn xóa ");
                builder.setCancelable(false);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = thanhVienDAO.XoaThanhVien(list.get(holder.getAdapterPosition()).getMaTv());
                        switch (check) {
                            case 1:
                                Toast.makeText(context, "Xoá thành công!", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = thanhVienDAO.getDanhSachThanhVien();
                                notifyItemRemoved(holder.getAdapterPosition());
                                break;
                            case 0:
                                Toast.makeText(context, "Xoá thất bại!", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Thành viên có phiếu mượn, không được xoá", Toast.LENGTH_SHORT).show();
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

        holder.imgSuaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.dialog_sua_thanh_vien, null);
                builder.setView(view);

                AppCompatEditText edHoTenTV = view.findViewById(R.id.edHoTenTVEdit);
                AppCompatEditText edNamSinhTV = view.findViewById(R.id.edNamSinhTVEdit);

                edHoTenTV.setText(list.get(holder.getAdapterPosition()).getTenTv());
                edNamSinhTV.setText(list.get(holder.getAdapterPosition()).getNamsinh());

                edNamSinhTV.setFocusable(false);
                edNamSinhTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowDateDialog(edNamSinhTV);
                    }
                });
                builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String hoTenTV = Objects.requireNonNull(edHoTenTV.getText()).toString().trim();
                        String namSinhTV = Objects.requireNonNull(edNamSinhTV.getText()).toString().trim();
                        boolean check = thanhVienDAO.SuaThanhVien(list.get(holder.getAdapterPosition()).getMaTv(), hoTenTV, namSinhTV);
                        if (check) {
                            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = thanhVienDAO.getDanhSachThanhVien();
                            notifyItemChanged(holder.getAdapterPosition());
                        } else {
                            Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
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
            private void ShowDateDialog(EditText ns) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context,
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
                                ns.setText(ngay  + "/" +thang+ "/" +year);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();

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
        TextView tvHoTen, tvNgaySinh;
        ImageView imgSuaTV, imgXoaTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoTen = itemView.findViewById(R.id.tvHoTenTV);
            tvNgaySinh = itemView.findViewById(R.id.tvNgaySinhTV);
            imgSuaTV = itemView.findViewById(R.id.imgSuaTV);
            imgXoaTV = itemView.findViewById(R.id.imgXoaTV);
        }
    }
}
