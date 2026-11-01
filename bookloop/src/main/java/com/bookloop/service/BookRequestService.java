package com.bookloop.service;

import com.bookloop.dto.RequestDto;
import com.bookloop.entity.BookRequest;
import com.bookloop.entity.User;
import com.bookloop.repository.BookRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * BookRequestService
 * Manages requests for textbooks created by students ("Need a Book").
 */
@Service
public class BookRequestService {

    private final BookRequestRepository requestRepository;

    public BookRequestService(BookRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<BookRequest> getAllRequests() {
        return requestRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<BookRequest> getRequestsByUser(User user) {
        return requestRepository.findByRequesterOrderByCreatedAtDesc(user);
    }

    @Transactional
    public BookRequest createRequest(RequestDto dto, User requester) {
        BookRequest request = new BookRequest(
                dto.getTitle().trim(),
                dto.getAuthor() != null ? dto.getAuthor().trim() : "",
                dto.getCourse().trim(),
                dto.getSemester().trim(),
                dto.getNotes() != null ? dto.getNotes().trim() : "",
                dto.getContactInfo().trim(),
                requester
        );
        return requestRepository.save(request);
    }

    public Optional<BookRequest> findById(Long id) {
        return requestRepository.findById(id);
    }

    @Transactional
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }
}
