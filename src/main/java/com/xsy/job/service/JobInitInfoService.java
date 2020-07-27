package com.xsy.job.service;


import com.xsy.job.model.JobInitInfo;

import java.util.List;

/**
 * @author xueshaoyi
 * @Date 2022/07/26 下午9:15
 **/
public interface JobInitInfoService {


    void addJobInfo(String jobName, String jobCron, String executerClassName, String executeParam);


    void stopJob(String jobName, String executerClassName);
}
