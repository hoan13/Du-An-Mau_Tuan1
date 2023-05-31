package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Phieu_Muon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

    PhieuMuonDAO phieuMuonDAO;

    ArrayList<HashMap<String, Object>> listspinnertv;
    ArrayList<HashMap<String, Object>> listspinnertensach;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;

    public PhieuMuon_Adapter(Context context, ArrayList<PhieuMuon> list, PhieuMuonDAO phieuMuonDAO, ArrayList<HashMap<String, Object>> listspinnertv, ArrayList<HashMap<String, Object>> listspinnertensach) {
        this.context = context;
        this.list = list;
        this.phieuMuonDAO = phieuMuonDAO;
        this.listspinnertv = listspinnertv;
        this.listspinnertensach = listspinnertensach;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_phieu_muon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvTenThanhVien.setText(list.get(position).getTenThanhVien());
        holder.tvTenThuThu.setText(list.get(position).getTenThuThu());
        holder.tvTenSach.setText(list.get(position).getTenSach());
        holder.tvNgay.setText(list.get(position).getNgay());
        holder.tvTien.setText(String.valueOf(list.get(position).getTienThue()));

        

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

        AppCompatButton btnTraSach;

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
}
