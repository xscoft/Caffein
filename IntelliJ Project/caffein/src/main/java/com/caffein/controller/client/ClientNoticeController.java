package com.caffein.controller.client;

import com.caffein.dto.NoticeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/notice")
public class ClientNoticeController {

    private final NoticeService service;

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/notice/list";
    }

    @GetMapping({"/list"})
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list............" + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
        return "/client/notice/list";
    }

    @GetMapping("/read")
    public String read(long noticeId, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("noticeId: " + noticeId);

        NoticeDTO dto = service.read(noticeId);

        model.addAttribute("dto", dto);

        return "/client/notice/read";
    }
}
