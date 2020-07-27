package com.xsy.job.service.impl;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.internal.config.LiteJobConfigurationGsonFactory;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.xsy.job.common.ZkNodeConstants;
import com.xsy.job.dao.JobInitInfoMapper;
import com.xsy.job.model.JobInitInfo;
import com.xsy.job.service.JobInitInfoService;
import com.xsy.job.service.util.TimeCronUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author xueshaoyi
 * @Date 2022/07/26 下午9:15
 **/

@Slf4j
@Service
public class JobInitInfoServiceImpl implements JobInitInfoService {

    public static final String STATEGY_JOB_CLASS = "com.dangdang.ddframe.job.lite.api.strategy.impl.RotateServerByNameJobShardingStrategy";

    @Resource
    private JobInitInfoMapper jobInitInfoMapper;


    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Override
    public void addJobInfo(String jobName, String jobCron, String executerClassName, String executeParam) {
        JobInitInfo initInfo = jobInitInfoMapper.getInfoByJobName(jobName, executerClassName);
        if (StringUtils.isEmpty(jobCron)) {
            jobCron = "";
        }

        String[] classs = executerClassName.split("\\.");
        String className = classs[classs.length - 1];
        String job = className + "_" + jobName;
        String jobsNode = ZkNodeConstants.JOBS.replace(ZkNodeConstants.JOB, className);
        String jobNode = jobsNode + "/" + job;
        log.info("jobNode {}",jobNode);

        if (initInfo != null) {
            jobInitInfoMapper.updateJobCronInfo(jobName, executerClassName, jobCron);

            String jobConfig = initConfig(job, initInfo.getExecuterClassName(), jobCron, 1, initInfo.getExecuteParam(), STATEGY_JOB_CLASS);

            regCenter.update(jobNode, jobConfig);
        } else {
            JobInitInfo jobInitInfo = new JobInitInfo();
            jobInitInfo.setJobName(jobName);
            jobInitInfo.setExecuterClassName(executerClassName);
            jobInitInfo.setShardingTotalCount(1);
            jobInitInfo.setTriggerStatus(true);
            jobInitInfo.setExecuteParam(executeParam);
            jobInitInfo.setJobCron(jobCron);
            jobInitInfoMapper.insertSelective(jobInitInfo);

            String jobConfig = initConfig(job, jobInitInfo.getExecuterClassName(), jobCron, 1, jobInitInfo.getExecuteParam(), STATEGY_JOB_CLASS);

            regCenter.persist(jobNode, jobConfig);

        }
    }


    @Override
    public void stopJob(String jobName, String executerClassName) {
        JobInitInfo jobInitInfo = jobInitInfoMapper.getInfoByJobName(jobName, executerClassName);
        String[] classs = executerClassName.split("\\.");
        String className = classs[classs.length - 1];
        String job = className + "_" + jobName;
        String jobsNode = ZkNodeConstants.JOBS.replace(ZkNodeConstants.JOB, className);
        String jobNode = jobsNode + "/" + job;
        log.info("jobNode {}",jobNode);
        regCenter.remove(jobNode);
        // update db trigger status
    }

    private String initConfig(String jobName, String jobClassName, String cron, int shardingTotalCount, String jobParameter, String shardingStrategyClass) {
        LiteJobConfiguration
		        liteJobConfiguration = LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobName, cron, shardingTotalCount).jobParameter(jobParameter).build(), jobClassName)).overwrite(true)
		                                                   .jobShardingStrategyClass(shardingStrategyClass).build();

        String configJson = LiteJobConfigurationGsonFactory.toJson(liteJobConfiguration);
        return configJson;
    }
}
