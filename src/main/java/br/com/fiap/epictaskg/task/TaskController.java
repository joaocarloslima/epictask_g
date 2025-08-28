package br.com.fiap.epictaskg.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final MessageSource messageSource;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        var tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/form")
    public String form(){
        return "form";
    }

    @PostMapping("/form")
    public String create(@Valid Task task, BindingResult result, RedirectAttributes redirect ){ //biding

        if(result.hasErrors()) return "form";

        var message = messageSource.getMessage("task.create.success", null, LocaleContextHolder.getLocale());
        taskService.save(task);
        redirect.addFlashAttribute("message", message);
        return "redirect:/task"; //301
    }
}
