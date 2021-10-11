package com.lb.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class QueryVo {
    private long pageNo = 1;
    private long pageSize = 5;
    private String patientName;
    private String doctorName;
    private Integer userId;
    private Integer patientId;
    private  Integer  appointmentId;
    private String certId;
    private Integer doctorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;
}
