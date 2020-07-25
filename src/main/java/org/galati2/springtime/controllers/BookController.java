package org.galati2.springtime.controllers;

import org.galati2.springtime.model.Book;
import org.galati2.springtime.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/addbook")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addBook";
    }

    @PostMapping("/addbook")
    public String addBook(@ModelAttribute Book book) {
        bookService.saveBook(book);

        return "redirect:/allbooks";
    }

    @GetMapping("/allbooks")
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookService.getBooks());
        return "booksList";
    }

    // TODO: add Edit book controller + html
    @GetMapping("/book/{id}/edit")
    public String editBook(@PathVariable Integer id , Model model) {
//        model.addAttribute("books", bookService.getBooks());
        return "booksList";
    }



}
