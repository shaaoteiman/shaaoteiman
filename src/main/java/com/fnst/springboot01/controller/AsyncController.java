package com.fnst.springboot01.controller;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

@Controller
public class AsyncController {

	@Bean
	private ThreadPoolTaskExecutor myThreadPoolTaskExecutor(){
		return new ThreadPoolTaskExecutor();
	};

	@Resource
	private ThreadPoolTaskExecutor myThreadPoolTaskExecutor;
	
	@RequestMapping(value = "/email/servletReq")
	public void servletReq(HttpServletRequest request, HttpServletResponse response) {

		AsyncContext asyncContext = request.startAsync();
		// 设置监听器:可设置其开始、完成、异常、超时等事件的回调处理
		asyncContext.addListener(new AsyncListener() {
			@Override
			public void onTimeout(AsyncEvent event) throws IOException {
				System.out.println("超时了...");
				// 做一些超时后的相关操作...
			}

			@Override
			public void onStartAsync(AsyncEvent event) throws IOException {
				System.out.println("线程开始");
			}

			@Override
			public void onError(AsyncEvent event) throws IOException {
				System.out.println("发生错误：" + event.getThrowable());
			}

			@Override
			public void onComplete(AsyncEvent event) throws IOException {
				System.out.println("执行完成");
				// 这里可以做一些清理资源的操作...
			}
		});
		// 设置超时时间
		asyncContext.setTimeout(10000);
		asyncContext.start(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					System.out.println("内部线程：" + Thread.currentThread().getName());
					asyncContext.getResponse().setCharacterEncoding("utf-8");
					asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
					asyncContext.getResponse().getWriter().println("这是异步的请求返回");
				} catch (Exception e) {
					System.out.println("异常：" + e);
				}
				// 异步请求完成通知
				// 此时整个请求才完成
				asyncContext.complete();
			}
		});
		// 此时之类 request的线程连接已经释放了
		System.out.println("主线程：" + Thread.currentThread().getName());
	}

	@RequestMapping(value = "/email/callableReq")
	@ResponseBody
	public Callable callableReq() {

		System.out.println("外部线程：" + Thread.currentThread().getName());

		return new Callable<String>() {

			@Override
			public String call() throws Exception {
				Thread.sleep(10000);
				System.out.println("内部线程：" + Thread.currentThread().getName());
				return "callable!";
			}
		};
	}

	@RequestMapping(value = "/email/webAsyncReq")
	@ResponseBody
	public WebAsyncTask<String> webAsyncReq() {
		System.out.println("外部线程：" + Thread.currentThread().getName());
		Callable<String> result = () -> {
			System.out.println("内部线程开始：" + Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (Exception e) {
				// TODO: handle exception
			}
			// logger.info("副线程返回");
			System.out.println("内部线程返回：" + Thread.currentThread().getName());
			return "success";
		};
		WebAsyncTask<String> wat = new WebAsyncTask<String>(5000L, result);
		wat.onTimeout(new Callable<String>() {

			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				return "超时";
			}
		});
		return wat;
	}

	@RequestMapping(value = "/email/deferredResultReq")

	@ResponseBody
	public DeferredResult<String> deferredResultReq() {
		System.out.println("外部线程：" + Thread.currentThread().getName());
		// 设置超时时间
		DeferredResult<String> result = new DeferredResult<String>(5 * 1000L);
		// 处理超时事件 采用委托机制
		result.onTimeout(new Runnable() {

			@Override
			public void run() {
				System.out.println("DeferredResult超时");
				result.setResult("超时了!");
			}
		});
		result.onCompletion(new Runnable() {

			@Override
			public void run() {
				// 完成后
				System.out.println("调用完成");
			}
		});
		myThreadPoolTaskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				// 处理业务逻辑
				System.out.println("内部线程：" + Thread.currentThread().getName());
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (Exception e) {
					// TODO: handle exception
				}
				// 返回结果
				result.setResult("DeferredResult!!");
			}
		});
		return result;
	}
}
