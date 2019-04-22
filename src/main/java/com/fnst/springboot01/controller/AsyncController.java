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
		// ���ü�����:�������俪ʼ����ɡ��쳣����ʱ���¼��Ļص�����
		asyncContext.addListener(new AsyncListener() {
			@Override
			public void onTimeout(AsyncEvent event) throws IOException {
				System.out.println("��ʱ��...");
				// ��һЩ��ʱ�����ز���...
			}

			@Override
			public void onStartAsync(AsyncEvent event) throws IOException {
				System.out.println("�߳̿�ʼ");
			}

			@Override
			public void onError(AsyncEvent event) throws IOException {
				System.out.println("��������" + event.getThrowable());
			}

			@Override
			public void onComplete(AsyncEvent event) throws IOException {
				System.out.println("ִ�����");
				// ���������һЩ������Դ�Ĳ���...
			}
		});
		// ���ó�ʱʱ��
		asyncContext.setTimeout(10000);
		asyncContext.start(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					System.out.println("�ڲ��̣߳�" + Thread.currentThread().getName());
					asyncContext.getResponse().setCharacterEncoding("utf-8");
					asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
					asyncContext.getResponse().getWriter().println("�����첽�����󷵻�");
				} catch (Exception e) {
					System.out.println("�쳣��" + e);
				}
				// �첽�������֪ͨ
				// ��ʱ������������
				asyncContext.complete();
			}
		});
		// ��ʱ֮�� request���߳������Ѿ��ͷ���
		System.out.println("���̣߳�" + Thread.currentThread().getName());
	}

	@RequestMapping(value = "/email/callableReq")
	@ResponseBody
	public Callable callableReq() {

		System.out.println("�ⲿ�̣߳�" + Thread.currentThread().getName());

		return new Callable<String>() {

			@Override
			public String call() throws Exception {
				Thread.sleep(10000);
				System.out.println("�ڲ��̣߳�" + Thread.currentThread().getName());
				return "callable!";
			}
		};
	}

	@RequestMapping(value = "/email/webAsyncReq")
	@ResponseBody
	public WebAsyncTask<String> webAsyncReq() {
		System.out.println("�ⲿ�̣߳�" + Thread.currentThread().getName());
		Callable<String> result = () -> {
			System.out.println("�ڲ��߳̿�ʼ��" + Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (Exception e) {
				// TODO: handle exception
			}
			// logger.info("���̷߳���");
			System.out.println("�ڲ��̷߳��أ�" + Thread.currentThread().getName());
			return "success";
		};
		WebAsyncTask<String> wat = new WebAsyncTask<String>(5000L, result);
		wat.onTimeout(new Callable<String>() {

			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				return "��ʱ";
			}
		});
		return wat;
	}

	@RequestMapping(value = "/email/deferredResultReq")

	@ResponseBody
	public DeferredResult<String> deferredResultReq() {
		System.out.println("�ⲿ�̣߳�" + Thread.currentThread().getName());
		// ���ó�ʱʱ��
		DeferredResult<String> result = new DeferredResult<String>(5 * 1000L);
		// ����ʱ�¼� ����ί�л���
		result.onTimeout(new Runnable() {

			@Override
			public void run() {
				System.out.println("DeferredResult��ʱ");
				result.setResult("��ʱ��!");
			}
		});
		result.onCompletion(new Runnable() {

			@Override
			public void run() {
				// ��ɺ�
				System.out.println("�������");
			}
		});
		myThreadPoolTaskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				// ����ҵ���߼�
				System.out.println("�ڲ��̣߳�" + Thread.currentThread().getName());
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (Exception e) {
					// TODO: handle exception
				}
				// ���ؽ��
				result.setResult("DeferredResult!!");
			}
		});
		return result;
	}
}
