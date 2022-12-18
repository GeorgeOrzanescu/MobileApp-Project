package eu.ase.ro.livescoringapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.livescoringapp.AddCommentActivity;
import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.adapters.CommentAdapter;
import eu.ase.ro.livescoringapp.async.CallbackFunction;
import eu.ase.ro.livescoringapp.classes.CategoryWithComments;
import eu.ase.ro.livescoringapp.classes.Comment;
import eu.ase.ro.livescoringapp.database.CategoryWithCommentsService;
import eu.ase.ro.livescoringapp.database.CommentService;

public class ChatFragment extends Fragment {

    private static final String MESSAGE_KEY = "message";

    FloatingActionButton addCommentButton;
    private RadioGroup chatCategory;
    private ListView listViewComments; // ListView for showing the comments
    private List<Comment> comments = new ArrayList<>();
    ActivityResultLauncher<Intent> addCommentLauncher;

    private CommentService commentService;
    private CategoryWithCommentsService categoryWithCommentsService;

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
        addCommentButton.setOnClickListener(getAddCommentEvent());
        listViewComments = view.findViewById(R.id.list_view_chat);
        chatCategory = view.findViewById(R.id.radio_group_chat);

        // showing a delete chat message popup
        listViewComments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // use this to get the comment from the List
                int commentIndex = i;
                Comment selectedComment = comments.get(commentIndex);

                new AlertDialog.Builder(getContext()).setTitle("Are you sure?")
                        .setIcon(R.drawable.ic_delete)
                        .setMessage("Do you want to remove this message")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                commentService.delete(selectedComment,deleteCommentCallback(commentIndex));
                            }
                        })
                        .show();

                return false;
            };
        });

        chatCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(radioGroup.getCheckedRadioButtonId() == R.id.radio_btn_chat_football) {
                    Log.i("radio group id", "football");
                    categoryWithCommentsService.getAll(getAllCategoryCommentsCallback("football"));
                }
                else{
                    Log.i("radio group id", "basketball");
                    categoryWithCommentsService.getAll(getAllCategoryCommentsCallback("basketball"));
                }
            }
        });

        addCommentLauncher = registerAddCommentLauncher();
        commentService = new CommentService(getContext().getApplicationContext());
        categoryWithCommentsService = new CategoryWithCommentsService(getContext().getApplicationContext());

        //get all comments from DB on load
        //commentService.getAll(getAllCommentsCallback());
        categoryWithCommentsService.getAll(getAllCategoryCommentsCallback("football"));

        // we need an ArrayAdapter or a CustomAdapter for the ListView
        CommentAdapter commentAdapter =  new CommentAdapter(getContext().getApplicationContext(),R.layout.list_view_chat_design,comments,getLayoutInflater());
        listViewComments.setAdapter(commentAdapter);
        return view;
    }

    private CallbackFunction<List<Comment>> getAllCommentsCallback() {
        return new CallbackFunction<List<Comment>>() {
            @Override
            public void runResultOnUiThread(List<Comment> result) {
                    if(result != null) {
                        comments.clear();
                        comments.addAll(result);
                        notifyAdapter();
                    }
            }
        };
    }

    private CallbackFunction<List<CategoryWithComments>> getAllCategoryCommentsCallback(String  chatCategory) {
        return new CallbackFunction<List<CategoryWithComments>>() {
            @Override
            public void runResultOnUiThread(List<CategoryWithComments> result) {
                if(result != null) {
                    if(chatCategory == "football"){
                        comments.clear();
                        comments.addAll(result.get(0).commentList);
                        notifyAdapter();
                    }
                    else {
                        comments.clear();
                        comments.addAll(result.get(1).commentList);
                        notifyAdapter();
                    }
                }
            }
        };
    }

    // this is being called to notify the adapter when changes occur
    public void notifyAdapter() {
        //!! because CommendAdapter inherits from ArrayAdapter there is no problem using it here
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) listViewComments.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener getAddCommentEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), AddCommentActivity.class);
                addCommentLauncher.launch(intent);
            }
        };
    }

    private ActivityResultLauncher<Intent> registerAddCommentLauncher() {

        ActivityResultCallback<ActivityResult> callback = getAddCommentResultCallback();
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    private ActivityResultCallback<ActivityResult> getAddCommentResultCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                // getParcelableExtra calls the static Creator function of the Comment class
                Comment comment = result.getData().getParcelableExtra(AddCommentActivity.MESSAGE_KEY);

                // insert into DB
                commentService.insert(comment,insertCommentCallback());
            }
        };
    }

    private CallbackFunction<Comment> insertCommentCallback() {
        return new CallbackFunction<Comment>() {
            @Override
            public void runResultOnUiThread(Comment result) {
                if(result != null) {
                    comments.add(result);
                    Log.i("Chat Fragment received", comments.toString());
                    notifyAdapter();
                }
            }
        };
    }

    private CallbackFunction<Integer> deleteCommentCallback(int commentIndex) {
        return new CallbackFunction<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if(result == 1) {
                    Log.i("Comment removed" , "1 comment removed");
                    comments.remove(commentIndex);
                    notifyAdapter();
                }
            }
        };
    }
}