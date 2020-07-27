package com.xsy.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.xsy.job.annotation.ElasticTask;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xueshaoyi
 * @Date 2020/7/26 下午9:07
 **/
@Slf4j
@ElasticTask(jobName = "EsJobTask", cron = "0 * * ? 10 *", manualTrigger = true,
		description = "初始化各自动测试任务")
public class EsJobTask implements SimpleJob {

	public void execute(ShardingContext shardingContext) {

		log.info("run task job {}", shardingContext.getJobParameter());

	}
}
