package hoanhqph30066.fpoly.du_an_mau.Fragment.Doi_Mat_Khau;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hoanhqph30066.fpoly.du_an_mau.R;

public class Doi_MatKhau_Fragment extends Fragment {
    public Doi_MatKhau_Fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_matkhau,container,false);
        return view;
    }
}
