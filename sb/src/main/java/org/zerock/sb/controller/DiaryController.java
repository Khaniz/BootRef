package org.zerock.sb.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.sb.dto.DiaryDTO;
import org.zerock.sb.dto.DiaryListDTO;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.PageResponseDTO;
import org.zerock.sb.service.DiaryService;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerPost(DiaryDTO diaryDTO, RedirectAttributes redirectAttributes){

        log.info("=========================");
        log.info(diaryDTO);

        Long dno = diaryService.register(diaryDTO);

        log.info("DNO: " + dno);

        redirectAttributes.addFlashAttribute("result", dno);

        return "redirect:/diary/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<DiaryListDTO> responseDTO = diaryService.getListWithFavorite(pageRequestDTO);

        model.addAttribute("res", responseDTO);

    }

    @GetMapping("/read")
    public void getRead(Long dno, PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("dto", diaryService.read(dno));
    }
}
