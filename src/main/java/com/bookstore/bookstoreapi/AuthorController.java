package com.bookstore.bookstoreapi;

import com.bookstore.bookstoreapi.Author;
import com.bookstore.bookstoreapi.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return (ResponseEntity<Author>) authorRepository.findById(id)
                .map(author -> ResponseEntity.ok(author))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return (Author) authorRepository.save(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author newAuthor) {
        return authorRepository.findById(id)
                .map(existingAuthor -> {
                    existingAuthor.setName(newAuthor.getName());
                    Author updatedAuthor = (Author) authorRepository.save(existingAuthor);
                    return ResponseEntity.ok(updatedAuthor);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}