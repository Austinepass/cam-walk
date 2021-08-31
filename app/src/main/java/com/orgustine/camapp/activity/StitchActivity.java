package com.orgustine.camapp.activity;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.orgustine.camapp.MyApp;
import com.orgustine.camapp.R;
import com.arashivision.sdkmedia.player.image.ImageParamsBuilder;
import com.arashivision.sdkmedia.player.image.InstaImagePlayerView;
import com.arashivision.sdkmedia.stitch.StitchUtils;
import com.arashivision.sdkmedia.work.WorkWrapper;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public class StitchActivity extends AppCompatActivity {

    public static final String COPY_DIR = MyApp.getInstance().getCacheDir() + "/hdr_source";
    private static final String[] URLS = new String[]{
            COPY_DIR + "/img1.jpg",
            COPY_DIR + "/img2.jpg",
            COPY_DIR + "/img3.jpg"
    };
    private WorkWrapper mWorkWrapper = new WorkWrapper(URLS);
    private String mOutputPath = MyApp.getInstance().getFilesDir() + "/hdr_generate/generate.jpg";
    private StitchTask mStitchTask;

    private InstaImagePlayerView mImagePlayerView;

    private LifecycleRegistry mLifecycleRegistry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stitch);

        mLifecycleRegistry = new LifecycleRegistry((LifecycleOwner) this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);

        setTitle(R.string.stitch_toolbar_title);
        bindViews();

        // 初始显示无HDR效果的图片
        // Initial display image effect without HDR stitching
        showGenerateResult(false);
    }

    @NotNull
    public Lifecycle getArchLifecycle() {
        return mLifecycleRegistry;
    }

    private void bindViews() {
        mImagePlayerView = findViewById(R.id.player_image);
        mImagePlayerView.setLifecycle(getArchLifecycle());

        RadioGroup rgStitchMode = findViewById(R.id.rg_stitch_mode);
        rgStitchMode.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_none) {
                showGenerateResult(false);
            } else if (checkedId == R.id.rb_hdr) {
                startGenerate();
            }
        });
    }

    private void startGenerate() {
        mStitchTask = new StitchTask(this);
        mStitchTask.execute();
    }

    private void showGenerateResult(boolean successful) {
        ImageParamsBuilder builder = new ImageParamsBuilder()
                // 如果HDR合成成功，则将其文件路径设置为播放参数
                // If HDR stitching is successful then set it as the playback proxy
                .setUrlForPlay(successful ? mOutputPath : null);
        mImagePlayerView.prepare(mWorkWrapper, builder);
        mImagePlayerView.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mStitchTask != null) {
            mStitchTask.cancel(true);
        }
        mImagePlayerView.destroy();
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
    }

    private static class StitchTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<StitchActivity> activityWeakReference;
        private MaterialDialog mDialog;

        private StitchTask(StitchActivity activity) {
            super();
            activityWeakReference = new WeakReference<>(activity);
            mDialog = new MaterialDialog.Builder(activity)
                    .progress(true, 100)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .build();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            StitchActivity stitchActivity = activityWeakReference.get();
            if (stitchActivity != null && !isCancelled()) {
                // Start HDR stitching
                return StitchUtils.generateHDR(stitchActivity.mWorkWrapper, stitchActivity.mOutputPath);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            StitchActivity stitchActivity = activityWeakReference.get();
            if (stitchActivity != null && !isCancelled()) {
                stitchActivity.showGenerateResult(result);
            }
            mDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

}
