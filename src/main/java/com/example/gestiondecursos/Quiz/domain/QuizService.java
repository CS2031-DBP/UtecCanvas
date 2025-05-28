package com.example.gestiondecursos.Quiz.domain;

import com.example.gestiondecursos.Course.domain.Course;
import com.example.gestiondecursos.Course.infrastructure.CourseRepository;
import com.example.gestiondecursos.Instructor.domain.Instructor;
import com.example.gestiondecursos.Question.domain.Question;
import com.example.gestiondecursos.Question.domain.QuestionAnswer;
import com.example.gestiondecursos.Question.domain.QuestionType;
import com.example.gestiondecursos.Question.dto.QuestionAnswerDTO;
import com.example.gestiondecursos.Question.dto.QuestionRequestDTO;
import com.example.gestiondecursos.Question.dto.QuestionResponseDTO;
import com.example.gestiondecursos.Quiz.Dto.QuizRequestDTO;
import com.example.gestiondecursos.Quiz.Dto.QuizResponseDTO;
import com.example.gestiondecursos.Quiz.Dto.QuizSubmissionDTO;
import com.example.gestiondecursos.Quiz.Dto.QuizSubmissionResponseDTO;
import com.example.gestiondecursos.Quiz.infrastructure.QuizRepository;
import com.example.gestiondecursos.Quiz.infrastructure.QuizSubmissionRepository;
import com.example.gestiondecursos.Student.domain.Student;
import com.example.gestiondecursos.User.domain.User;
import com.example.gestiondecursos.User.domain.UserService;
import com.example.gestiondecursos.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final UserService userService;

    public QuizResponseDTO createQuiz(Long courseId, QuizRequestDTO quizRequest) {
        User user = userService.getAuthenticatedUser();

        if (!(user instanceof Instructor)) {
            throw new AccessDeniedException("Only instructors can create quizzes");
        }

        boolean isInstructorOfCourse = courseRepository.existsByIdAndInstructorId(courseId, user.getId());
        if (!isInstructorOfCourse) {
            throw new AccessDeniedException("You are not the instructor of this course");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFound("Course not found"));

        if (quizRequest.getQuestions() == null || quizRequest.getQuestions().isEmpty()) {
            throw new IllegalArgumentException("Quiz must have at least one question");
        }

        for (QuestionRequestDTO qdto : quizRequest.getQuestions()) {
            if (qdto.getQuestionType() == QuestionType.CLOSED) {
                if (qdto.getOptions() == null || qdto.getOptions().isEmpty()) {
                    throw new IllegalArgumentException("Closed questions must have options");
                }
                if (qdto.getCorrectAnswer() == null || qdto.getCorrectAnswer().isBlank()) {
                    throw new IllegalArgumentException("Closed questions must have a correct answer");
                }
                if (!qdto.getOptions().contains(qdto.getCorrectAnswer())) {
                    throw new IllegalArgumentException("Correct answer must be one of the options");
                }
            } else if (qdto.getQuestionType() == QuestionType.OPEN) {
                qdto.setOptions(null);
                qdto.setCorrectAnswer(null);
            }
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setMaxScore(quizRequest.getMaxScore());
        quiz.setInstructions(quizRequest.getInstructions());
        quiz.setDueDate(quizRequest.getDueDate());
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setCourse(course);

        List<Question> questionList = quizRequest.getQuestions().stream().map(qdto -> {
            Question q = new Question();
            q.setQuestion(qdto.getQuestion());
            q.setCorrectAnswer(qdto.getCorrectAnswer());
            q.setOptions(qdto.getOptions());
            q.setIsOpen(qdto.getIsOpen());
            q.setQuestionType(qdto.getQuestionType());
            q.setQuiz(quiz);
            return q;
        }).toList();

        quiz.setQuestions(questionList);
        quizRepository.save(quiz);

        return modelMapper.map(quiz, QuizResponseDTO.class);
    }


    public QuizResponseDTO getQuizByTitle(String title) {
        Quiz quiz = quizRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFound("Quiz not found"));

        QuizResponseDTO dto = new QuizResponseDTO();
        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        dto.setInstructions(quiz.getInstructions());
        dto.setDueDate(quiz.getDueDate());
        dto.setMaxScore(quiz.getMaxScore());

        List<QuestionResponseDTO> questions = quiz.getQuestions().stream().map(q -> {
            QuestionResponseDTO qr = new QuestionResponseDTO();
            qr.setId(q.getId());
            qr.setQuestion(q.getQuestion());
            qr.setIsOpen(q.getIsOpen());
            qr.setOptions(q.getIsOpen() ? null : q.getOptions());
            return qr;
        }).toList();

        dto.setQuestions(questions);
        return dto;
    }


    @Transactional
    public QuizSubmissionResponseDTO submitQuiz(QuizSubmissionDTO submissionDTO) {
        User user = userService.getAuthenticatedUser();
        if (!(user instanceof Student student)) {
            throw new AccessDeniedException("Only students can submit quizzes.");
        }

        Quiz quiz = quizRepository.findById(submissionDTO.getQuizId())
                .orElseThrow(() -> new ResourceNotFound("Quiz not found"));

        QuizSubmission submission = new QuizSubmission();
        submission.setStudent(student);
        submission.setQuiz(quiz);
        submission.setSubmittedAt(LocalDateTime.now());

        Map<Long, String> answersMap = submissionDTO.getAnswers().stream()
                .collect(Collectors.toMap(QuestionAnswerDTO::getQuestionId, QuestionAnswerDTO::getAnswer));

        double totalCorrect = 0.0;
        int autoGradableCount = 0;

        for (Question question : quiz.getQuestions()) {
            String answer = answersMap.get(question.getId());

            QuestionAnswer qa = new QuestionAnswer();
            qa.setQuestion(question);
            qa.setAnswer(answer);
            qa.setSubmission(submission);
            submission.getAnswers().add(qa);

            if (!question.getIsOpen() && question.getCorrectAnswer() != null) {
                autoGradableCount++;
                if (question.getCorrectAnswer().equalsIgnoreCase(answer)) {
                    totalCorrect += 1.0;
                }
            }
        }

        if (autoGradableCount > 0) {
            double score = (totalCorrect / autoGradableCount) * quiz.getMaxScore();
            submission.setScore(score);
        } else {
            submission.setScore(null);
        }

        QuizSubmission saved = quizSubmissionRepository.save(submission);

        QuizSubmissionResponseDTO responseDTO = new QuizSubmissionResponseDTO();
        responseDTO.setId(saved.getId());
        responseDTO.setQuizId(saved.getQuiz().getId());
        responseDTO.setStudentId(saved.getStudent().getId());
        responseDTO.setScore(saved.getScore());
        responseDTO.setSubmittedAt(saved.getSubmittedAt());

        return responseDTO;
    }



    @Transactional
    public void deleteQuizById(Long quizId) {
        User user = userService.getAuthenticatedUser();

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFound("Quiz not found"));

        if (!(user instanceof Instructor)) {
            throw new AccessDeniedException("Only instructors can delete quizzes");
        }

        if (!quiz.getCourse().getInstructor().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not the instructor of this course");
        }

        quizRepository.deleteById(quizId);
    }

    @Transactional
    public void deleteQuizByTitle(String title) {
        User user = userService.getAuthenticatedUser();

        Quiz quiz = quizRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFound("Quiz not found"));

        if (!(user instanceof Instructor)) {
            throw new AccessDeniedException("Only instructors can delete quizzes");
        }

        if (!quiz.getCourse().getInstructor().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not the instructor of this course");
        }

        quizRepository.delete(quiz);
    }


}
