package ra.com.session03.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.com.session03.model.dto.ExamDTO;
import ra.com.session03.model.entity.Exam;
import ra.com.session03.service.ExamService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/exams")
public class ExamController {
    @Autowired
    private ExamService examService;

    @GetMapping
    public String listExams(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "size",defaultValue = "5") int size) {
        Page<Exam> examPage = examService.findAll(PageRequest.of(page, size),"");
        List<Integer> pages = new ArrayList<>();
        for (int i = 1 ; i <= examPage.getTotalPages(); i++ ) {
            pages.add(i);
        }
        model.addAttribute("page",page);
        model.addAttribute("pages",pages);
        model.addAttribute("exams", examPage.getContent());
        model.addAttribute("exam",new ExamDTO());
        return "examList";
    }

    @PostMapping("/add")
    public String addExam(@Valid @ModelAttribute("exam") ExamDTO examDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("exam",examDTO);
            return "redirect:/exams";
        }
        Exam exam = examService.save(examDTO);
        if (exam != null) {
            redirectAttributes.addFlashAttribute("message", "Exam added successfully");
        }else {
            redirectAttributes.addFlashAttribute("exam",examDTO);
            redirectAttributes.addFlashAttribute("message", "Error adding exam");
        }
        return "redirect:/exams";
    }
}
