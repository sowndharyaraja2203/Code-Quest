package CodeQuest.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import CodeQuest.model.Submission;
import CodeQuest.repository.SubmissionRepository;

@Service
public class DashboardService {

    private final SubmissionRepository submissionRepository;

    public DashboardService(
            SubmissionRepository submissionRepository) {

        this.submissionRepository = submissionRepository;
    }

    public DashboardStats getDashboardStats(String userEmail) {

        List<Submission> acceptedSubmissions =
                submissionRepository.findByUserEmail(userEmail);

        Set<Integer> solvedProblems =
                new HashSet<>();

        int totalScore = 0;
        int easy = 0;
        int medium = 0;
        int hard = 0;

        for (Submission submission : acceptedSubmissions) {

            if (!"Accepted".equals(submission.getStatus())) {
                continue;
            }

            if (solvedProblems.add(
                    submission.getProblemId())) {

                totalScore += submission.getScore();

                int problemId =
                        submission.getProblemId();

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
        }

      int currentStreak =
        calculateCurrentStreak(acceptedSubmissions);

return new DashboardStats(

        solvedProblems.size(),

        totalScore,

        currentStreak,

        easy,

        medium,

        hard
);
    }
    private int calculateCurrentStreak(
        List<Submission> submissions) {

    Set<LocalDate> submissionDates =
            new HashSet<>();

    for (Submission submission : submissions) {

        if ("Accepted".equals(submission.getStatus())
                && submission.getSubmittedAt() != null) {

            submissionDates.add(
                    submission.getSubmittedAt().toLocalDate()
            );
        }
    }

    if (submissionDates.isEmpty()) {
        return 0;
    }

    LocalDate today = LocalDate.now();

    LocalDate currentDate = today;

    int streak = 0;

    while (submissionDates.contains(currentDate)) {

        streak++;

        currentDate =
                currentDate.minusDays(1);
    }

    return streak;
}

    public static class DashboardStats {

        private int problemsSolved;
        private int totalScore;
        private int currentStreak;
        private int easy;
        private int medium;
        private int hard;

        public DashboardStats(
                int problemsSolved,
                int totalScore,
                int currentStreak,
                int easy,
                int medium,
                int hard) {

            this.problemsSolved = problemsSolved;
            this.totalScore = totalScore;
            this.currentStreak = currentStreak;
            this.easy = easy;
            this.medium = medium;
            this.hard = hard;
        }

        public int getProblemsSolved() {
            return problemsSolved;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public int getCurrentStreak() {
            return currentStreak;
        }

        public int getEasy() {
            return easy;
        }

        public int getMedium() {
            return medium;
        }

        public int getHard() {
            return hard;
        }
    }
}