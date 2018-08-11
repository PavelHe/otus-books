package com.github.pavelhe.shell;

import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import com.github.pavelhe.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.shell.standard.*;

@ShellComponent
public class CommentCommands {

    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentService;
    @Autowired
    private MessageSourceWrapper messageSource;

    @ShellMethod("Count of comments")
    public String commentsCount() {
        return messageSource.getMessage("comments.count", commentService.count());
    }

    @ShellMethod("All comments")
    public String allComments() {
        return commentService.getAll().toString();
    }

    @ShellMethod("Get comment by ID")
    public String getCommentById(@ShellOption Long id) {
        return commentService.getById(id).toString();
    }

    @ShellMethod("Get comment by name of comment author")
    public String getCommentByName(@ShellOption String authorOfComment) {
        return commentService.getByName(authorOfComment).toString();
    }

    @ShellMethod("Add comment to book")
    public String addComment(@ShellOption String nameOfAuthor, @ShellOption String text, @ShellOption Long bookId) {
        Comment comment = new Comment(nameOfAuthor, text);
        commentService.create(comment, bookId);
        return messageSource.getMessage("add.comment", bookId);
    }

    @ShellMethod("Remove comment by ID")
    public String removeCommentById(@ShellOption Long id) {
        commentService.remove(id);
        return messageSource.getMessage("remove.comment");
    }

    @ShellMethod("Update text comment")
    public String updateTextComment(@ShellOption Long commentId, @ShellOption String newText) {
        Comment comment = commentService.getById(commentId);
        comment.setText(newText);
        commentService.update(comment);
        return messageSource.getMessage("update.text.comment");
    }

    @ShellMethod("Update name of author comment")
    public String updateAuthorNameComment(@ShellOption Long commentId, @ShellOption String newName) {
        Comment comment = commentService.getById(commentId);
        comment.setName(newName);
        commentService.update(comment);
        return messageSource.getMessage("update.author.comment");
    }

}
