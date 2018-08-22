package triagedh.hospital.triage20.book;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import triagedh.hospital.triage20.R;


public class FragmentShowBookMTS extends Fragment {
    PDFView pdf;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_show_book_mts, container, false);
        pdf = (PDFView) view.findViewById(R.id.pdfView);
        pdf.fromAsset("MTS.pdf").load();
        return view;
    }

}
