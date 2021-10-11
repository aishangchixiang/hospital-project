package com.lb.controller.admin;

import com.lb.entity.LbAppointment;
import com.lb.service.LbAppointmentService;
import com.lb.service.LbPatientService;
import com.lb.vo.QueryVo;
import com.lb.vo.ResponseResult;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/appointment")
class AppointmentController {
    @Autowired
    private LbAppointmentService lbAppointmentService;
    @Autowired
    private LbPatientService lbPatientService;

    @RequestMapping("/manage")
    public String manage(QueryVo queryVo,Model model) {
        //查询预约记录
        PageQuery<LbAppointment> page = lbAppointmentService.findList(queryVo);
        model.addAttribute("page",page);
        model.addAttribute("pageNo",queryVo.getPageNo());
        model.addAttribute("patientName",queryVo.getPatientName());
        model.addAttribute("doctorName",queryVo.getDoctorName());
        model.addAttribute("path","/admin/appointment/manage");
        return "admin/appointmentManage";
    }


    @RequestMapping("/")
    public String doctorAddForm(LbAppointment lbAppointment,Model model) {
        model.addAttribute("patientList",lbPatientService.findAll());
        model.addAttribute("appointment",lbAppointment);
        return "admin/appointmentForm";
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String doctorEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("appointment",lbAppointmentService.findOne(id));
        return "admin/appointmentForm";
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseResult insert(@RequestBody LbAppointment lbAppointment) {
        return lbAppointmentService.insertAppointment(lbAppointment);
    }


    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody LbAppointment lbAppointment) {
        return lbAppointmentService.updateAppointment(lbAppointment);
    }


    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable Integer id){
        return lbAppointmentService.deleteById(id);
    }
}
