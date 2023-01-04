package com.demo;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(ProcessEngineCoverageExtension.class)
@SpringBootTest
class ApplicationTests {

	@Test
	@Deployment(resources = "QA-process.bpmn")
	public void contextLoads() {
		// Create a HashMap to put in variables for the process instance
		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("approved", true);
		variable.put("content","3211125");
		// Start process with Java API and variables
		ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("twitter-QA-process", variable);

		List<Task> taskList = taskService().createTaskQuery().taskCandidateGroup("management")
				.processInstanceId(processInstance.getId()).list();

		assertThat(taskList).isNotNull();
		assertThat(taskList).hasSize(1);
		
		Task task = taskList.get(0);

		taskService().complete(task.getId(), variable);
		// Make assertions on the process instance
		assertThat(processInstance).isEnded();
	}

}
