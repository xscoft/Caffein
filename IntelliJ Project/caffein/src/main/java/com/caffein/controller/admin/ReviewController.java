package com.caffein.controller.admin;

import com.caffein.dto.*;
import com.caffein.entity.Cafe;
import com.caffein.entity.Member;
import com.caffein.entity.Review;
import com.caffein.service.CafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.caffein.service.ReviewService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/review")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;
    private final CafeService cafeService;

    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/admin/review/list";
    }

    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list............" + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/read")
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long reviewId, Model model) {
        log.info("reviewId: " + reviewId);

        ReviewDTO reviewDTO = service.get(reviewId);

        log.info(reviewDTO);

        model.addAttribute("dto", reviewDTO);
    }

    @PostMapping("/remove")
    public String remove(Long reviewId, RedirectAttributes rtts) {

        log.info("reviewId: " + reviewId);

        service.remove(reviewId);

        rtts.addAttribute("msg", reviewId);

        return "redirect:/admin/review/list";
    }

    @GetMapping("/write")
    public void write(Long cafeId, Model model) {
        log.info("write get...");

        model.addAttribute("cafeId", cafeId);
    }

    @PostMapping("/write")
    public String writeRun(ReviewDTO dto, RedirectAttributes rtts) {
        log.info("dto..." + dto);

        Long reviewId = service.write(dto);

        updateAverageScore(dto.getTargetCafeId());

        rtts.addFlashAttribute("msg", reviewId);

        return "redirect:/admin/review/readByCafeId" + "?reviewId=" + reviewId;
    }

    public void updateAverageScore(Long cafeId) {
        log.info("update Cafe Average Score");

        List<Review> reviewListByCafeId = service.getReviewsByCafeId(cafeId);

        int sumScore = 0;

        for (int i = 0; i < reviewListByCafeId.size(); i++) {
            Review review = reviewListByCafeId.get(i);
            sumScore = sumScore + review.getScore();
        }

        int averageScore = (int) (sumScore / reviewListByCafeId.size());

        CafeDTO cafeDTO = CafeDTO.builder().cafeId(cafeId).score(averageScore).build();
        cafeService.updateScore(cafeDTO);
    }

    @GetMapping({"/listByCafeId"})
    public void listByCafeId(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long cafeId, Model model) {
        log.info("cafeId: " + cafeId);

        String targetCafeName = cafeService.get(cafeId).getName();
        PageResultDTO<ReviewDTO, Object[]> pageResultDTO = service.getListByCafeId(pageRequestDTO, cafeId);

        model.addAttribute("targetCafeName", targetCafeName);
        model.addAttribute("result", pageResultDTO);
    }

    @GetMapping("/readByCafeId")
    public void readByCafeId(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long reviewId, Model model) {
        log.info("reviewId: " + reviewId);

        ReviewDTO reviewDTO = service.get(reviewId);

        log.info(reviewDTO);
        model.addAttribute("targetCafeName", reviewDTO.getTargetCafeName());
        model.addAttribute("dto", reviewDTO);
    }
}
