package com.lb.controller.admin;

import com.lb.entity.LbDoctor;
import com.lb.service.LbDoctorService;
import com.lb.vo.ResponseResult;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin/doctor")
public class DoctorController {
    @Autowired
    private LbDoctorService lbDoctorService;


    @RequestMapping("/manage")
    public String doctorManage(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String certId,
                               Model model) {
        //查询医生的集合数据
        PageQuery<LbDoctor> page = lbDoctorService.findList(pageNo,pageSize,name,certId);
        model.addAttribute("page",page);
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("name",name);
        model.addAttribute("certId",certId);
        model.addAttribute("path","/admin/doctor/manage");
        return "admin/doctorManage";
    }


    @RequestMapping("/")
    public String doctorAddForm(LbDoctor lbDoctor,Model model) {
        model.addAttribute("doctor",lbDoctor);
        return "admin/doctorForm";
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String doctorEditForm(@PathVariable Integer id,Model model) {
        model.addAttribute("doctor",lbDoctorService.findOne(id));
        return "admin/doctorForm";
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseResult insert(@RequestBody LbDoctor lbDoctor) {
        return lbDoctorService.insertDoctor(lbDoctor);
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody LbDoctor lbDoctor) {
        return lbDoctorService.updateDoctor(lbDoctor);
    }


    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable Integer id){
        return lbDoctorService.deleteDoctor(id);
    }


    @ResponseBody
    @RequestMapping(value = "/getList/{department}")
    public List<LbDoctor> getList(@PathVariable String department){
        return lbDoctorService.getListByDepartment(department);
    }
}
