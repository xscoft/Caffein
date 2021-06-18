package com.caffein.controller;

import com.caffein.dto.MemberDTO;
import com.caffein.service.CafeService;
import com.caffein.service.MemberService;
import com.caffein.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class BaseController {

    private final NoticeService noticeService;
    private final CafeService cafeService;
    private final MemberService memberService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("cafeList", cafeService.getCafeList());
        model.addAttribute("noticeList", noticeService.getNoticeList());
        return "/client/index";
    }

    @GetMapping("/register")
    public String register() {
        log.info("register get...");
        return "/client/register";
    }

    @PostMapping("/register")
    public String registerRun(MemberDTO dto, RedirectAttributes rtts) {
        log.info("dto..." + dto);

        Long memberId = memberService.register(dto);

        rtts.addFlashAttribute("msg", memberId);

        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String adminIndex() {
        return "/admin/index";
    }
}
