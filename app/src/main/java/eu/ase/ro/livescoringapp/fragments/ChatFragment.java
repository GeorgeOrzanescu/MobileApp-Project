package eu.ase.ro.livescoringapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import eu.ase.ro.livescoringapp.AddCommentActivity;
import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.classes.Comment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    private static final String MESSAGE_KEY = "message";
    FloatingActionButton addCommentButton;

    private List<Comment> comments;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);
        addCommentButton = view.findViewById(R.id.button_add_comment);
        addCommentButton.setOnClickListener(this.startAddComment());
        return view;
    }

    private View.OnClickListener startAddComment() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddCommentActivity.class);
                startActivity(intent);
            }
        };
    }
}