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
import ru.varnavskii.librarydynamika.controller.dto.BookLoanOut;
import ru.varnavskii.librarydynamika.controller.dto.BookLoanOutShort;
import ru.varnavskii.librarydynamika.controller.dto.ClientFilterIn;
import ru.varnavskii.librarydynamika.controller.mapping.BookLoanMapper;
import ru.varnavskii.librarydynamika.repository.entity.BookEntity;
import ru.varnavskii.librarydynamika.repository.entity.BookLoanEntity;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;
import ru.varnavskii.librarydynamika.service.BookLoanService;
import ru.varnavskii.librarydynamika.service.BookService;
import ru.varnavskii.librarydynamika.service.ClientService;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/bookLoan")
@RequiredArgsConstructor
public class BookLoanController {

    private static final String BOOK_LOAN_LIST_VIEW = "bookLoan/bookLoanList";
    private static final String BOOK_LOAN_CREATE_FORM = "bookLoan/bookLoanCreateRecord";

    public static final String BOOK_LOANS_ATTRIBUTE = "bookLoans";
    public static final String NEW_BOOK_LOAN_ATTRIBUTE = "newBookLoan";
    public static final String BOOK_TOTAL_PAGES_ATTRIBUTE = "bookTotalPages";
    public static final String BOOK_CURRENT_PAGE_ATTRIBUTE = "bookCurrentPage";
    public static final String BOOK_SIZE_ATTRIBUTE = "bookSize";
    public static final String BOOK_FILTER_IN_ATTRIBUTE = "bookFilterIn";
    public static final String SIZE_ATTRIBUTE = "size";
    public static final String SHOW_RETURNED_ATTRIBUTE = "showReturned";

    private final BookLoanService bookLoanService;
    private final ClientService clientService;
    private final BookService bookService;
    private final BookLoanMapper bookLoanMapper;

    @GetMapping("/list")
    public ModelAndView getBookLoan(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "false") boolean showReturned,
                                    ModelAndView modelAndView) {
        Page<BookLoanEntity> bookLoanEntities = bookLoanService.getBookLoans(PageRequest.of(page, size), showReturned);
        List<BookLoanOutShort> records = bookLoanEntities.getContent().stream()
            .map(bookLoanMapper::toOutShort)
            .collect(Collectors.toList());

        modelAndView.addObject(BOOK_LOANS_ATTRIBUTE, records);
        modelAndView.addObject(PaginationUtils.CURRENT_PAGE_ATTRIBUTE, page);
        modelAndView.addObject(PaginationUtils.TOTAL_PAGES_ATTRIBUTE, bookLoanEntities.getTotalPages());
        modelAndView.addObject(NEW_BOOK_LOAN_ATTRIBUTE, new BookLoanIn(null, null));
        modelAndView.addObject(SHOW_RETURNED_ATTRIBUTE, showReturned);
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

        modelAndView.addObject(ClientController.CLIENTS_ATTRIBUTE_NAME, clientsPage.getContent());
        modelAndView.addObject(PaginationUtils.TOTAL_PAGES_ATTRIBUTE, clientsPage.getTotalPages());
        modelAndView.addObject(PaginationUtils.CURRENT_PAGE_ATTRIBUTE, page);
        modelAndView.addObject(SIZE_ATTRIBUTE, size);
        modelAndView.addObject(ClientController.CLIENT_FILTER_IN_ATTRIBUTE, clientFilterIn);
        modelAndView.addObject(BookController.BOOKS_ATTRIBUTE_NAME, booksPage.getContent());
        modelAndView.addObject(BOOK_TOTAL_PAGES_ATTRIBUTE, booksPage.getTotalPages());
        modelAndView.addObject(BOOK_CURRENT_PAGE_ATTRIBUTE, page);
        modelAndView.addObject(BOOK_SIZE_ATTRIBUTE, size);
        modelAndView.addObject(BOOK_FILTER_IN_ATTRIBUTE, bookFilterIn);
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

    @GetMapping("/report")
    public List<BookLoanOut> getReport() {
        List<BookLoanEntity> bookLoanEntities = bookLoanService.getAllBookLoansRecords();
        return bookLoanEntities.stream()
            .map(bookLoanMapper::toOut)
            .collect(Collectors.toList());
    }
}
