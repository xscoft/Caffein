package com.caffein.controller.admin;

import com.caffein.dto.ConceptDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.service.ConceptService;
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
@RequestMapping("/admin/concept")
public class ConceptController {

    private final ConceptService service;

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/concept/list";
    }

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("Concept List..........." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
        return "/admin/concept/list";
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerRun(ConceptDTO dto, RedirectAttributes rtts) {
        log.info("dto..." + dto);

        Long conceptId = service.register(dto);

        rtts.addFlashAttribute("msg", conceptId);

        return "redirect:/admin/concept/list";
    }

    @GetMapping({"/view", "/modify"})
    public void view(long conceptId, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("conceptId: " + conceptId);

        ConceptDTO dto = service.get(conceptId);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(ConceptDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes rtts) {
        log.info("concept Modify..........");
        log.info("dto: " + dto);
        service.modify(dto);
        rtts.addAttribute("page", requestDTO.getPage());
        rtts.addAttribute("conceptId", dto.getConceptId());

        return "redirect:/admin/concept/view";
    }

    @PostMapping("/remove")
    public String remove(long conceptId, RedirectAttributes rtts) {
        log.info("conceptId: " + conceptId);
        service.remove(conceptId);
        rtts.addFlashAttribute("msg", conceptId);
        return "redirect:/admin/concept/list";
    }
}
