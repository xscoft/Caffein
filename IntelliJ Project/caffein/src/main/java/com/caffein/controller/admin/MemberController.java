package com.caffein.controller.admin;

import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.MemberDTO;
import com.caffein.service.MemberService;
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
@RequestMapping("/admin/member")
public class MemberController {

    private final MemberService service;

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/member/list";
    }

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("Member List..........." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
        return "/admin/member/list";
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerRun(MemberDTO dto, RedirectAttributes rtts) {
        log.info("dto..." + dto);

        Long memberId = service.register(dto);

        rtts.addFlashAttribute("msg", memberId);

        return "redirect:/admin/member/list";
    }

    @GetMapping({"/view", "/modify"})
    public void view(long memberId, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("memberId: " + memberId);

        MemberDTO dto = service.view(memberId);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(MemberDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes rtts) {
        log.info("member Modify..........");
        log.info("dto: " + dto);
        service.modify(dto);
        rtts.addAttribute("page", requestDTO.getPage());
        rtts.addAttribute("memberId", dto.getMemberId());

        return "redirect:/admin/member/view";
    }
}
