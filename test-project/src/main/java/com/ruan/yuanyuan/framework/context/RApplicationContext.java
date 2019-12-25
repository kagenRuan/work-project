package com.ruan.yuanyuan.framework.context;

import com.ruan.yuanyuan.framework.annoation.RAutoWried;
import com.ruan.yuanyuan.framework.annoation.RController;
import com.ruan.yuanyuan.framework.annoation.RService;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: ruanyuanyuan
 * Date: 2019-06-10
 * Time: 14:38
 * version:
 * Description:
 */
public class RApplicationContext {

    private static final String SCAN_PACKAGE = "sacnPackage";

    /**
     * 存放bean的map容器
     */
    Map<String, Object> beanDefilion = new ConcurrentHashMap<>();
    /**
     * 用于保存bean的信息
     */
    List<String> beanCache = new ArrayList<>();

    /**
     * 这里不像springMvc那么复杂，直接从类路径classpath下查找配置文件
     * 也就是说这里默认初始化spring ioc【模拟，后期改造】
     * 并且这里的配置文件也先用简易版Properties代替xml
     *
     * @param location
     */
    public RApplicationContext(String location) {
        //第一步：定位配置文件路径
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(location.replace("classpath*:", ""));
        try {
            //第二步：载入配置文件信息
            Properties properties = new Properties();
            properties.load(stream);
            //第三步：注册beanDefition信息
            String packageName = properties.getProperty(SCAN_PACKAGE);
            doRegister(packageName);
            //第四步：初始化bean信息
            doCreateBean();
            //第五步：注入
            populateBean();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 注册bean信息，按照配置文件中配置的扫描包路径
     */
    private void doRegister(String packageName) {
//        //这里的packageName获取的包名：如com.ruan.yuanyuan
//        //将上面的包名转换为文件夹转换后为：com/ruan/yuanyuan
//        URL url = this.getClass().getClassLoader().getResource("/"+packageName.replaceAll("\\.","/"));
//        File dir = new File(url.getFile());
//        for(File file:dir.listFiles()){
//            /**
//             * 如果当前是文件夹则递归继续查询文件
//             * 否则将查找的文件保存到list中
//             */
//            if(file.isDirectory()){
//                doRegister(packageName+"."+file.getName());
//            }else{
//                beanCache.add(packageName+"."+file.getName().replace(".class",""));
//            }
//        }
    }


    private void doCreateBean() {
        if (beanCache.size() == 0) {
            return;
        }

        try {
            /**
             * 在spring源码 中这里是需要根据代理来生产bean的【后期需要改造】
             * 同时还需要判断哪些bean需要初始化哪些不需要初始化
             * 需要初始化的bean为@Service,@Conponet,@Controller类似的注解都主要初始化
             */
            for (String beanPackageName : beanCache) {
                Class<?> aClass = Class.forName(beanPackageName);
                if (aClass.isAnnotationPresent(RController.class)) {
                    String id = loverFirstChar(aClass.getSimpleName());
                    beanDefilion.put(id, aClass.newInstance());

                } else if (aClass.isAnnotationPresent(RService.class)) {
                    RService rService = aClass.getAnnotation(RService.class);
                    beanDefilion.put(rService.value().equalsIgnoreCase("") ? loverFirstChar(aClass.getSimpleName()) : rService.value(), aClass.newInstance());
                    /**
                     * 获取类实现的接口并初始化
                     */
                    Class[] interfaces = aClass.getInterfaces();
                    for (Class inter : interfaces) {
                        beanDefilion.put(inter.getName(), aClass.newInstance());
                    }
                } else {
                    continue;
                }


            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    /**
     * 首字母小写
     *
     * @param beanName
     * @return
     */
    private String loverFirstChar(String beanName) {
        char[] chars = beanName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);

    }

    /**
     * 注入bean实行
     */
    private void populateBean() {
        if (beanDefilion.isEmpty()) {
            return;
        }

        try {
            for (Map.Entry<String, Object> bean : beanDefilion.entrySet()) {

                //取出所有的属性包括私有属性
                Field[] fields = bean.getValue().getClass().getDeclaredFields();
                for (Field field : fields) {

                    if (!field.isAnnotationPresent(RAutoWried.class)) {
                        continue;
                    }

                    RAutoWried rAutoWried = field.getAnnotation(RAutoWried.class);
                    String id = rAutoWried.value();
                    if (id.equalsIgnoreCase("")) {
                        id = field.getType().getName();
                    }

                    //设置可以访问私有属性
                    field.setAccessible(true);

                    field.set(bean.getValue(), beanDefilion.get(id));
                }

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //获取bean
    public Object getBean(String name) {
        return beanDefilion.get(name);
    }


    public Map<String, Object> getAll() {
        return beanDefilion;
    }
}
