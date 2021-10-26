package org.zerock.sb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.PageResponseDTO;
import org.zerock.sb.dto.ReplyDTO;
import org.zerock.sb.service.ReplyService;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/list/{bno}")
    public PageResponseDTO<ReplyDTO> getListOfBoard(@PathVariable("bno") Long bno, PageRequestDTO pageRequestDTO){

        return replyService.getListOfBoard(bno, pageRequestDTO);
    }

    @PostMapping("")
    public PageResponseDTO<ReplyDTO> register(@RequestBody ReplyDTO replyDTO){

        replyService.register(replyDTO);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(-1).build();

        return replyService.getListOfBoard(replyDTO.getBno(), pageRequestDTO);
    }

    @DeleteMapping("/{bno}/{rno}")
    public PageResponseDTO<ReplyDTO> remove(
            @PathVariable("bno") Long bno,
            @PathVariable("rno") Long rno,
            PageRequestDTO requestDTO)
    {

        return replyService.remove(bno, rno, requestDTO);
    }

    @PutMapping("/{bno}/{rno}")
    public PageResponseDTO<ReplyDTO> modify(
            @PathVariable("bno") Long bno,
            @PathVariable("rno") Long rno,
            @RequestBody ReplyDTO replyDTO,
            PageRequestDTO requestDTO){

        log.info("bno: "+ bno);
        log.info("rno: "+ rno);
        log.info("replyDTO: "+replyDTO);

        return replyService.modify(replyDTO,requestDTO);
    }
}
