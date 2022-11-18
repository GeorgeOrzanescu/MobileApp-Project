package eu.ase.ro.livescoringapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.classes.Comment;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context context;
    private int resource;
    private List<Comment> comments;
    private LayoutInflater layoutInflater;

    public CommentAdapter(@NonNull Context context, int resource, @NonNull List<Comment> objects, LayoutInflater layoutInflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.comments = objects;
        this.layoutInflater=layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource,parent,false);
        Comment comment = comments.get(position);
        if(comment == null) {
            return view;
        }

        addUserNameToChat(view,comment.getUserName());
        addMessageToChat(view,comment.getComment());
        addCategoryToChat(view,comment.getSportCategory());
        return view;

    }

    private void addCategoryToChat(View view, String sportCategory) {
        TextView category = view.findViewById(R.id.category_chat_design);
        //TODO add min validation
        category.setText(sportCategory);
    }

    private void addMessageToChat(View view, String comment) {
        TextView messageText = view.findViewById(R.id.message_chat_design);
        //TODO add min validation
        messageText.setText(comment);
    }

    private void addUserNameToChat(View view, String userName) {
        TextView userNameTxt = view.findViewById(R.id.userName_chat_design);
        //TODO add min validation
        userNameTxt.setText(userName);
    }
}
