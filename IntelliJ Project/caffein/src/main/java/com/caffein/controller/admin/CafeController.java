package com.caffein.controller.admin;

import com.caffein.dto.CafeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.service.CafeService;
import com.caffein.service.ReviewService;
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
@RequestMapping("/admin/cafe")
@Log4j2
@RequiredArgsConstructor
public class CafeController {

    private final CafeService service;
    private final ReviewService reviewService;

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/cafe/list";
    }

    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list............" + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get.....");
    }

    @PostMapping("/register")
    public String registerRun(CafeDTO dto, RedirectAttributes rtts) {
        log.info("dto....." + dto);

        Long cafeId = service.register(dto);

        log.info("cafeId: " + cafeId);

        rtts.addFlashAttribute("msg" + cafeId);

        return "redirect:/admin/cafe/list";
    }

    @GetMapping({"/view", "/modify"})
    public void view(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long cafeId, Model model) {
        log.info("cafeId: " + cafeId);

        CafeDTO cafeDTO = service.get(cafeId);

        log.info(cafeDTO);

        model.addAttribute("dto", cafeDTO);
        model.addAttribute("result", reviewService.getReviewsByCafeIdLimit3(cafeId));
    }

    @PostMapping("/modify")
    public String modify(CafeDTO dto, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes rtts) {

        log.info("cafe modify.............");
        log.info("dto: " + dto);

        service.modify(dto);

        rtts.addAttribute("page", pageRequestDTO.getPage());

        rtts.addAttribute("cafeId", dto.getCafeId());

        return "redirect:/admin/cafe/view";
    }

    @PostMapping("/remove")
    public String remove(Long cafeId, RedirectAttributes rtts) {

        log.info("cafeId: " + cafeId);

        service.remove(cafeId);

        rtts.addAttribute("msg", cafeId);

        return "redirect:/admin/cafe/list";
    }
}
