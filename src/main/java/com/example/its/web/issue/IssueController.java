package com.example.its.web.issue;

import com.example.its.domain.issue.IssueEntity;
import com.example.its.domain.issue.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/issues")
@RequiredArgsConstructor //finalがついているフィールドかつ、初期化されていないフィールドを初期化するコンストラクタを自動生成する
public class IssueController {

    private final IssueService issueService;
    @GetMapping
    public String showList(Model model) {
        model.addAttribute("issueList", issueService.findAll());
        return "issues/list";
    }

    // GET /issues/creationForm
    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute IssueForm form) {
//        model.addAttribute("issueForm", new IssueForm());
        return "issues/creationForm";
    }

    // POST /issues
    @PostMapping
    public String create(@Validated IssueForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form);
        }
        issueService.create(form.getSummary(), form.getDescription());
        return "redirect:/issues";
    }

    // GET localhost:8080/issues/1
    @GetMapping("/{issueId}")
    public String showDetail(@PathVariable("issueId") long issueId, Model model) {
        model.addAttribute("issue", issueService.findById(issueId));
        return "issues/detail";
    }
}

