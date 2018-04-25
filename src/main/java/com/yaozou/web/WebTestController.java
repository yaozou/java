package com.yaozou.web;

import com.yaozou.service.TestService;
import com.yaozou.vo.TestVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping(value="/web")
public class WebTestController {
    @Resource
    private TestService testService;

    @RequestMapping(value = "/list/{desc}",method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value="name")String name, String key,
                             @RequestBody TestVO testVO, @PathVariable("desc") String desc){
        ModelAndView modelAndView = new ModelAndView("");
        modelAndView.addObject(name,key);
        return modelAndView;
    }

    @RequestMapping(value="/detail",method = RequestMethod.POST)
    public String detail(Long id,Model model){
        model.addAttribute("qq","www");
        return "/detail";
    }

}
