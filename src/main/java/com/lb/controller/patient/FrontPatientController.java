package com.lb.controller.patient;

import com.lb.common.Global;
import com.lb.entity.LbAppointment;
import com.lb.entity.LbPatient;
import com.lb.entity.LbUser;
import com.lb.service.LbAppointmentService;
import com.lb.service.LbPatientService;
import com.lb.utils.PDFUtils;
import com.lb.vo.QueryVo;
import com.lb.vo.ResponseResult;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/patient")
public class FrontPatientController {
    @Value("${filepath.appointpdf}")
    private String appointPath;

    @Autowired
    private LbAppointmentService lbAppointmentService;
    @Autowired
    private LbPatientService lbPatientService;



    @RequestMapping("/index")
    public String index(HttpSession session,QueryVo queryVo, Model model) {
        //查询当前登录用户的预约记录
        LbUser user = (LbUser) session.getAttribute("user");
        queryVo.setUserId(user.getId());
        PageQuery<LbAppointment> page = lbAppointmentService.findList(queryVo);
        model.addAttribute("page",page);
        model.addAttribute("pageNo",queryVo.getPageNo());
        model.addAttribute("path","/patient/index");
        return "patient/appointmentHistory";
    }


    @RequestMapping(value = "/appointment", method = RequestMethod.GET)
    public String form(HttpSession session,Model model) {
        LbUser user = (LbUser) session.getAttribute("user");
        LbPatient patient = lbPatientService.findOneByUserId(user.getId());
        //将患者的信息放到model
        model.addAttribute("patient",patient);
        return "patient/appointmentForm";
    }


    @ResponseBody
    @RequestMapping(value = "/appointment", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody LbAppointment appointment) {
        appointment.setStatus(Global.SEEK_CODE_NONE);
        Integer appointmentId = lbAppointmentService.insertReturnId(appointment);
        LbPatient patient = new LbPatient();
        patient.setId(appointment.getPatientId());
        patient.setAppointmentId(appointmentId);
        lbPatientService.updatePatient(patient);
        return new ResponseResult(Global.SAVE_CODE_SUCCESS,Global.SAVE_APPOINTMENT_SUCCESS);
    }


    @ResponseBody
    @RequestMapping(value = "/appointment/createPDF",method = RequestMethod.POST)
    public ResponseResult createPDF(HttpSession session) {
        //获取当前用户最近一次的预约记录
        LbUser user = (LbUser) session.getAttribute("user");
        LbPatient patient = lbPatientService.findOneByUserId(user.getId());
        LbAppointment appointment = lbAppointmentService.findOne(patient.getAppointmentId());
        return new ResponseResult(Global.APPOINTMENT_CODE_SUCCESS, PDFUtils.createAppointment(appointment,"D:/"));
    }


    @RequestMapping("/search")
    public String search() {
        return "patient/search";
    }


    @ResponseBody
    @RequestMapping("/searchInfo")
    public Map<String, List> searchInfo(String type,String name) {
        return lbPatientService.findInfo(type,name);
    }


}
