package com.yamschikov.dima.justtodo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnJustShare, R.id.btnJustGrade, R.id.btnJusEmailFeedbackBtn})
    public void AboutAppButtonsOnClick(View view) {

        switch (view.getId()) {

            case R.id.btnJustShare:
                KLog.e("btnJustShare");

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_TEXT, "share");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;

            case R.id.btnJustGrade:
                KLog.e("btnJustGrade");

                String gradeUrl = getResources().getString(R.string.feedbackGradeUrl);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(gradeUrl));
                startActivity(i);

                break;

            case R.id.btnJusEmailFeedbackBtn:

                Intent feedbackIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + getResources().getString(R.string.feedback_title)
                        + "&to=" + getResources().getString(R.string.email_to_feedback));
                feedbackIntent.setData(data);
                startActivity(feedbackIntent);
                break;
        }
    }
}
