package hoanhqph30066.fpoly.du_an_mau.Fragment.Sach_Top_Muon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hoanhqph30066.fpoly.du_an_mau.DAO.ThongKeDAO;
import hoanhqph30066.fpoly.du_an_mau.Model.Sach;
import hoanhqph30066.fpoly.du_an_mau.R;

public class SachMuon_NhieuNhat_Fragment extends Fragment {

    private AdapterTop10 adapterTop10;
    private ArrayList<Sach> toplist;
    private RecyclerView recyTop;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach_muon_nhieu_nhat,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyTop = view.findViewById(R.id.recyTop10);
        ThongKeDAO tkDao = new ThongKeDAO(getContext());

        toplist = tkDao.getTop10MuonSach();
        adapterTop10 = new AdapterTop10(toplist, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyTop.setLayoutManager(layoutManager);
        recyTop.setAdapter(adapterTop10);
    }
}
