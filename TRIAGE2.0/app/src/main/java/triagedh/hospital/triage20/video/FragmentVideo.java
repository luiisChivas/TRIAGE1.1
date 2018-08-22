package triagedh.hospital.triage20.video;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;
import triagedh.hospital.triage20.R;


public class FragmentVideo extends Fragment implements MediaPlayer.OnCompletionListener {
    private View view;
    private String videoPath;
    private VideoView mVV;
    private MediaController mediaC;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);
        VideoView videoView = view.findViewById(R.id.videoView);
        String videoPath = "android.resource://" +getActivity().getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        return view;
    }


    private void media(){
        Uri uri = Uri.parse(videoPath);
        mVV.setVideoURI(uri);
        mVV.setMediaController(mediaC);
        mediaC.setAnchorView(mVV);
        mVV.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        getActivity().finish();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
