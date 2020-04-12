package eu.anifantakis.bakingapp.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import eu.anifantakis.bakingapp.R;
import eu.anifantakis.bakingapp.data.model.Step;
import eu.anifantakis.bakingapp.databinding.FragmentRecipeInfoDetailBinding;
import eu.anifantakis.bakingapp.utils.AppUtils;

public class RecipeInfoDetailFragment extends Fragment {

    private SimpleExoPlayer exoPlayer;
    private FragmentRecipeInfoDetailBinding binding;
    private Step step;
    private Bitmap stepThumbnail;

    public RecipeInfoDetailFragment(){}

    private final String POSITION_SELECTED = "selected position";
    private final String PLAY_WHEN_READY = "play when ready";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle!=null){
            step = bundle.getParcelable(AppUtils.EXTRAS_STEP);
        }

        if (savedInstanceState != null){
            position = savedInstanceState.getLong(POSITION_SELECTED , C.TIME_UNSET);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_info_detail, container, false);
        final View rootView = binding.getRoot();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.tvStepShortDescription.setText(step.getShortDescription());
        binding.tvStepDescription.setText(step.getDescription());

        if (!TextUtils.isEmpty(step.getVideoURL()))
            initializerPlayer(Uri.parse(step.getVideoURL()));
        else if (!step.getThumbnailURL().isEmpty()) {
            // if Thumbnail exists but no video exists (General case 2)
            binding.thumbnailView.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(step.getThumbnailURL())
                    .into(binding.thumbnailView);
        }
    }

    private void initializerPlayer(Uri mediaUri){
        if (exoPlayer==null){
            binding.playerView.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            binding.playerView.setPlayer(exoPlayer);

            binding.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            if (position>0)
                exoPlayer.seekTo(position);
            exoPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    private boolean playWhenReady = true;
    private long position = -1;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(POSITION_SELECTED , position);
        outState.putBoolean(PLAY_WHEN_READY , playWhenReady);
    }
    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null){
            position = exoPlayer.getCurrentPosition();
            playWhenReady = exoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

}
