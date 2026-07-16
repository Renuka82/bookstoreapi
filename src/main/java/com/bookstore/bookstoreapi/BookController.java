package com.bookstore.bookstoreapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Add Book
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Get All Books with Pagination & Sorting
    @GetMapping
    public Page<Book> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        return bookRepository.findAll(
                PageRequest.of(page, size, Sort.by(sortBy))
        );
    }

    // Get Book By Id
    @GetMapping("/id/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    // Update Book
    @PutMapping("/id/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book newBook) {

        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            book.setTitle(newBook.getTitle());
            book.setAuthor(newBook.getAuthor());
            book.setPrice(newBook.getPrice());
            book.setCategory(newBook.getCategory());

            return bookRepository.save(book);
        }

        return null;
    }

    // Delete Book
    @DeleteMapping("/id/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "Book Deleted Successfully";
    }

    // Search Book By Title
    @GetMapping("/search")
    public List<Book> searchBook(@RequestParam String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}