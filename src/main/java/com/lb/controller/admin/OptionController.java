package com.lb.controller.admin;

import com.lb.entity.LbOption;
import com.lb.service.LbOptionService;
import com.lb.vo.ResponseResult;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/option")
public class OptionController {
    @Autowired
    private LbOptionService lbOptionService;

    @RequestMapping("/manage")
    public String manage(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                         @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) String type,
                         Model model) {
        //分页查询
        PageQuery<LbOption> page = lbOptionService.findList(pageNo,pageSize,name,type);
        model.addAttribute("page",page);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("name",name);
        model.addAttribute("type",type);
        model.addAttribute("path","/admin/option/manage");
        return "admin/optionManage";
    }


    @RequestMapping("/")
    public String addForm(LbOption lbOption,Model model) {
        model.addAttribute("option",lbOption);
        return "admin/optionForm";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("option",lbOptionService.findOne(id));
        return "admin/optionForm";
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseResult insert(@RequestBody LbOption lboption) {
        return lbOptionService.insertOption(lboption);
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody LbOption lboption) {
        return lbOptionService.updateOption(lboption);
    }


    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable Integer id){
        return lbOptionService.deleteOption(id);
    }
}
