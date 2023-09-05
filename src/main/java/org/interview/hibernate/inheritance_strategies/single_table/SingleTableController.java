package org.interview.hibernate.inheritance_strategies.single_table;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/singletable")
public class SingleTableController {

    private final EntityManager em;

    public SingleTableController(EntityManager entityManager) {
        this.em = entityManager;
    }

    @GetMapping("/allblogs")
    public ResponseEntity<List<BlogPostST>> findAll() {
        List<BlogPostST> blogs = em.createQuery("SELECT b FROM BlogPostST b", BlogPostST.class).getResultList();
        return ResponseEntity.status(blogs.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .header("BlogPostST", "BlogPostST")
                .body(blogs);
    }

    @GetMapping("/allBooks")
    public ResponseEntity<List<BookST>> findAll() {
        List<BookST> books = em.createQuery("SELECT b FROM BookST b", BookST.class).getResultList();
        return ResponseEntity.status(books.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .header("BookST", "BookST")
                .body(books);
    }
}
