package hoanhqph30066.fpoly.du_an_mau.Fragment.Them_Thanh_Vien;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.DAO.ThuThuDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.ThuThu;
import hoanhqph30066.fpoly.du_an_mau.R;

public class AdapterDanhSach_Them_tv extends RecyclerView.Adapter<AdapterDanhSach_Them_tv.MyViewHolder> {
    private ArrayList<ThuThu> list;
    private Context context;
    private ThuThuDAO thuThuDAO;

    public AdapterDanhSach_Them_tv(Context context) {
        this.context = context;
        thuThuDAO = new ThuThuDAO(context);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<ThuThu> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.item_ds_tk,parent,false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ThuThu tt = list.get(position);
        holder.tv_loaitk.setText(tt.getLoaiTaiKhoan());
        holder.tv_hoten.setText(tt.getTenTt());
        holder.tvtentk.setText(tt.getMaTt());
        holder.tv_matkhau.setText(tt.getMK());

        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        // khai các view trong file layout_item
        private ImageView xoa , sua;
        private TextView tvtentk , tv_hoten, tv_loaitk, tv_matkhau;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_loaitk = itemView.findViewById(R.id.tv_loaitk_item_tk);
            tvtentk = itemView.findViewById(R.id.tv_tentk_item_tk);
            tv_hoten = itemView.findViewById(R.id.tv_hoten_item_tk);
            tv_matkhau = itemView.findViewById(R.id.tv_matkhautk_item_tk);

            xoa = itemView.findViewById(R.id.imgXoa_item_tk);
            sua = itemView.findViewById(R.id.imgSua_item_tk);
        }
    }
}
