package ra.com.session03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.com.session03.model.entity.Exam;
import ra.com.session03.model.entity.Question;
import ra.com.session03.service.AnswerService;
import ra.com.session03.service.ExamService;
import ra.com.session03.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private List<Question> questions = new ArrayList<Question>();
    private int indexQuestion = 0 ;
    private int trueAnswer = 0 ;
    private int falseAnswer = 0 ;
    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @GetMapping
    public String home(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "size",defaultValue = "5") int size ,
                       @RequestParam(name = "search" , required = false) String search) {
        // reset lại các giá trị bài thi trước đó
        trueAnswer = 0 ;
        falseAnswer = 0 ;
        indexQuestion = 0 ;

        Page<Exam> examPage = examService.findAll(PageRequest.of(page, size),search);
        List<Integer> totalPage = new ArrayList<>();
        for(int i = 1 ; i <= examPage.getTotalPages(); i++) {
            totalPage.add(i);
        }
        model.addAttribute("search", search);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("page", examPage);
        model.addAttribute("exams",examPage.getContent());
        return "home";
    }

    @GetMapping("/play/{examId}")
    public String play(@PathVariable int examId, Model model, RedirectAttributes redirectAttributes) {

        if (indexQuestion == 0){
            questions = questionService.getQuestionByExamId(examId);
        }
        if (questions.isEmpty()) {
            redirectAttributes.addFlashAttribute("message","Bài thi naày chưa có câu hỏi !");
            return "redirect:/home";
        }
        Question currentQuestion = questions.get(indexQuestion);
        model.addAttribute("question", currentQuestion);
        indexQuestion++;
        model.addAttribute("examId", examId);
        model.addAttribute("answers" , answerService.getAnswers(currentQuestion.getId()));
        return "play" ;
    }

    @PostMapping("/play/exam/{examId}")
    public String playExam(@PathVariable int examId,
                           @RequestParam("answer") String answer, Model model, RedirectAttributes redirectAttributes) {
        String message = "" ;
        if(answer.equals("true")){
            trueAnswer++;
            message = "Bạn đã trả lời đúng !" ;
        }else {
            falseAnswer++;
            message = "Bạn đã trả lời sai !" ;
        }
        if (indexQuestion == questions.size()) {

            model.addAttribute("message", message);
            model.addAttribute("trueAnswer", trueAnswer);
            model.addAttribute("falseAnswer", falseAnswer);
            model.addAttribute("totalQuestion", questions.size());
            indexQuestion = 0;
            trueAnswer = 0;
            falseAnswer = 0;
            return "result";
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/home/play/" + examId;
    }
}
