package com.fnst.springboot01;

import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fnst.springboot01.controller.AsyncTask;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncTest {

	private final static Logger log = LoggerFactory.getLogger(AsyncTest.class);

	@Autowired
	private AsyncTask asyncTask;

	@Test
	public void testTask1(){
		log.info("start");
		asyncTask.task1();
		log.info("finish");
	}

	@Test
	public void testTask2()  {
		log.info("start");
		Future<String> future = asyncTask.task2();
		while (true) {
			if (future.isDone()) {
				try {
					log.info("result is " + future.get());
				} catch (Exception e) {
					e.printStackTrace();
				} 
				break;
			}
		}
		log.info("finish");
	}

}
