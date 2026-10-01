package CodeQuest.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CodeQuest.dto.CodeSubmission;
import CodeQuest.service.CodeExecutionService;

@RestController
@RequestMapping("/api/code")
@CrossOrigin(origins = "http://localhost:5173")
public class CodeController {

    private final CodeExecutionService codeExecutionService;

    public CodeController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    @PostMapping("/run")
    public String runCode(@RequestBody CodeSubmission submission) {

        return codeExecutionService.executeJavaCode(
            submission.getCode(),
            submission.getProblemId()
        );
    }

    @PostMapping("/submit")
    public String submitCode(@RequestBody CodeSubmission submission) {

        return codeExecutionService.submitJavaCode(
            submission.getCode(),
            submission.getProblemId(),
            submission.getUserEmail()
        );
    }
}