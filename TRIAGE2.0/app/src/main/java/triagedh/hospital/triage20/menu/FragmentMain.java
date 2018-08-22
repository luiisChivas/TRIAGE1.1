package triagedh.hospital.triage20.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import triagedh.hospital.triage20.R;
import triagedh.hospital.triage20.socialNetworks.FacebookActivity;
import triagedh.hospital.triage20.socialNetworks.YoutubeActivity;


public class FragmentMain extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        Button btn = (Button) view.findViewById(R.id.btnFb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), FacebookActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        Button btn1 = (Button) view.findViewById(R.id.btnyoutube);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), YoutubeActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }
}
