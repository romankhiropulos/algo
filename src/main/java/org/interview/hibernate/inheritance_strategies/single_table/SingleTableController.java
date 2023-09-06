package org.interview.hibernate.inheritance_strategies.single_table;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/singletable")
public class SingleTableController {

    private final EntityManager em;

    public SingleTableController(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Transactional
    @GetMapping("/createblog")
    public ResponseEntity<BlogPostST> createBlog() {
        BlogPostST newBlog = BlogPostST.builder()
                .url("urltoblog1.com")
                .build();
        newBlog.setTitle("Test blog");
        newBlog.setVersion(1);
        newBlog.setPublishingDate(new Date());
        newBlog.setAuthors(Set.of(AuthorST.builder()
                .firstName("Ivanov")
                .lastName("Petr")
                .version(1)
                .build()));
        em.persist(newBlog);
        em.flush();
        return new ResponseEntity<>(newBlog, HttpStatus.OK);
    }

    @GetMapping("/allblogs")
    public ResponseEntity<List<BlogPostST>> findAllBlogs() {
        List<BlogPostST> blogs = em.createQuery("SELECT b FROM BlogPostST b", BlogPostST.class).getResultList();
        return ResponseEntity.status(blogs.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .header("BlogPostST", "BlogPostST")
                .body(blogs);
    }

    @GetMapping("/allbooks")
    public ResponseEntity<List<BookST>> findAllBooks() {
        List<BookST> books = em.createQuery("SELECT b FROM BookST b", BookST.class).getResultList();
        return ResponseEntity.status(books.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .header("BookST", "BookST")
                .body(books);
    }
}
