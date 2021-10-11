package com.lb.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/home")
public class HomeController {
    //主页
    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("cur","index");
        return "home/index";
    }

    //医院介绍
    @RequestMapping("/introduction")
    public String introduction(Model model) {
        model.addAttribute("cur","introduction");
        return "home/introduction";
    }

    //专家团队
    @RequestMapping("/expert")
    public String expert(Model model) {
        model.addAttribute("cur","expert");
        return "home/expert";
    }

    //患者服务
    @RequestMapping("/service")
    public String service(Model model) {
        model.addAttribute("cur","service");
        return "home/service";
    }

    //坐诊时间
    @RequestMapping("/seekGuide")
    public String seekGuide(Model model) {
        model.addAttribute("cur","seekGuide");
        return "home/seekGuide";
    }

    //就医指南
    @RequestMapping("/guide")
    public String guide(Model model) {
        model.addAttribute("cur","guide");
        return "home/guide";
    }

    //新闻中心
    @RequestMapping("/news")
    public String news(Model model) {
        model.addAttribute("cur","news");
        return "home/news";
    }

}
