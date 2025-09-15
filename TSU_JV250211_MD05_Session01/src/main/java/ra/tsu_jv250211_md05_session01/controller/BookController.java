package ra.tsu_jv250211_md05_session01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.tsu_jv250211_md05_session01.model.entity.Book;
import ra.tsu_jv250211_md05_session01.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String getBooks(Model model ,@RequestParam(required = false) String search) {

        model.addAttribute("books", bookService.getAllBooks(search));
        model.addAttribute("search", search);
        return "bookList"; // trả về trang bookList.jsp
    }

    @GetMapping("/add")
    public String showAddBookForm( Model model) {
        model.addAttribute("book", new Book());
        return "addBook"; // trả về trang addBook.jsp
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book  , RedirectAttributes redirectAttributes , Model model) {
        Book newBook = bookService.save(book);
        if (newBook != null) {
            redirectAttributes.addFlashAttribute("message", "Book added successfully");
            return "redirect:/books";
        }else {
            model.addAttribute("book",book);
            model.addAttribute("message", "Something went wrong");
            return "addBook";
        }

    }

    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "editBook"; // trả về trang editBook.jsp
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book, RedirectAttributes redirectAttributes, Model model) {
        Book newBook = bookService.update(book,id);
        if (newBook != null) {
            redirectAttributes.addFlashAttribute("message", "Book updated successfully");
            return "redirect:/books";
        }else {
            model.addAttribute("book",book);
            model.addAttribute("message", "Something went wrong");
            return "editBook";
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean rs = bookService.deleteById(id);
        if (rs){
            redirectAttributes.addFlashAttribute("message", "Book deleted successfully");
        }else {
            redirectAttributes.addFlashAttribute("message", "Something went wrong");
        }
        return "redirect:/books";
    }


}