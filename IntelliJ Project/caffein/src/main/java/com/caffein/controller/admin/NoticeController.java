package com.caffein.controller.admin;

import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.NoticeDTO;
import com.caffein.service.NoticeService;
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
@RequestMapping("/admin/notice")
public class NoticeController {

    private final NoticeService service;

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/notice/list";
    }

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("Notice List..........." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
        return "/admin/notice/list";
    }

    @GetMapping("/write")
    public void write() {
        log.info("write get...");
    }

    @PostMapping("/write")
    public String writeRun(NoticeDTO dto, RedirectAttributes rtts) {
        log.info("dto..." + dto);

        Long noticeId = service.write(dto);

        rtts.addFlashAttribute("msg", noticeId);

        return "redirect:/admin/notice/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long noticeId, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("noticeId: " + noticeId);

        NoticeDTO dto = service.read(noticeId);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(NoticeDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes rtts) {
        log.info("notice Modify..........");
        log.info("dto: " + dto);
        service.modify(dto);
        rtts.addAttribute("page", requestDTO.getPage());
        rtts.addAttribute("noticeId", dto.getNoticeId());

        return "redirect:/admin/notice/read";
    }

    @PostMapping("/remove")
    public String remove(long noticeId, RedirectAttributes rtts) {
        log.info("noticeId: " + noticeId);
        service.remove(noticeId);
        rtts.addFlashAttribute("msg", noticeId);
        return "redirect:/admin/notice/list";
    }
}
