package CodeQuest.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CodeQuest.model.Submission;
import CodeQuest.repository.SubmissionRepository;

@RestController
@RequestMapping("/api/submissions")
@CrossOrigin(origins = "http://localhost:5173")
public class SubmissionController {

    private final SubmissionRepository submissionRepository;

    public SubmissionController(
            SubmissionRepository submissionRepository) {

        this.submissionRepository = submissionRepository;
    }

    // ==========================================
    // GET ALL SUBMISSIONS
    // ==========================================

    @GetMapping
    public List<Submission> getAllSubmissions() {

        return submissionRepository.findAll();
    }

    // ==========================================
    // GET SUBMISSIONS FOR A USER
    // ==========================================

    @GetMapping("/user/{email}")
    public List<Submission> getUserSubmissions(
            @PathVariable String email) {

        return submissionRepository.findByUserEmail(email);
    }

    // ==========================================
    // DELETE ALL OLD SUBMISSIONS
    // ==========================================

    @GetMapping("/clear")
    public String clearSubmissions() {

        submissionRepository.deleteAll();

        return "All submissions cleared successfully.";
    }

    // ==========================================
    // GET USER STATISTICS
    // ==========================================

    @GetMapping("/stats/{email}")
    public Map<String, Object> getStats(
            @PathVariable String email) {

        List<Submission> submissions =
                submissionRepository.findByUserEmail(email);

        Set<Integer> solvedProblems =
                new HashSet<>();

        int totalScore = 0;
        int easy = 0;
        int medium = 0;
        int hard = 0;

        for (Submission submission : submissions) {

            if ("Accepted".equalsIgnoreCase(
                    submission.getStatus())) {

                int problemId =
                        submission.getProblemId();

                // Count each problem only once
                if (solvedProblems.add(problemId)) {

                    if (problemId == 1 ||
                        problemId == 2 ||
                        problemId == 4 ||
                        problemId == 5) {

                        easy++;

                    } else if (problemId == 3 ||
                               problemId == 6) {

                        medium++;

                    } else if (problemId == 7) {

                        hard++;
                    }
                }

                totalScore += submission.getScore();
            }
        }

        int currentStreak =
                calculateCurrentStreak(submissions);

        return Map.of(
                "problemsSolved",
                solvedProblems.size(),

                "totalScore",
                totalScore,

                "easy",
                easy,

                "medium",
                medium,

                "hard",
                hard,

                "currentStreak",
                currentStreak
        );
    }

    // ==========================================
    // CALCULATE CURRENT STREAK
    // ==========================================

    private int calculateCurrentStreak(
            List<Submission> submissions) {

        Set<LocalDate> submissionDates =
                new HashSet<>();

        for (Submission submission : submissions) {

            if ("Accepted".equalsIgnoreCase(
                    submission.getStatus())
                    && submission.getSubmittedAt() != null) {

                submissionDates.add(
                        submission
                                .getSubmittedAt()
                                .toLocalDate()
                );
            }
        }

        if (submissionDates.isEmpty()) {
            return 0;
        }

        LocalDate today =
                LocalDate.now();

        LocalDate currentDate =
                today;

        int streak = 0;

        while (submissionDates.contains(currentDate)) {

            streak++;

            currentDate =
                    currentDate.minusDays(1);
        }

        return streak;
    }
}