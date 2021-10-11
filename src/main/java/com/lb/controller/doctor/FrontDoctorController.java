package com.lb.controller.doctor;

import com.lb.common.Global;
import com.lb.entity.*;
import com.lb.service.*;
import com.lb.utils.PDFUtils;
import com.lb.vo.QueryVo;
import com.lb.vo.ResponseResult;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/doctor")
public class FrontDoctorController {
    @Autowired
    private LbDoctorService lbDoctorService;
    @Autowired
    private LbPatientService lbPatientService;
    @Autowired
    private LbAppointmentService lbAppointmentService;
    @Autowired
    private LbOptionService lbOptionService;
    @Autowired
    private LbDrugsService lbDrugsService;
    @Autowired
    private LbSeekService lbSeekService;


    @Value("${filepath.seekpdfpath}")
    private String path;


    @RequestMapping("/index")
    public String index(QueryVo queryVo, HttpSession session, Model model){
        LbUser user = (LbUser) session.getAttribute("user");
        queryVo.setUserId(user.getId());
        LbDoctor doctor = lbDoctorService.findOneByUserId(user.getId());

        //获取患者信息
        PageQuery<LbAppointment> page = lbAppointmentService.findListByDoctor(queryVo);
        model.addAttribute("page", page);
        model.addAttribute("pageNo",queryVo.getPageNo());
        model.addAttribute("path","/doctor/index");
        model.addAttribute("doctor", doctor);
        return "doctor/index";
    }


    @RequestMapping("/seek/{id}/{appointmentId}")
    public String seek(@PathVariable("id") Integer patientId,
                       @PathVariable("appointmentId") Integer appointmentId,
                       Model model){
        //获取患者信息
        model.addAttribute("patient",lbPatientService.findOne(patientId));
        //检查项目列表
        model.addAttribute("options",lbOptionService.findAll());
        //药品列表
        model.addAttribute("drugs", lbDrugsService.findAll());
        model.addAttribute("appointmentId", appointmentId);
        return "doctor/seek";
    }


    @ResponseBody
    @RequestMapping("/seekInfo")
    public ResponseResult seekInfo(@RequestBody Map map,HttpSession session) {
        return lbSeekService.save(map,session);
    }


    @ResponseBody
    @RequestMapping("/drug")
    public ResponseResult drug(@RequestBody Map map,HttpSession session) {
        return lbSeekService.update(map,session);
    }





    @RequestMapping( value = "/printSeek/{id}//{appointmentId}",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult printSeek(@PathVariable("id") Integer patientId,
                                    @PathVariable("appointmentId") Integer appointmentId,
                                    HttpSession session){
        LbUser user = (LbUser) session.getAttribute("user");
        LbDoctor doctor = lbDoctorService.findOneByUserId(user.getId());
        LbSeek seek = lbSeekService.findOneByPatientId(patientId,appointmentId,session);
        seek.setPatientName(lbPatientService.findOne(patientId).getName());
        seek.setDoctorName(doctor.getName());

        String message = PDFUtils.createSeekInfo(seek,lbOptionService,"D:/");
        return new ResponseResult(Global.SEEK_CODE_SUCCESS,message);
    }


    @ResponseBody
    @RequestMapping("/finishSeek/{id}")
    public ResponseResult finishSeek(@PathVariable Integer id) {
        LbAppointment appointment = new LbAppointment();
        appointment.setId(id);
        appointment.setStatus(Global.SEEK_CODE_DONE);
        return lbAppointmentService.updateAppointment(appointment);
    }


    @ResponseBody
    @RequestMapping(value = "/getList/{department}")
    public List<LbDoctor> getList(@PathVariable String department){
        return lbDoctorService.getListByDepartment(department);
    }
}
