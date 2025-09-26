package ra.com.session03.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.com.session03.model.dto.AnswerDTO;
import ra.com.session03.model.entity.Answer;
import ra.com.session03.model.entity.Question;
import ra.com.session03.service.AnswerService;
import ra.com.session03.service.QuestionService;

@Controller
@RequestMapping("/answers")
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions/{id}")
    public String getAnswers(Model model, @PathVariable long id) {
        Question question = questionService.getQuestionById(id);
        model.addAttribute("answers" , answerService.getAnswers(id));
        model.addAttribute("answerDTO" , new AnswerDTO());
        model.addAttribute("id" , id);
        model.addAttribute("question" , question);
        return "questionDetail";
    }

    @PostMapping("/question/{id}/add")
    public String addAnswer(Model model, @PathVariable long id, @Valid @ModelAttribute("answer") AnswerDTO answerDTO
            , BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("answerDTO" , answerDTO);
            return "questionDetail";
        }
        Answer answer = answerService.save(answerDTO,id);
        return "redirect:/answers/questions/" + id;

    }

}
