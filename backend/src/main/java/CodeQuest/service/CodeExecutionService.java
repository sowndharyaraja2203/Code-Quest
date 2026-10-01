package CodeQuest.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import CodeQuest.model.Submission;
import CodeQuest.repository.SubmissionRepository;

@Service
public class CodeExecutionService {

    private final SubmissionRepository submissionRepository;

    public CodeExecutionService(
            SubmissionRepository submissionRepository) {

        this.submissionRepository = submissionRepository;
    }

    // ==========================================
    // RUN CODE - DEFAULT SAMPLE INPUT
    // ==========================================

    public String executeJavaCode(String code) {

        String sampleInput =
                "2 7 11 15\n9\n";

        return executeJavaCode(code, sampleInput);
    }

    public String executeJavaCode(
            String code,
            int problemId) {

        String sampleInput = "";

        if (problemId == 1) {

            sampleInput = "2 7 11 15\n9\n";

        } else if (problemId == 2) {

            sampleInput = "hello\n";

        } else if (problemId == 3) {

            sampleInput = "1 3 5 7 9\n7\n";
        } else if (problemId == 4) {
            sampleInput = "madam\n";
        } else if (problemId == 5) {
             sampleInput = "10 25 7 40 15\n";
        } else if (problemId == 6) {
             sampleInput = "hello\n";
        } else if (problemId == 7) {
             sampleInput = "1 3 4 2 2\n";
        } else {

        return "❌ Unknown problem ID: " + problemId;
       }

      System.out.println(
        "PROBLEM ID = [" + problemId + "]"
       );

       System.out.println(
        "SAMPLE INPUT = [" + sampleInput + "]"
       );

        return executeJavaCode(code, sampleInput);
    }

    // ==========================================
    // RUN CODE WITH INPUT
    // ==========================================

    public String executeJavaCode(
            String code,
            String input) {

        System.out.println("INPUT RECEIVED:");
        System.out.println(input);

        Path tempDirectory = null;

        try {

            // ==========================================
            // CREATE TEMPORARY DIRECTORY
            // ==========================================

            tempDirectory =
                    Files.createTempDirectory("codequest");

            // ==========================================
            // CREATE SOLUTION.JAVA
            // ==========================================

            Path javaFile =
                    tempDirectory.resolve("Solution.java");

            Files.writeString(javaFile, code);

            // ==========================================
            // COMPILE
            // ==========================================

            Process compileProcess =
                    new ProcessBuilder(
                            "javac",
                            javaFile.toString()
                    )
                    .redirectErrorStream(true)
                    .start();

            String compileOutput =
                    readOutput(
                            compileProcess.getInputStream()
                    );

            boolean compileFinished =
                    compileProcess.waitFor(
                            10,
                            TimeUnit.SECONDS
                    );

            if (!compileFinished) {

                compileProcess.destroyForcibly();

                return "⏱️ Compilation Time Limit Exceeded";
            }

            if (compileProcess.exitValue() != 0) {

                return "🔴 Compilation Error:\n\n"
                        + compileOutput;
            }

            // ==========================================
            // RUN PROGRAM
            // ==========================================
        Process runProcess =new ProcessBuilder(
                "java",
                "-cp",
                tempDirectory.toString(),
                "Solution"
        )
        .redirectErrorStream(true)
        .start();

        System.out.println("DEBUG INPUT = [" + input + "]");

            // ==========================================
            // SEND INPUT
            // ==========================================

            // SEND INPUT
        if (input != null) {
        java.io.BufferedWriter writer =
            new java.io.BufferedWriter(
                    new java.io.OutputStreamWriter(
                            runProcess.getOutputStream()
                    )
            );

        writer.write(input);
        writer.flush();
        writer.close();
}

            // ==========================================
            // WAIT
            // ==========================================

            boolean finished =
                    runProcess.waitFor(
                            5,
                            TimeUnit.SECONDS
                    );

            if (!finished) {

                runProcess.destroyForcibly();

                return "⏱️ Time Limit Exceeded";
            }

            // ==========================================
            // READ OUTPUT
            // ==========================================

            String output =
                    readOutput(
                            runProcess.getInputStream()
                    );

            // ==========================================
            // RUNTIME ERROR
            // ==========================================

            if (runProcess.exitValue() != 0) {

                return "⚠️ Runtime Error:\n\n"
                        + output;
            }

            // ==========================================
            // NO OUTPUT
            // ==========================================

            if (output.trim().isEmpty()) {

                return "Program executed successfully.\n\n"
                        + "No output produced.";
            }

            return "Program Output:\n\n"
                    + output.trim();

        } catch (Exception e) {

            return "❌ Error while executing code:\n\n"
                    + e.getMessage();

        } finally {

            deleteTemporaryDirectory(
                    tempDirectory
            );
        }
    }

    // ==========================================
    // SAVE SUBMISSION
    // ==========================================

    private void saveSubmission(
        int problemId,
        String language,
        int score,
        String userEmail) {

    Submission submission =
            new Submission(
                    problemId,
                    language,
                    "Accepted",
                    score
            );

    submission.setUserEmail(userEmail);

    submissionRepository.save(submission);
}
    // ==========================================
    // SUBMIT CODE
    // ==========================================

    public String submitJavaCode(
        String code,
        int problemId,
        String userEmail) {
        // ==========================================
        // PROBLEM 1 - TWO SUM
        // ==========================================

        if (problemId == 1) {

            String[][] testCases = {

                    {
                            "2 7 11 15\n9\n",
                            "0 1"
                    },

                    {
                            "3 2 4\n6\n",
                            "1 2"
                    },

                    {
                            "3 3\n6\n",
                            "0 1"
                    }
            };

            for (int i = 0;
                 i < testCases.length;
                 i++) {

                String result =
                        executeJavaCode(
                                code,
                                testCases[i][0]
                        );

                String expected =
                        testCases[i][1];

                // Error checking

                if (result.startsWith("🔴")
                        || result.startsWith("⚠️")
                        || result.startsWith("⏱️")
                        || result.startsWith("❌")) {

                    return result;
                }

                String actual =
                        result
                                .replace(
                                        "Program Output:",
                                        ""
                                )
                                .trim();

                if (!actual.equals(expected)) {

                    return "❌ Wrong Answer\n\n"
                            + "Test Case: "
                            + (i + 1)
                            + "\n\n"
                            + "Expected: "
                            + expected
                            + "\n"
                            + "Your Output: "
                            + actual;
                }
            }

            // Save accepted submission

            saveSubmission(
                problemId,
                "Java",
                10,
                userEmail
                );
            return "✅ Accepted\n\n"
                    + "All 3 test cases passed!";
        }

        // ==========================================
        // PROBLEM 2 - REVERSE STRING
        // ==========================================

        if (problemId == 2) {

            String[][] testCases = {

                    {
                            "hello\n",
                            "olleh"
                    },

                    {
                            "world\n",
                            "dlrow"
                    },

                    {
                            "codequest\n",
                            "tseuqedoc"
                    }
            };

            for (int i = 0;
                 i < testCases.length;
                 i++) {

                String result =
                        executeJavaCode(
                                code,
                                testCases[i][0]
                        );

                if (result.startsWith("🔴")
                        || result.startsWith("⚠️")
                        || result.startsWith("⏱️")
                        || result.startsWith("❌")) {

                    return result;
                }

                String actual =
                        result
                                .replace(
                                        "Program Output:",
                                        ""
                                )
                                .trim();

                String expected =
                        testCases[i][1];

                if (!actual.equals(expected)) {

                    return "❌ Wrong Answer\n\n"
                            + "Test Case: "
                            + (i + 1)
                            + "\n\n"
                            + "Expected: "
                            + expected
                            + "\n"
                            + "Your Output: "
                            + actual;
                }
            }

            // Save accepted submission

            saveSubmission(
                problemId,
                "Java",
                10,
                userEmail
                );

            return "✅ Accepted\n\n"
                    + "All 3 test cases passed!";
        }

        // ==========================================
        // PROBLEM 3 - BINARY SEARCH
        // ==========================================

        if (problemId == 3) {

            String[][] testCases = {

                    {
                            "1 3 5 7 9\n7\n",
                            "3"
                    },

                    {
                            "1 3 5 7 9\n1\n",
                            "0"
                    },

                    {
                            "1 3 5 7 9\n8\n",
                            "-1"
                    }
            };

            for (int i = 0;
                 i < testCases.length;
                 i++) {

                String result =
                        executeJavaCode(
                                code,
                                testCases[i][0]
                        );

                if (result.startsWith("🔴")
                        || result.startsWith("⚠️")
                        || result.startsWith("⏱️")
                        || result.startsWith("❌")) {

                    return result;
                }

                String actual =
                        result
                                .replace(
                                        "Program Output:",
                                        ""
                                )
                                .trim();

                String expected =
                        testCases[i][1];

                if (!actual.equals(expected)) {

                    return "❌ Wrong Answer\n\n"
                            + "Test Case: "
                            + (i + 1)
                            + "\n\n"
                            + "Expected: "
                            + expected
                            + "\n"
                            + "Your Output: "
                            + actual;
                }
            }

            // Save accepted submission

           saveSubmission(
                problemId,
                "Java",
                20,
                userEmail
                );

            return "✅ Accepted\n\n"
                    + "All 3 test cases passed!";
        }
        // ==========================================
        // PROBLEM 4 - PALINDROME CHECK
        // ==========================================

        if (problemId == 4) {

            String[][] testCases = {
                    {
                            "madam\n",
                            "true"
                    },
                    {
                            "hello\n",
                            "false"
                    },
                    {
                            "level\n",
                            "true"
                    }
            };

            for (int i = 0;
                 i < testCases.length;
                 i++) {

                String result =
                        executeJavaCode(
                                code,
                                testCases[i][0]
                        );

                if (result.startsWith("🔴")
                        || result.startsWith("⚠️")
                        || result.startsWith("⏱️")
                        || result.startsWith("❌")) {

                    return result;
                }

                String actual =
                        result
                                .replace(
                                        "Program Output:",
                                        ""
                                )
                                .trim();

                String expected =
                        testCases[i][1];

                if (!actual.equals(expected)) {

                    return "❌ Wrong Answer\n\n"
                            + "Test Case: "
                            + (i + 1)
                            + "\n\n"
                            + "Expected: "
                            + expected
                            + "\n"
                            + "Your Output: "
                            + actual;
                }
            }

            saveSubmission(
                problemId,
                "Java",
                10,
                userEmail
               );
            return "✅ Accepted\n\n"
                    + "All 3 test cases passed!";
        }
        // ==========================================
        // PROBLEM 5 - FIND MAXIMUM NUMBER
       // ==========================================

        if (problemId == 5) {

        String[][] testCases = {

        {
            "10 25 7 40 15\n",
            "40"
        },

        {
            "5 2 8 1 9\n",
            "9"
        },

        {
            "100 20 50\n",
            "100"
        }
    };

        for (int i = 0; i < testCases.length; i++) {

        String result =
                executeJavaCode(
                        code,
                        testCases[i][0]
                );

        if (result.startsWith("🔴")
                || result.startsWith("⚠️")
                || result.startsWith("⏱️")
                || result.startsWith("❌")) {

            return result;
        }

        String actual =
                result
                        .replace(
                                "Program Output:",
                                ""
                        )
                        .trim();

        String expected = testCases[i][1];

        if (!actual.equals(expected)) {

            return "❌ Wrong Answer\n\n"
                    + "Test Case: "
                    + (i + 1)
                    + "\n\n"
                    + "Expected: "
                    + expected
                    + "\n"
                    + "Your Output: "
                    + actual;
        }
    }

    saveSubmission(
            problemId,
            "Java",
            10,
            userEmail
    );

    return "✅ Accepted\n\n"
            + "All 3 test cases passed!";
    }
    // ==========================================
   // PROBLEM 6 - COUNT VOWELS
   // ==========================================

   if (problemId == 6) {

    String[][] testCases = {

        {
            "hello\n",
            "2"
        },

        {
            "programming\n",
            "3"
        },

        {
            "education\n",
            "5"
        }
    };

    for (int i = 0; i < testCases.length; i++) {

        String result = executeJavaCode(
                code,
                testCases[i][0]
        );

        if (result.startsWith("🔴")
                || result.startsWith("⚠️")
                || result.startsWith("⏱️")
                || result.startsWith("❌")) {

            return result;
        }

        String actual = result
                .replace("Program Output:", "")
                .trim();

        String expected = testCases[i][1];

        if (!actual.equals(expected)) {

            return "❌ Wrong Answer\n\n"
                    + "Test Case: "
                    + (i + 1)
                    + "\n\n"
                    + "Expected: "
                    + expected
                    + "\n"
                    + "Your Output: "
                    + actual;
        }
    }

    saveSubmission(
            problemId,
            "Java",
            10,
            userEmail
    );

    return "✅ Accepted\n\n"
            + "All 3 test cases passed!";
    }
    // ==========================================
    // PROBLEM 7 - FIND DUPLICATE ELEMENTS
   // ==========================================

   if (problemId == 7) {

    String[][] testCases = {

        {
            "1 3 4 2 2\n",
            "2"
        },

        {
            "3 1 3 4 2\n",
            "3"
        },

        {
            "1 4 2 4 3\n",
            "4"
        }
    };

    for (int i = 0; i < testCases.length; i++) {

        String result = executeJavaCode(
                code,
                testCases[i][0]
        );

        if (result.startsWith("🔴")
                || result.startsWith("⚠️")
                || result.startsWith("⏱️")
                || result.startsWith("❌")) {

            return result;
        }

        String actual = result
                .replace("Program Output:", "")
                .trim();

        String expected = testCases[i][1];

        if (!actual.equals(expected)) {

            return "❌ Wrong Answer\n\n"
                    + "Test Case: "
                    + (i + 1)
                    + "\n\n"
                    + "Expected: "
                    + expected
                    + "\n"
                    + "Your Output: "
                    + actual;
        }
    }

    saveSubmission(
            problemId,
            "Java",
            20,
            userEmail
    );

    return "✅ Accepted\n\n"
            + "All 3 test cases passed!";
    }

        // ==========================================
        // UNKNOWN PROBLEM
        // ==========================================

        return "❌ Unknown problem.";
    }

    // ==========================================
    // READ OUTPUT
    // ==========================================

    private String readOutput(
            InputStream inputStream)
            throws IOException {

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                inputStream
                        )
                );

        StringBuilder output =
                new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {

            output.append(line)
                    .append(
                            System.lineSeparator()
                    );
        }

        return output.toString();
    }

    // ==========================================
    // DELETE TEMPORARY FILES
    // ==========================================

    private void deleteTemporaryDirectory(
            Path tempDirectory) {

        if (tempDirectory == null) {
            return;
        }

        try {

            Files.walk(tempDirectory)
                    .sorted(
                            (a, b) ->
                                    b.compareTo(a)
                    )
                    .forEach(path -> {

                        try {

                            Files.deleteIfExists(path);

                        } catch (IOException ignored) {

                        }
                    });

        } catch (IOException ignored) {

        }
    }
}