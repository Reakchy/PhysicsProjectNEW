package org.example.controller;

import org.example.model.ResistivityForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "redirect:/resistance";
    }

    @GetMapping("/resistance")
    public String resistancePage(Model model) {
        model.addAttribute("form", new ResistivityForm());
        model.addAttribute("metals", List.of(
                "Медь", "Алюминий", "Железо", "Вольфрам", "Нихром", "Серебро"
        ));
        return "resistance";
    }
}