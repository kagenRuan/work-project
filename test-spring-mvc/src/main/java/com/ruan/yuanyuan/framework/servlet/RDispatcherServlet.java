package com.ruan.yuanyuan.framework.servlet;

import com.ruan.yuanyuan.framework.adapter.RHandlerAdapter;
import com.ruan.yuanyuan.framework.annoation.RController;
import com.ruan.yuanyuan.framework.annoation.RRequestMapping;
import com.ruan.yuanyuan.framework.annoation.RRequestParam;
import com.ruan.yuanyuan.framework.context.RApplicationContext;
import com.ruan.yuanyuan.framework.handler.RHander;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: ruanyuanyuan
 * Date: 2019-06-10
 * Time: 14:15
 * version:
 * Description: 手下springmvc,这里说下Dispatcher的原理主要是继承了HttpServlet
 */
public class RDispatcherServlet extends HttpServlet {

    /**
     * 用于获取到在web.xml配置文件中配置的文件路径值(也就是获取它classpath*:application-web.properties)
     */
    private static final String LOCATION ="contextConfigLocation";
    /**
     * handerMapping
     */
//    private List<RHander> handerMapping = new ArrayList<>();
    private Map<String,RHander> handerMapping = new ConcurrentHashMap<>();
    /**
     * handlerAdapter
     */
    private Map<RHander,RHandlerAdapter> handlerAdapters = new HashMap<>();

    private Map<String,Object> paramMapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    //初始化springMvc逻辑
    @Override
    public void init(ServletConfig config) throws ServletException {

        RApplicationContext applicationContext = new RApplicationContext(config.getInitParameter(LOCATION));
        Map<String,Object> map = applicationContext.getAll();
        System.err.println("DispatcherServlet 已启动并初始化完成");

        //请求解析
        initMultipartResolver(applicationContext);
        //国际化
        initLocaleResolver(applicationContext);
        //主题view层
        initThemeResolver(applicationContext);

        /**
         * 解析url与controller中的menthod的关系 重要
         */
        initHandlerMappings(applicationContext);
        /**
         * 适配，匹配url与方法 重要
         */
        initHandlerAdapters(applicationContext);


        //异常解析
        initHandlerExceptionResolvers(applicationContext);
        //试图转发
        initRequestToViewNameTranslator(applicationContext);

        /**
         * 解析模板内容
         */
        initViewResolvers(applicationContext);

        //
        initFlashMapManager(applicationContext);
    }

    /**
     * 请求到达后逻辑处理
     * @param request
     * @param response
     */
    private void doDispatch(HttpServletRequest request,HttpServletResponse response){

        RHander hander = getHandler(request);
        try {
            //根据请求的url查询Handeler中是否包含次url,没有则直接返回404，否则往下走
            if(null == hander){
                response.getWriter().write(404);
                return;
            }

            //获取Adapter
            RHandlerAdapter ha = getHandlerAdapter(hander);

            //执行请求
            ha.handle(request,response,hander);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 方法的适配器
     * @param hander
     * @return
     */
    private RHandlerAdapter getHandlerAdapter(RHander hander){
        if(handlerAdapters.isEmpty()){
            return null;
        }
        RHandlerAdapter handlerAdapter = handlerAdapters.get(hander);
        return handlerAdapter;
    }

    /**
     * url 与方法的映射关系
     * @param request
     * @return
     */
    private RHander getHandler(HttpServletRequest request){
        if(handerMapping.isEmpty()){
            return null;
        }

        /**
         * 获取请求资源地址
         */
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        /**
         * 将请求资源域名前面的部分替换为空，并且替换后的请求路径有多个//则替换为单个
         */
        url = url.replace(contextPath,"").replaceAll("/+","/");
        if(handerMapping.containsKey(url)){
         return handerMapping.get(url);
        }
        return null;

    }

    //请求解析
    private void initMultipartResolver(RApplicationContext applicationContext){
        System.out.println("====请求解析 start======");

        System.out.println("====请求解析 end======");

    }
    //国际化
    private  void initLocaleResolver(RApplicationContext applicationContext){
        System.out.println("====国际化 start======");
        System.out.println("====国际化 end======");

    }
    //主题view层
    private void initThemeResolver(RApplicationContext applicationContext){
        System.out.println("====主题view层 start======");
        System.out.println("====主题view层 end======");

    }

    //解析url与controller中的menthod的关系保存到HandlerMapping
    private void initHandlerMappings(RApplicationContext applicationContext){

        System.out.println("====解析url与controller中的menthod的关系 start======");

        Map<String,Object> ioc = applicationContext.getAll();
        if(ioc.isEmpty()){
            return;
        }

        /**
         * 拿到ioc容器中所有的beanDefition进行循环
         */
        for (Map.Entry<String,Object> bean:ioc.entrySet()){
           Class<?> clazz = bean.getValue().getClass();
           String url = "";

            /**
             * 首先判断类上是否添加@RController注解,如果没有则说明该路径不能被请求
             */
           boolean i =  clazz.isAnnotationPresent(RController.class);
           if(!clazz.isAnnotationPresent(RController.class)){
               continue;
           }

            /**
             * 判断类上是否有@RequestMapping注解，如果有则需要拿到设置的请求路径
             */
           if(clazz.isAnnotationPresent(RRequestMapping.class)){
               RRequestMapping rRequestMapping = clazz.getAnnotation(RRequestMapping.class);
               url = rRequestMapping.value();
           }


            /**
             * 拿到类里面的所以方法并循环，如果方法上有@RequestMapping注解，则拿到设置的请求路径和类上的请求路径进行拼接保存到HandlerMapping中
             */
            Method[] methods = clazz.getMethods();
           for(Method method:methods){
               if(method.isAnnotationPresent(RRequestMapping.class)){
                   RRequestMapping rRequestMapping = method.getAnnotation(RRequestMapping.class);
                   handerMapping.put(url+rRequestMapping.value(),new RHander(bean.getValue(),method));
               }
           }
        }

        System.out.println("====解析url与controller中的menthod的关系 将请求url与controller中的方法映射关系保存到HandlerMapping中 end======");


    }
    //初始化Adapter,主要是把HandlerMapping中的添加的方法里面的参数拿出来并添加到Adapter中以便在请求是使用
    private void initHandlerAdapters(RApplicationContext applicationContext){
        System.out.println("====适配，匹配url与方法 start======");

        if(handerMapping.isEmpty()){
            return;
        }

        for(Map.Entry<String,RHander> bean:handerMapping.entrySet()){
            //获取方法中所以的参数，并且循环
            Class<?>[]  classes = bean.getValue().method.getParameterTypes();

            for(int i=0;i<classes.length;i++){
                Class clas = classes[i];
                if(clas == HttpServletRequest.class || clas == HttpServletResponse.class){
                    paramMapping.put(clas.getName(),i);

                }
            }

            Annotation[][] annoations = bean.getValue().method.getParameterAnnotations();

            for(int i=0;i<annoations.length;i++){
                for(Annotation annotation:annoations[i]){
                    if(annotation instanceof RRequestParam){
                        String name = ((RRequestParam) annotation).value();
                        paramMapping.put(name,i);
                    }
                }
            }

            handlerAdapters.put(bean.getValue(),new RHandlerAdapter(paramMapping));
        }


        System.out.println("====适配，匹配url与方法 end======");

    }
    //异常解析
    private void initHandlerExceptionResolvers(RApplicationContext applicationContext){
        System.out.println("====异常解析 start======");
        System.out.println("====异常解析 end======");

    }
    //视图转发
    private void initRequestToViewNameTranslator(RApplicationContext applicationContext){
        System.out.println("====视图转发 start======");
        System.out.println("====视图转发 end======");

    }
    //解析模板内容
    private void initViewResolvers(RApplicationContext applicationContext){
        System.out.println("====解析模板内容 start======");
        System.out.println("====解析模板内容 end======");

    }

    private void initFlashMapManager(RApplicationContext applicationContext){
        System.out.println("====请求解析 start======");
        System.out.println("====请求解析 start======");

    }
}
