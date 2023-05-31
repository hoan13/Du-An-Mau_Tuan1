package hoanhqph30066.fpoly.du_an_mau.Fragment.QuanLy_Loai_Sach;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Objects;

import hoanhqph30066.fpoly.du_an_mau.DAO.LoaiSachDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.LoaiSach;
import hoanhqph30066.fpoly.du_an_mau.R;

public class QuanLy_LoaiSach_Fragment extends Fragment {
    FloatingActionButton flbt;
    RecyclerView recyclerView;
    ArrayList<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;
    LoaiSach_Adapter loaiSach_adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanly_loaisach,container,false);

        flbt = view.findViewById(R.id.flbt_loaisach);
        recyclerView = view.findViewById(R.id.recycciew_ql_loaisach);
        list = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(requireContext());
        loaiSach_adapter = new LoaiSach_Adapter(requireContext());
        capnhatdl();


        flbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                LayoutInflater inflaterFab = LayoutInflater.from(requireContext());
                View viewFlbt = inflaterFab.inflate(R.layout.dialog_loai_sach, null);

                builder.setView(viewFlbt);
                builder.setCancelable(false);

                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppCompatEditText edThemLoaiSach = viewFlbt.findViewById(R.id.edThemLoaiSach);
                        String themLoaiSach = Objects.requireNonNull(edThemLoaiSach.getText()).toString().trim();
                        if (loaiSachDAO.ThemLoaiSach(themLoaiSach)) {
                            Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            capnhatdl();
                        } else {
                            Toast.makeText(requireContext(), "Không thành công!", Toast.LENGTH_SHORT).show();
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

        return view;
    }

    private void capnhatdl() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        list = loaiSachDAO.getDanhSachLoaiSach();
        loaiSach_adapter.setData(list);
        recyclerView.setAdapter(loaiSach_adapter);
    }
}
