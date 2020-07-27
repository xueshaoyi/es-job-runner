package com.xsy.job.controller;

import com.xsy.job.service.JobInitInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xueshaoyi
 * @Date 2020/7/27 下午3:41
 **/
@Slf4j
@RestController
@RequestMapping("/api/job")
public class JobManagerController {

	@Autowired
	private JobInitInfoService jobInitInfoService;

	@PostMapping("/add")
	private String addJob(String jobName, String jobCron, String executerClassName, String executeParam) {

		jobInitInfoService.addJobInfo(jobName, jobCron, executerClassName, executeParam);
		return "success";
	}

	@PostMapping("/stop")
	private String stopJob(String jobName, String executerClassName) {
		jobInitInfoService.stopJob(jobName, executerClassName);

		return "success";
	}
}
