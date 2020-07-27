package com.xsy.job.model;

import lombok.Data;

import java.util.Date;

/**
 * @author xueshaoyi
 * @Date 2022/07/26 下午9:15
 **/

@Data
public class JobInitInfo {
    private long id;

    private String jobName;

    private String jobCron;

    private String executerClassName;

    private String strategyClass;

    private Integer shardingTotalCount;

    private Integer intervalTime;

    private String executeParam;

    private Boolean triggerStatus;

    private Long triggerLastTime;

    private Long triggerNextTime;

    private Date createTime;

    private Date updateTime;


}