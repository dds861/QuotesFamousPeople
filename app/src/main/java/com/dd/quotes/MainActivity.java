package com.dd.quotes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView mAuthor;
    private TextView mQuote;
    private Button mGenerateBtn;
    private ImageView mIvShare;
    private ImageView mIvCopyAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initView();
        connectDatabase();
        setLogoAndAppName();
    }

    //Logo and app name on action bar
    private void setLogoAndAppName() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    void makeAnimationOnView(Techniques techniques, int duration, int resourceId) {
        YoYo.with(techniques)
                .duration(duration)
                .playOn(findViewById(resourceId));

    }

    void connectDatabase() {

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

        int randomNumber = new Random().nextInt(databaseAccess.getCursorCount()) + 1;


//        makeAnimationOnView(Techniques.FadeOut, 700, R.id.quote);
//        makeAnimationOnView(Techniques.FadeOut, 700, R.id.author);
//        makeAnimationOnView(Techniques.FadeOut, 700, R.id.tv_dots);
        makeAnimationOnView(Techniques.FadeIn, 1000, R.id.quote);
        makeAnimationOnView(Techniques.FadeIn, 1000, R.id.author);
        makeAnimationOnView(Techniques.FadeIn, 1000, R.id.tv_dots);


        mAuthor.setText(databaseAccess.getList(randomNumber).get(1));

        mQuote.setText(databaseAccess.getList(randomNumber).get(0));

        int randomNumber1 = new Random().nextInt(6) + 1;
        switch (randomNumber1) {
            case 1:
                mQuote.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
                break;
            case 2:
                mQuote.setTypeface(Typeface.create("casual", Typeface.NORMAL));
                break;
            case 3:
                mQuote.setTypeface(Typeface.create("cursive", Typeface.NORMAL));
            case 4:
                mQuote.setTypeface(Typeface.create("monospace", Typeface.NORMAL));
                break;
            case 5:
                mQuote.setTypeface(Typeface.create("serif", Typeface.NORMAL));
                break;
            case 6:
                mQuote.setTypeface(Typeface.create("sans-serif-condensed-light", Typeface.NORMAL));
                break;
        }

    }


    private void initView() {
        mAuthor = findViewById(R.id.author);
        mQuote = findViewById(R.id.quote);


        mGenerateBtn = findViewById(R.id.btn_generate);
        mGenerateBtn.setOnClickListener(this);
        mIvShare = (ImageView) findViewById(R.id.ivShare);
        mIvShare.setOnClickListener(this);
        mIvCopyAll = (ImageView) findViewById(R.id.ivCopyAll);
        mIvCopyAll.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_generate:


                YoYo.with(Techniques.Pulse).duration(700).playOn(findViewById(R.id.btn_generate));
//                YoYo.with(Techniques.FadeIn).duration(350).playOn(findViewById(R.id.btn_generate));


                connectDatabase();
                break;
            case R.id.ivShare:// TODO 18/08/13
                YoYo.with(Techniques.FadeIn).duration(150).playOn(findViewById(R.id.ivShare));
                YoYo.with(Techniques.FadeIn).duration(350).playOn(findViewById(R.id.ivShare));

                //Share button click listener code:
                String shareBody = "\"" + mQuote.getText().toString() + "\" - " + mAuthor.getText().toString();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_using)));
                break;
            case R.id.ivCopyAll:// TODO 18/08/13

                YoYo.with(Techniques.FadeIn).duration(150).playOn(findViewById(R.id.ivCopyAll));
                YoYo.with(Techniques.FadeIn).duration(350).playOn(findViewById(R.id.ivCopyAll));

                // Gets a handle to the clipboard service.
                ClipboardManager clipboard2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                String textQuote = "\"" + mQuote.getText().toString() + "\" - " + mAuthor.getText().toString();

                // Creates a new text clip to put on the clipboard
                ClipData clip = ClipData.newPlainText("simple text", textQuote);

                // Set the clipboard's primary clip.
                clipboard2.setPrimaryClip(clip);
                Toast.makeText(this, R.string.TextCopied, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}
