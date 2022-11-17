package eu.ase.ro.livescoringapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.livescoringapp.AddCommentActivity;
import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.classes.Comment;

public class ChatFragment extends Fragment {

    private static final String MESSAGE_KEY = "message";

    FloatingActionButton addCommentButton;
    private ListView listViewComments; // ListView for showing the comments
    private List<Comment> comments = new ArrayList<>();
    ActivityResultLauncher<Intent> addExpenseLauncher;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(ArrayList<Comment> comments) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(MESSAGE_KEY,comments); // put in Parcelable file the array of comments
        fragment.setArguments(args); //set the bundle arg to fragment
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comments = getArguments().getParcelableArrayList(MESSAGE_KEY); // read list of Comments from the bundle in getInstance method using the key
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);
        addCommentButton = view.findViewById(R.id.button_add_comment);
        addCommentButton.setOnClickListener(getAddExpenseEvent());
        listViewComments = view.findViewById(R.id.list_view_chat);
        addExpenseLauncher = registerAddExpenseLauncher();
        // we need an ArrayAdapter for the ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<>(getContext().getApplicationContext(),
                android.R.layout.simple_list_item_1, comments);
        listViewComments.setAdapter(adapter);
        return view;
    }

    // this is being called to notify the adapter when changes occur
    public void notifyAdapter() {
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) listViewComments.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener getAddExpenseEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), AddCommentActivity.class);
                addExpenseLauncher.launch(intent);
            }
        };
    }

    private ActivityResultLauncher<Intent> registerAddExpenseLauncher() {

        ActivityResultCallback<ActivityResult> callback = getAddExpenseResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private ActivityResultCallback<ActivityResult> getAddExpenseResultCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                // getParcelableExtra calls the static Creator function of the Expense class
                Comment expense = result.getData().getParcelableExtra(AddCommentActivity.MESSAGE_KEY);
                comments.add(expense);
                Log.i("Chat Fragment received", comments.toString());
                // notify the adapter when ListView changes
                notifyAdapter();
            }
        };
    }
}