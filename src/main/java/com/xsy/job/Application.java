package com.xsy.job;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.xsy.job.annotation.EnableElasticJob;
import com.xsy.job.common.ZkNodeConstants;
import com.xsy.job.listener.JobInitListenerManager;
import com.xsy.job.listener.JobLeaderListenerManager;
import com.xsy.job.task.EsJobTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author xueshaoyi
 * @Date 2020/7/26 下午8:57
 **/
@SpringBootApplication(scanBasePackages = {"com.xsy.job"}, exclude = {DataSourceAutoConfiguration.class})
@EnableElasticJob(basePackages = {"com.xsy.job.task"})
public class Application {
	@Resource
	private ZookeeperRegistryCenter regCenter;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	/**
	 * 对EsJobTask 的子集任务的监听
	 * @return
	 */
	@Bean(initMethod = "start")
	public JobInitListenerManager jobInitListenerManager() {
		String jobName = EsJobTask.class.getSimpleName();
		return new JobInitListenerManager(jobName);
	}

	/**
	 * 对esJob主节点的监听
	 * @return
	 */
	@Bean(initMethod = "start")
	public JobLeaderListenerManager jobInitLeaderListnerManager() {
		return new JobLeaderListenerManager(regCenter, ZkNodeConstants.INIT_JOB_Name);
	}
}
