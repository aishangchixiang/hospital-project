package com.lb.controller.admin;

import com.lb.entity.LbIllness;
import com.lb.service.LbIllnessService;
import com.lb.vo.ResponseResult;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/illness")
public class IllnessController {
    @Autowired
    private LbIllnessService lbIllnessService;

    @RequestMapping("/manage")
    public String manage(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                         @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) String type,
                         Model model) {
        //分页查询
        PageQuery<LbIllness> page = lbIllnessService.findList(pageNo,pageSize,name);
        model.addAttribute("page",page);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("name",name);
        model.addAttribute("type",type);
        model.addAttribute("path","/admin/illness/manage");
        return "admin/IllnessManage";
    }


    @RequestMapping("/")
    public String addForm(LbIllness lbIllness,Model model) {
        model.addAttribute("illness",lbIllness);
        return "admin/IllnessForm";
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("illness",lbIllnessService.findOne(id));
        return "admin/IllnessForm";
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseResult insert(@RequestBody LbIllness lbIllness) {
        return lbIllnessService.insertIllness(lbIllness);
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody LbIllness lbIllness) {
        return lbIllnessService.updateIllness(lbIllness);
    }


    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable Integer id){
        return lbIllnessService.deleteIllness(id);
    }
}
