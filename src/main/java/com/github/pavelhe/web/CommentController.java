package com.github.pavelhe.web;


import com.github.pavelhe.model.*;
import com.github.pavelhe.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    private BookService bookService;
    private CommentService commentService;

    public CommentController(@Qualifier("commentServiceImpl") CommentService commentService,
                             @Qualifier("bookServiceImpl") BookService bookService) {
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @RequestMapping(value = "/comment/add", method = RequestMethod.POST)
    public String addComment(@RequestParam("bookId") Long bookId, @ModelAttribute Comment comment) {
        Book book = bookService.getById(bookId);
        book.getComments().add(comment);
        comment.setBook(bookService.getById(bookId));
        commentService.create(comment);
        return "redirect:/book/info/" + bookId;
    }

    @RequestMapping(value = "/comment/delete/{id}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable("id") Long id) {
        Comment comment = commentService.getById(id);
        Long bookId = comment.getBook().getId();
        commentService.remove(id);
        return "redirect:/book/info/" + bookId;
    }

}
