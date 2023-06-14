package hoanhqph30066.fpoly.du_an_mau.Fragment.Sach_Top_Muon;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.R;

public class AdapterTop10 extends RecyclerView.Adapter<AdapterTop10.ViewHolder> {
    private ArrayList<Sach> toplist;
    private Context context;

    public AdapterTop10(ArrayList<Sach> toplist, Context context) {
        this.toplist = toplist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top10, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTenSach.setText(toplist.get(position).getTenSach());
        holder.tvSoLuong.setText(String.valueOf(toplist.get(position).getSoluongSachMuon()));
    }

    @Override
    public int getItemCount() {
        if (toplist != null)
            return toplist.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenSach, tvSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tv_item_top10_tensach);
            tvSoLuong = itemView.findViewById(R.id.tv_item_top10_giatien);
        }
    }
}
