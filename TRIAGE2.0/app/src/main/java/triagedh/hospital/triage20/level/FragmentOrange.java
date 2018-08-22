package triagedh.hospital.triage20.level;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import triagedh.hospital.triage20.R;


public class FragmentOrange extends Fragment {
    private Button back;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orange, container, false);
        back = (Button) view.findViewById(R.id.btnBackOrange);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nuevoFragment = new FragmentClasificationLevel();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor_fragment, nuevoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

}
