package co.shimm.app.activity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.shimm.app.R;
import co.shimm.app.retrofit.ServiceGenerator;
import co.shimm.app.retrofit.ShimService;
import co.shimm.app.retrofit.request.FeedbackRequest;
import co.shimm.app.retrofit.response.BaseResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static co.shimm.app.activity.MainActivity.isCurrentEtc;
import static co.shimm.app.activity.MainActivity.userID;

public class FeedbackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        findViewById(R.id.button_feedback_send).setOnClickListener(v -> {
            Editable title = ((EditText) findViewById(R.id.text_feedback_title)).getText();
            Editable contact = ((EditText) findViewById(R.id.text_feedback_contact)).getText();
            Editable content = ((EditText) findViewById(R.id.text_feedback_content)).getText();
            if (title != null && contact != null && content != null) {
                ShimService service = ServiceGenerator.create();
                service.sendFeedback(new FeedbackRequest(userID, contact.toString(), title.toString(), content.toString()))
                        .enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(@NotNull Call<BaseResponse> call, @NotNull Response<BaseResponse> response) {
                                Toast.makeText(getApplicationContext(), "의견이 전송되었습니다!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(@NotNull Call<BaseResponse> call, @NotNull Throwable t) {
                                Toast.makeText(getApplicationContext(), "전송 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(getApplicationContext(), "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });


        isCurrentEtc = false;
    }
}
