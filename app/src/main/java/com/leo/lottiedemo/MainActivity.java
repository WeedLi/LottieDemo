package com.leo.lottiedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.airbnb.lottie.LottieTask;
import com.leo.lottiedemo.widgets.MyLottieAnimationView;
import com.litesuits.common.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private MyLottieAnimationView mAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAnimationView = findViewById(R.id.main_animation_view);

        LottieTask.EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final File file = new File(getCacheDir() + "logo.json");
                    final InputStream open = getAssets().open("Lottie Logo.json");
                    byte[] bytes = new byte[0];
                    bytes = new byte[open.available()];
                    open.read(bytes);
                    String str = new String(bytes);
                    FileUtils.writeStringToFile(file, str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadFromAssets(View view) {
        mAnimationView.removeAllLottieOnCompositionLoadedListener();
        mAnimationView.setAnimation("Lottie Logo.json");
        mAnimationView.playAnimation();
    }

    public void loadFromRaw(View view) {
        mAnimationView.removeAllLottieOnCompositionLoadedListener();
        mAnimationView.setAnimation(R.raw.football);
        mAnimationView.playAnimation();
    }

    public void loadFromFile(View view) {
        mAnimationView.removeAllLottieOnCompositionLoadedListener();
        File file = new File(getCacheDir() + "logo.json");
        mAnimationView.setAnimationFromLocal(file, "logo");
        mAnimationView.playAnimation();
    }

    public void loadFromUrl(View view) {
        mAnimationView.removeAllLottieOnCompositionLoadedListener();
        mAnimationView.setAnimationFromUrl("https://assets7.lottiefiles.com/private_files/lf30_rBgRS1.json");
        mAnimationView.playAnimation();
    }

    public void setDynamicImage(View view) {
        mAnimationView.removeAllLottieOnCompositionLoadedListener();
        mAnimationView.setAnimation("lottie/match_success.json");
        mAnimationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(LottieComposition composition) {
                String imageUrl = "https://ae01.alicdn.com/kf/U75499bbca2264e6badd85f7695dd9ab5H.jpg";
                mAnimationView.setDynamicImage("image_0", imageUrl);
                mAnimationView.setDynamicImage("image_1", imageUrl);
                mAnimationView.playAnimation();
            }
        });
    }


}
