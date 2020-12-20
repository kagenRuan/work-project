package com.ruan.yuanyuan;

import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.hystrix.ProductHystrixCommand;
import com.ruan.yuanyuan.mesage.enums.RabbitMqExchangeEnum;
import com.ruan.yuanyuan.mesage.enums.RabbitMqRoutingKeyEnum;
import com.ruan.yuanyuan.rabbitmq.RabbitMessageProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceApplicationTests {
    /**
     * @Author: ruanyuanyuan
     * @Date: 2020/12/18 10:45
     * @Description:
     * @param args: 
     * @return: void
     **/
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HystrixRequestContext requestContext = HystrixRequestContext.initializeContext();
        ProductHystrixCommand productHystrixCommand = new ProductHystrixCommand("1");


        //同步
        ResultObject executeResultObject = productHystrixCommand.execute();
        //异步
        Future<ResultObject> future = productHystrixCommand.queue();
        ResultObject futureResultObject = future.get();



        //observe阻塞
        Observable<ResultObject> result =  productHystrixCommand.observe();
        ResultObject resultObject = result.toBlocking().single();
        //observe非阻塞方式
        result.subscribe(new Subscriber<ResultObject>() {
            @Override
            public void onCompleted() {
                System.out.println("observe - subscribe -> onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("observe - subscribe -> onError");
            }

            @Override
            public void onNext(ResultObject resultObject) {
                System.out.println("observe - subscribe -> onNext");
            }
        });


        //toObservable阻塞
        Observable<ResultObject> toObservableResult =  productHystrixCommand.toObservable();
        ResultObject toObservableResultObject = toObservableResult.toBlocking().single();
        //toObservable非阻塞方式
        Observable<ResultObject> toObservableResult1 =  productHystrixCommand.toObservable();
        toObservableResult1.subscribe(new Subscriber<ResultObject>() {
            @Override
            public void onCompleted() {
                System.out.println("toObservable - subscribe -> onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("toObservable - subscribe -> onError");
            }

            @Override
            public void onNext(ResultObject resultObject) {
                System.out.println("toObservable - subscribe -> onNext");
            }
        });


        requestContext.close();
    }


}
