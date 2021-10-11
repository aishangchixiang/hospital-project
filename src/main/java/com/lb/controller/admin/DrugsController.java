package com.lb.controller.admin;

import com.lb.entity.LbDrugs;
import com.lb.service.LbDrugsService;
import com.lb.vo.ResponseResult;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/drugs")
public class DrugsController {
    @Autowired
    private LbDrugsService lbDrugsService;

    @RequestMapping("/manage")
    public String manage(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                         @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) String type,
                         Model model) {
        //分页查询
        PageQuery<LbDrugs> page = lbDrugsService.findList(pageNo,pageSize,name,type);
        model.addAttribute("page",page);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("name",name);
        model.addAttribute("type",type);
        model.addAttribute("path","/admin/drugs/manage");
        return "admin/drugsManage";
    }


    @RequestMapping("/")
    public String addForm(LbDrugs lbDrugs,Model model) {
        model.addAttribute("drugs",lbDrugs);
        return "admin/drugsForm";
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("drugs",lbDrugsService.findOne(id));
        return "admin/drugsForm";
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseResult insert(@RequestBody LbDrugs lbDrugs) {
        return lbDrugsService.insertDrugs(lbDrugs);
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody LbDrugs lbDrugs) {
        return lbDrugsService.updateDrugs(lbDrugs);
    }


    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable Integer id){
        return lbDrugsService.deleteDrugs(id);
    }
}
