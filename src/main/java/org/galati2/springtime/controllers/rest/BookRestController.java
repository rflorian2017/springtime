package org.galati2.springtime.controllers.rest;

import org.galati2.springtime.model.Book;
import org.galati2.springtime.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "subtitle", required = false) String subtitle
    ) {
        List<Book> books = bookService.getBooks(title, subtitle);
        return books;
    }
//
//    @GetMapping("/books/add")
//    public String addBook(Model model) {
//        model.addAttribute("book", new Book());
//        return "addBook";
//    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book result = bookService.getBook(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Book>(result, HttpStatus.OK);
    }

    @PostMapping("/books/{id}")
    public String changeBook(@PathVariable Long id, @ModelAttribute("book") Book book, Model model) {
        book.setBook_id(id);
        bookService.saveBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "booksList";
    }
    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        bookService.deleteBook(id);
        model.addAttribute("books", bookService.getBooks());
        return "booksList";
    }

    @PostMapping("/books")
    public String addBook(@ModelAttribute("book") Book book, Model model) {
        bookService.saveBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "booksList";
    }
}
