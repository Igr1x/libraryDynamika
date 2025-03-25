package ru.varnavskii.librarydynamika.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ru.varnavskii.librarydynamika.common.utils.PaginationUtils;
import ru.varnavskii.librarydynamika.controller.dto.BookIn;
import ru.varnavskii.librarydynamika.controller.mapping.BookMapper;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;
import ru.varnavskii.librarydynamika.service.BookService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
@RequiredArgsConstructor
public class BookController {

    private static final String BOOK_LIST_VIEW = "book/bookList";
    private static final String BOOK_DETAIL_VIEW = "book/bookDetail";
    private static final String BOOK_ATTRIBUTE_NAME = "book";
    private static final String BOOKS_ATTRIBUTE_NAME = "books";
    private static final String NEW_BOOK_ATTRIBUTE_NAME = "newBook";

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/list")
    public ModelAndView getAllBooks(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    ModelAndView modelAndView) {
        Page<BookEntity> books = bookService.getBooks(PageRequest.of(page, size));

        modelAndView.addObject(BOOKS_ATTRIBUTE_NAME, books.getContent());
        modelAndView.addObject(PaginationUtils.CURRENT_PAGE_ATTRIBUTE, page);
        modelAndView.addObject(PaginationUtils.TOTAL_PAGES_ATTRIBUTE, books.getTotalPages());
        modelAndView.addObject(NEW_BOOK_ATTRIBUTE_NAME, new BookIn(null, null, null));
        modelAndView.setViewName(BOOK_LIST_VIEW);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getBook(@PathVariable int id, ModelAndView modelAndView) {
        BookEntity book = bookService.getBookOrThrowException(id);

        modelAndView.setViewName(BOOK_DETAIL_VIEW);
        modelAndView.addObject(BOOK_ATTRIBUTE_NAME, bookMapper.toOut(book));
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(String.format("Book with id %d deleted", id));
    }

    @PostMapping("/delete")
    public ModelAndView deleteBooksByIds(@RequestParam List<Long> idsToDelete,
                                         @RequestParam int page,
                                         ModelAndView modelAndView) {
        bookService.deleteByIds(idsToDelete);

        modelAndView.setViewName("redirect:/book/list?page=" + page);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createBook(@Valid @ModelAttribute(NEW_BOOK_ATTRIBUTE_NAME) BookIn bookIn,
                                   BindingResult result,
                                   ModelAndView modelAndView) {
        if (result.hasErrors()) {
            modelAndView.setViewName(BOOK_LIST_VIEW);
            return modelAndView;
        }
        BookEntity bookEntity = bookMapper.toEntity(bookIn);
        bookEntity = bookService.createBook(bookEntity);

        modelAndView.setViewName("redirect:/book/" + bookEntity.getId());
        return modelAndView;
    }

    @PostMapping("/{id}")
    public ModelAndView updateBook(@PathVariable int id,
                                   @Valid @ModelAttribute BookIn bookIn,
                                   BindingResult result,
                                   ModelAndView modelAndView) {
        if (result.hasErrors()) {
            modelAndView.setViewName("redirect:/book/" + id);
            return modelAndView;
        }
        BookEntity bookEntity = bookMapper.toEntity(bookIn);
        bookEntity.setId((long) id);
        bookEntity = bookService.updateBook(bookEntity);

        modelAndView.setViewName(BOOK_DETAIL_VIEW);
        modelAndView.addObject(BOOK_ATTRIBUTE_NAME, bookMapper.toOut(bookEntity));
        return modelAndView;
    }

}
