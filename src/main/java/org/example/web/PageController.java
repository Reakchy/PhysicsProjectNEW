package org.example.web;

import org.example.model.ResistivityForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class PageController {

    private static final Map<String, Double> RHO_MAP = new LinkedHashMap<>();
    private static final Map<String, Double> ALPHA_MAP = new LinkedHashMap<>();

    static {
        // Ом·мм²/м при 20°C
        RHO_MAP.put("Медь", 0.0175);
        RHO_MAP.put("Алюминий", 0.0280);
        RHO_MAP.put("Железо", 0.1000);
        RHO_MAP.put("Вольфрам", 0.0550);
        RHO_MAP.put("Нихром", 1.1000);
        RHO_MAP.put("Серебро", 0.0160);

        // 1/°C
        ALPHA_MAP.put("Медь", 0.0043);
        ALPHA_MAP.put("Алюминий", 0.0040);
        ALPHA_MAP.put("Железо", 0.0060);
        ALPHA_MAP.put("Вольфрам", 0.0045);
        ALPHA_MAP.put("Нихром", 0.0004);
        ALPHA_MAP.put("Серебро", 0.0038);
    }

    @GetMapping("/")
    public String home() {
        return "page_start";
    }

    @GetMapping("/main")
    public String main() {
        return "redirect:/resistance";
    }

    @GetMapping("/resistance")
    public String resistancePage(Model model) {
        ResistivityForm form = new ResistivityForm();
        form.setMetal("Медь");
        form.setT1(20.0);
        form.setT2(100.0);
        form.setLength(2.0);
        form.setDiameterMm(0.5);
        form.setRho1(RHO_MAP.get("Медь"));
        form.setAlpha(ALPHA_MAP.get("Медь"));

        model.addAttribute("form", form);
        model.addAttribute("metals", RHO_MAP.keySet());
        model.addAttribute("rhoMap", RHO_MAP);
        model.addAttribute("alphaMap", ALPHA_MAP);

        return "resistance";
    }

    @PostMapping("/calculate")
    public String calculate(@ModelAttribute("form") ResistivityForm form, Model model) {
        model.addAttribute("metals", RHO_MAP.keySet());
        model.addAttribute("rhoMap", RHO_MAP);
        model.addAttribute("alphaMap", ALPHA_MAP);

        try {
            if (form.getMetal() != null && RHO_MAP.containsKey(form.getMetal())) {
                form.setRho1(RHO_MAP.get(form.getMetal()));
                form.setAlpha(ALPHA_MAP.get(form.getMetal()));
            }

            if (form.getT1() == null || form.getT2() == null || form.getAlpha() == null || form.getRho1() == null) {
                form.setError("Заполни температуру, металл и исходные данные.");
                return "resistance";
            }

            if (form.getLength() == null || form.getLength() <= 0) {
                form.setError("Длина нити должна быть больше 0.");
                return "resistance";
            }

            if (form.getDiameterMm() == null || form.getDiameterMm() <= 0) {
                form.setError("Диаметр нити должен быть больше 0.");
                return "resistance";
            }

            double dT = form.getT2() - form.getT1();
            double k = 1.0 + form.getAlpha() * dT;
            double rho2 = form.getRho1() * k;
            double deltaRho = rho2 - form.getRho1();
            double percent = (deltaRho / form.getRho1()) * 100.0;

            double diameter = form.getDiameterMm();
            double areaMm2 = Math.PI * diameter * diameter / 4.0;

            double r1 = form.getRho1() * form.getLength() / areaMm2;
            double r2 = rho2 * form.getLength() / areaMm2;

            form.setDT(dT);
            form.setK(k);
            form.setRho2(rho2);
            form.setDeltaRho(deltaRho);
            form.setPercent(percent);
            form.setAreaMm2(areaMm2);
            form.setR1(r1);
            form.setR2(r2);
            form.setError(null);

        } catch (Exception e) {
            form.setError("Ошибка вычисления. Проверь введённые данные.");
        }

        return "resistance";
    }
}