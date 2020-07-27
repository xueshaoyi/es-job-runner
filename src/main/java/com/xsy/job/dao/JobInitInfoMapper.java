package com.xsy.job.dao;


import com.xsy.job.annotation.DataSource;
import com.xsy.job.model.JobInitInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xueshaoyi
 * @Date 2019/10/22 下午4:15
 **/
@DataSource("esJobDataSource")
public interface JobInitInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(JobInitInfo record);

    JobInitInfo getInfoByJobName(@Param("jobName") String jobName, @Param("exectuerClass") String className);

    void updateJobCronInfo(@Param("jobName") String jobName, @Param("exectuerClass") String className,
                           @Param("cron") String cron);


}