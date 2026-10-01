package CodeQuest.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import CodeQuest.model.Submission;
import CodeQuest.model.User;
import CodeQuest.repository.SubmissionRepository;
import CodeQuest.repository.UserRepository;

@Service
public class LeaderboardService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    public LeaderboardService(
            SubmissionRepository submissionRepository,
            UserRepository userRepository) {

        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
    }

    public List<LeaderboardUser> getLeaderboard() {

        List<User> users = userRepository.findAll();

        List<Submission> submissions =
                submissionRepository.findByStatus("Accepted");

        List<LeaderboardUser> leaderboard =
                new ArrayList<>();

        for (User user : users) {

            Set<Integer> solvedProblems =
                    new HashSet<>();

            int totalScore = 0;

            for (Submission submission : submissions) {

                if (user.getEmail().equals(
                        submission.getUserEmail())) {

                    solvedProblems.add(
                            submission.getProblemId()
                    );

                    totalScore += submission.getScore();
                }
            }

            leaderboard.add(
                    new LeaderboardUser(
                            user.getName(),
                            solvedProblems.size(),
                            totalScore
                    )
            );
        }

        leaderboard.sort(
                (a, b) -> {

                    if (b.getProblemsSolved()
                            != a.getProblemsSolved()) {

                        return Integer.compare(
                                b.getProblemsSolved(),
                                a.getProblemsSolved()
                        );
                    }

                    return Integer.compare(
                            b.getScore(),
                            a.getScore()
                    );
                }
        );

        return leaderboard;
    }

    public static class LeaderboardUser {

        private String name;
        private int problemsSolved;
        private int score;

        public LeaderboardUser(
                String name,
                int problemsSolved,
                int score) {

            this.name = name;
            this.problemsSolved = problemsSolved;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getProblemsSolved() {
            return problemsSolved;
        }

        public int getScore() {
            return score;
        }
    }
}