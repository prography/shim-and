package com.shim.user.shimapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        editTitle = (EditText)findViewById(R.id.feedback_title);
        editContact = (EditText)findViewById(R.id.feedback_contact);
        editContents = (EditText)findViewById(R.id.feedback_contents);
        feedbackSendButton = (TextView)findViewById(R.id.feedback_send_button);


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
            }
        });
    }
}
