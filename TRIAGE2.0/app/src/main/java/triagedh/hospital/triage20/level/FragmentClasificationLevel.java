package triagedh.hospital.triage20.level;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import triagedh.hospital.triage20.R;


public class FragmentClasificationLevel extends Fragment {

    private Button btnRed;
    private Button btnOrange;
    private Button btnYellow;
    private Button btnGreen;
    private Button btnBlue;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_clasification_level, container, false);

        btnRed = (Button) view.findViewById(R.id.btnPriorityVeryHigh);
        btnOrange = (Button) view.findViewById(R.id.btnPriorityHigh);
        btnYellow= (Button) view.findViewById(R.id.btnPriorityMedium);
        btnGreen = (Button) view.findViewById(R.id.btnPriorityLow);
        btnBlue= (Button) view.findViewById(R.id.btnPriorityVeryLow);


        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragment = new FragmentRed();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor_fragment, nuevoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragment = new FragmentOrange();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor_fragment, nuevoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragment = new FragmentYellow();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor_fragment, nuevoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragment = new FragmentGreen();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor_fragment, nuevoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragment = new FragmentBlue();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor_fragment, nuevoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

}
