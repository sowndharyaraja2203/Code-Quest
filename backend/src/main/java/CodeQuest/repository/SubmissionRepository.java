package CodeQuest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import CodeQuest.model.Submission;

public interface SubmissionRepository
        extends JpaRepository<Submission, Long> {

    List<Submission> findByStatus(String status);

    List<Submission> findByProblemId(int problemId);

    List<Submission> findByUserEmail(String userEmail);
}