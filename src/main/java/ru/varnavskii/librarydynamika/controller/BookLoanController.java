package ru.varnavskii.librarydynamika.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ru.varnavskii.librarydynamika.common.utils.BookFilterApplier;
import ru.varnavskii.librarydynamika.common.utils.ClientFilterApplier;
import ru.varnavskii.librarydynamika.common.utils.PaginationUtils;
import ru.varnavskii.librarydynamika.controller.dto.BookFilterIn;
import ru.varnavskii.librarydynamika.controller.dto.BookLoanIn;
import ru.varnavskii.librarydynamika.controller.dto.ClientFilterIn;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;
import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;
import ru.varnavskii.librarydynamika.service.BookLoanService;
import ru.varnavskii.librarydynamika.service.BookService;
import ru.varnavskii.librarydynamika.service.ClientService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/bookLoan")
@RequiredArgsConstructor
public class BookLoanController {

    private static final String BOOK_LOANS_ATTRIBUTE = "bookLoans";
    private static final String NEW_BOOK_LOAN_ATTRIBUTE = "newBookLoan";
    private static final String BOOK_LOAN_LIST_VIEW = "bookLoan/bookLoanList";
    private static final String BOOK_LOAN_CREATE_FORM = "bookLoan/bookLoanCreateRecord";

    private final BookLoanService bookLoanService;
    private final ClientService clientService;
    private final BookService bookService;

    @GetMapping("/list")
    public ModelAndView getBookLoan(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "false") boolean showReturned,
                                    ModelAndView modelAndView) {
        Page<BookLoanEntity> bookLoanEntities = bookLoanService.getBookLoans(PageRequest.of(page, size), showReturned);

        modelAndView.addObject(BOOK_LOANS_ATTRIBUTE, bookLoanEntities.getContent());
        modelAndView.addObject(PaginationUtils.CURRENT_PAGE_ATTRIBUTE, page);
        modelAndView.addObject(PaginationUtils.TOTAL_PAGES_ATTRIBUTE, bookLoanEntities.getTotalPages());
        modelAndView.addObject(NEW_BOOK_LOAN_ATTRIBUTE, new BookLoanIn(null, null));
        modelAndView.setViewName(BOOK_LOAN_LIST_VIEW);
        return modelAndView;
    }

    @PostMapping("/returnBook/{id}")
    public ModelAndView returnBook(@PathVariable Long id,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "false") boolean showReturned,
                                   ModelAndView modelAndView) {
        bookLoanService.returnBook(id);

        modelAndView.setViewName("redirect:/bookLoan/list?page=" + page + "&showReturned=" + showReturned);
        return modelAndView;
    }

    @GetMapping("/addRecord")
    public ModelAndView showCreateForm(@ModelAttribute ClientFilterIn clientFilterIn,
                                       @ModelAttribute BookFilterIn bookFilterIn,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       ModelAndView modelAndView) {
        Specification<ClientEntity> specificationClients = ClientFilterApplier.withFilters(clientFilterIn);
        Specification<BookEntity> specificationBooks = BookFilterApplier.withFilters(bookFilterIn);
        Page<ClientEntity> clientsPage = clientService.getClients(specificationClients, PageRequest.of(page, size));
        Page<BookEntity> booksPage = bookService.getBooks(specificationBooks, PageRequest.of(page, size));

        modelAndView.addObject("clients", clientsPage.getContent());
        modelAndView.addObject("totalPages", clientsPage.getTotalPages());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("size", size);
        modelAndView.addObject("clientFilterIn", clientFilterIn);
        modelAndView.addObject("books", booksPage.getContent());
        modelAndView.addObject("bookTotalPages", booksPage.getTotalPages());
        modelAndView.addObject("bookCurrentPage", page);
        modelAndView.addObject("bookSize", size);
        modelAndView.addObject("bookFilterIn", bookFilterIn);
        modelAndView.setViewName(BOOK_LOAN_CREATE_FORM);
        return modelAndView;
    }

    @PostMapping("/takeBook")
    public ModelAndView takeBook(@Valid @ModelAttribute(NEW_BOOK_LOAN_ATTRIBUTE) BookLoanIn bookLoanIn,
                                 BindingResult result,
                                 ModelAndView modelAndView) {
        if (result.hasErrors()) {
            modelAndView.setViewName(BOOK_LOAN_LIST_VIEW);
            return modelAndView;
        }
        bookLoanService.takeBook(bookLoanIn.getClientId(), bookLoanIn.getBookId());

        modelAndView.setViewName("redirect:/bookLoan/list");
        return modelAndView;
    }
}
