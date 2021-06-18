package com.caffein.controller.client;

import com.caffein.dto.CafeDTO;
import com.caffein.dto.PageRequestDTO;
import com.caffein.dto.PageResultDTO;
import com.caffein.dto.ReviewDTO;
import com.caffein.entity.Cafe;
import com.caffein.entity.Member;
import com.caffein.entity.Review;
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

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/review")
public class ClientReviewController {

    private final ReviewService service;
    private final CafeService cafeService;

    @GetMapping("/write")
    public String write(@AuthenticationPrincipal Member member, Long cafeId, Model model) {
        log.info("write get...");

        model.addAttribute("cafeId", cafeId);
        model.addAttribute("member", member);

        return "/client/review/write";
    }

    @PostMapping("/write")
    public String writeRun(ReviewDTO dto, RedirectAttributes rtts) {
        log.info("dto..." + dto);

        Long reviewId = service.write(dto);

        updateAverageScore(dto.getTargetCafeId());

        rtts.addFlashAttribute("msg", reviewId);

        return "redirect:/review/readByCafeId" + "?reviewId=" + reviewId;
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

    @GetMapping("/read")
    public String read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long reviewId, Model model) {
        log.info("reviewId: " + reviewId);

        ReviewDTO reviewDTO = service.get(reviewId);

        log.info(reviewDTO);

        model.addAttribute("dto", reviewDTO);

        return "/client/review/read";
    }

    @GetMapping({"/listByCafeId"})
    public String listByCafeId(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long cafeId, Model model) {
        log.info("cafeId: " + cafeId);

        String targetCafeName = cafeService.get(cafeId).getName();
        PageResultDTO<ReviewDTO, Object[]> pageResultDTO = service.getListByCafeId(pageRequestDTO, cafeId);

        model.addAttribute("targetCafeName", targetCafeName);
        model.addAttribute("result", pageResultDTO);

        return "/client/review/listByCafeId";
    }

    @GetMapping("/readByCafeId")
    public String readByCafeId(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long reviewId, Model model) {
        log.info("reviewId: " + reviewId);

        ReviewDTO reviewDTO = service.get(reviewId);

        log.info(reviewDTO);

        model.addAttribute("targetCafeName", reviewDTO.getTargetCafeName());
        model.addAttribute("dto", reviewDTO);
        return "/client/review/readByCafeId";
    }
}
