package com.shim.user.shimapplication.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.shim.user.shimapplication.R;
import com.shim.user.shimapplication.data.Feedback;
import com.shim.user.shimapplication.data.FeedbackResponse;
import com.shim.user.shimapplication.data.handler.FeedbackHandler;
import com.shim.user.shimapplication.data.repository.EtcRepo;

import static com.shim.user.shimapplication.activity.MainActivity.userID;

public class FeedbackActivity extends AppCompatActivity {
    private EditText editTitle;
    private EditText editContact;
    private EditText editContents;
    private TextView feedbackSendButton;

    private Feedback feedback;
    EtcRepo etcRepo;

    //예외처리 및 디자인 추가 필요!! (추후 작업 예정)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        editTitle = (EditText)findViewById(R.id.text_feedback_title);
        editContact = (EditText)findViewById(R.id.text_feedback_contact);
        editContents = (EditText)findViewById(R.id.text_feedback_content);
        feedbackSendButton = (TextView)findViewById(R.id.button_feedback_send);


        feedback = new Feedback();
        FeedbackHandler feedbackHandler = new FeedbackHandler() {
            @Override
            public void onSuccessSendFeedback(FeedbackResponse response) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        };
        etcRepo = new EtcRepo(feedbackHandler);

        feedbackSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTitle.getText()!=null&&
                        editContact.getText()!=null&&
                        editContents.getText()!=null)
                feedback.setFeedback_userid(userID);
                feedback.setFeedback_title(editTitle.getText().toString());
                feedback.setFeedback_contact(editContact.getText().toString());
                feedback.setFeedback_contents(editContents.getText().toString());

                etcRepo.sendFeedback(feedback);

                Toast.makeText(getApplicationContext(), "의견이 전송되었습니다!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
