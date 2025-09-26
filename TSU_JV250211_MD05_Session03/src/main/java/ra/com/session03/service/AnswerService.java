package ra.com.session03.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.com.session03.model.dto.AnswerDTO;
import ra.com.session03.model.entity.Answer;
import ra.com.session03.model.entity.Question;
import ra.com.session03.repository.AnswerRepository;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionService questionService;

    public List<Answer> getAnswers(long id) {
        return answerRepository.findAllByQuestionId(id);
    }

    public Answer save(AnswerDTO answerDTO , long questionId) {
        Question question = questionService.getQuestionById(questionId);
        Answer answer = new Answer();
        answer.setContent(answerDTO.getContent());
        answer.setCorrect(answerDTO.isCorrect());
        answer.setQuestion(question);
        return answerRepository.save(answer);
    }
}