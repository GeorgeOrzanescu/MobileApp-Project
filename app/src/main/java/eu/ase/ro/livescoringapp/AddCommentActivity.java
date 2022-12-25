package eu.ase.ro.livescoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import eu.ase.ro.livescoringapp.classes.Comment;

public class AddCommentActivity extends AppCompatActivity {

    public static final String MESSAGE_KEY = "message_key";
    private TextInputEditText userName;
    private TextInputEditText message;
    private Spinner spnCategories;
    private Button btnAddComment;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        initComponents();
        intent = getIntent();
    }

    private void initComponents() {
        userName = findViewById(R.id.txt_inp_edit_chat_user_name);
        message = findViewById(R.id.txt_inp_edit_chat_comment);
        spnCategories = findViewById(R.id.spinner_add_comment_category);
        btnAddComment = findViewById(R.id.button_add_comment);

        btnAddComment.setOnClickListener(getAddCommentEvent());
    }

    private View.OnClickListener getAddCommentEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    Comment comment = createFromInputs();
                    intent.putExtra(MESSAGE_KEY, comment);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private Comment createFromInputs() {
        String userName = this.userName.getText().toString();
        String message = this.message.getText().toString();
        String category = spnCategories.getSelectedItem().toString();
        long category_id = spnCategories.getSelectedItemPosition();
        Log.i("Category id", String.valueOf(category_id));
        return new Comment(userName,message,category,category_id + 1);
    }

    private boolean isValid() {
        if (userName.getText() == null || userName.getText().toString().trim().length() < 4) {
            Toast.makeText(getApplicationContext(),"Please enter a valid username", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (message.getText() == null || message.getText().toString().trim().length() < 5) {
            Toast.makeText(getApplicationContext(), "Please enter a valid message" ,Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}