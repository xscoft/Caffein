package com.caffein.controller.client;

import com.caffein.dto.CafeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.entity.Member;
import com.caffein.service.CafeService;
import com.caffein.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping("/cafe")
public class ClientCafeController {

    private final CafeService service;
    private final ReviewService reviewService;

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/cafe/list";
    }

    @GetMapping("/register")
    public String register(@AuthenticationPrincipal Member member, Model model) {
        log.info("register get.....");

        model.addAttribute("member", member);

        return "/client/cafe/register";
    }

    @PostMapping("/register")
    public String registerRun(CafeDTO dto, RedirectAttributes rtts) {
        log.info("dto....." + dto);

        Long cafeId = service.register(dto);

        log.info("cafeId: " + cafeId);

        rtts.addFlashAttribute("msg" + cafeId);

        return "redirect:/cafe/list";
    }

    @GetMapping({"/list"})
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list............" + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
        return "/client/cafe/list";
    }

    @GetMapping({"/view"})
    public String view(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long cafeId, Model model) {
        log.info("cafeId: " + cafeId);

        CafeDTO cafeDTO = service.get(cafeId);

        log.info(cafeDTO);

        model.addAttribute("dto", cafeDTO);
        model.addAttribute("result", reviewService.getReviewsByCafeIdLimit3(cafeId));

        return"/client/cafe/view";
    }
}
