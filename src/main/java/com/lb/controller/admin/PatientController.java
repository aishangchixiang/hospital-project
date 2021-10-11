package com.lb.controller.admin;

import com.lb.entity.LbPatient;
import com.lb.service.LbPatientService;
import com.lb.vo.QueryVo;
import com.lb.vo.ResponseResult;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/patient")
public class PatientController {
    @Autowired
    private LbPatientService lbPatientService;

    @RequestMapping("/manage")
    public String manage(QueryVo queryVo,Model model) {
        //查询患者的集合数据
        PageQuery<LbPatient> page = lbPatientService.findList(queryVo);
        model.addAttribute("page",page);
        model.addAttribute("pageNo",queryVo.getPageNo());
        model.addAttribute("name",queryVo.getPatientName());
        model.addAttribute("certId",queryVo.getCertId());
        model.addAttribute("path","/admin/patient/manage");
        return "admin/patientManage";
    }


    @RequestMapping("/")
    public String doctorAddForm(LbPatient lbPatient,Model model) {
        model.addAttribute("patient",lbPatient);
        return "admin/patientForm";
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String doctorEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("patient",lbPatientService.findOne(id));
        return "admin/patientForm";
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseResult insert(@RequestBody LbPatient lbPatient) {
        return lbPatientService.insertPatient(lbPatient);
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody LbPatient lbPatient) {
        return lbPatientService.updatePatient(lbPatient);
    }


    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable Integer id){
        return lbPatientService.deleteById(id);
    }
}
