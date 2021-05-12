package org.example.controller.v1;

import lombok.RequiredArgsConstructor;
import org.example.exception.IssueNotFoundException;
import org.example.mapper.IssueMapper;
import org.example.mapper.IssueResponseMapper;
import org.example.service.IssueService;
import org.example.transport.dto.CreateIssueRequest;
import org.example.transport.dto.IssueResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/issues")
public class IssueController {

    private final IssueService issueService;

    private final IssueMapper issueMapper;

    private final IssueResponseMapper issueResponseMapper;

    @PostMapping
    public IssueResponse create(@RequestBody @Valid CreateIssueRequest request) {
        var createdIssue =  issueService.save(issueMapper.toDomain(request));

        return issueResponseMapper.toDomain(createdIssue);
    }

    @GetMapping
    public List<IssueResponse> getAll(){
        return issueResponseMapper.toDomain(issueService.getAll());
    }

    @GetMapping("/{id}")
    public IssueResponse getById(@PathVariable Long id) {
        return issueResponseMapper.toDomain(
                issueService
                        .getById(id)
                        .orElseThrow(() -> new IssueNotFoundException(id))
        );
    }
}
