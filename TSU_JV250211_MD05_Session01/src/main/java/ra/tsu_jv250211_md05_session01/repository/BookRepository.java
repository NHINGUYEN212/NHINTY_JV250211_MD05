package ra.tsu_jv250211_md05_session01.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ra.tsu_jv250211_md05_session01.model.entity.Book;

import java.util.List;

@Repository
public class BookRepository {
    @PersistenceContext
    private EntityManager em;



    public List<Book> getAllBooks(String search) {

        if (search == null || search.isEmpty()) {
            return em.createQuery("select b from Book b", Book.class)
                    .getResultList();
        }else {
            return em.createQuery("select b from Book b where b.title like :search or b.author like :search", Book.class)
                    .setParameter("search", "%" + search + "%")
                    .getResultList();
        }

    }

    public Book getBookById(long id) {
        return em.find(Book.class, id);
    }

    public Book saveBook(Book book) {
        return em.merge(book);
    }

    public boolean deleteBook(Book book) {
        try {
            em.remove(book);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}