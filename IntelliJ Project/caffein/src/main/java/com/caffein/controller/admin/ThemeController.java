package com.caffein.controller.admin;

import com.caffein.dto.ThemeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.service.ThemeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/admin/theme")
public class ThemeController {

    private final ThemeService service;

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/theme/list";
    }

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("Theme List..........." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
        return "/admin/theme/list";
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerRun(ThemeDTO dto, RedirectAttributes rtts) {
        log.info("dto..." + dto);

        Long themeId = service.register(dto);

        rtts.addFlashAttribute("msg", themeId);

        return "redirect:/admin/theme/list";
    }

    @GetMapping({"/view", "/modify"})
    public void view(long themeId, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("themeId: " + themeId);

        ThemeDTO dto = service.get(themeId);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(ThemeDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes rtts) {
        log.info("theme Modify..........");
        log.info("dto: " + dto);
        service.modify(dto);
        rtts.addAttribute("page", requestDTO.getPage());
        rtts.addAttribute("themeId", dto.getThemeId());

        return "redirect:/admin/theme/view";
    }

    @PostMapping("/remove")
    public String remove(long themeId, RedirectAttributes rtts) {
        log.info("themeId: " + themeId);
        service.remove(themeId);
        rtts.addFlashAttribute("msg", themeId);
        return "redirect:/admin/theme/list";
    }
}
