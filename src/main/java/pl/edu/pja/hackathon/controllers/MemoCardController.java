package pl.edu.pja.hackathon.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import pl.edu.pja.hackathon.model.memocard.MemoCard;
import pl.edu.pja.hackathon.services.MemoCardService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class MemoCardController {

    private final MemoCardService memoCardService;

    @GetMapping("/memocards/page/{page}")
    public ModelAndView getAllMemoCardsPaged(@PathVariable int page) {
        ModelAndView modelAndView = new ModelAndView("showMemoCards");
        PageRequest pageable = PageRequest.of(page - 1, 8);
        Page<MemoCard> memoCardPage = memoCardService.getAllMemoCardsPaged(pageable);
        System.out.println(memoCardPage.get().count());
        int totalPages = memoCardPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("activeMemoCardList", true);
        modelAndView.addObject("memocards", memoCardPage.getContent());
        System.out.println("Am i here");
        return modelAndView;
    }
}