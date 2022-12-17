package eu.ase.ro.livescoringapp.classes;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithComments {
    @Embedded public CommentCategory commentCategory;
    @Relation(
            parentColumn = "comment_category_id",
            entityColumn = "category_id"
    )
    public List<Comment> commentList;
}
