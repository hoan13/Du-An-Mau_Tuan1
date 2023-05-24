package hoanhqph30066.fpoly.du_an_mau.Fragment.Sach_Muon_Nhieu_Nhat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hoanhqph30066.fpoly.du_an_mau.R;

public class SachMuon_NhieuNhat_Fragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach_muon_nhieu_nhat,container,false);
        return view;
    }
}
