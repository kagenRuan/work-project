package io.renren.modules.app.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName QrCodeController
 * @Author ruanyuanyuan
 * @Date 2021/1/26-18:05
 * @Version 1.0
 * @Description TODO 生成二维码接口
 **/
@Log4j2
@RestController
@RequestMapping("/app/qrcode")
@Api("微信native接口")
public class QrCodeController {

    /**
     * @Author: ruanyuanyuan
     * @Date: 2021/1/26 17:50
     * @Description: TODO 生成二维码方法
     **/
    @GetMapping("/create")
    @ApiOperation("生成二维码")
    public void create(HttpServletRequest request, HttpServletResponse response){

        log.info("qrcode 微信【Native支付】生成二维码");

        String codeUrl = request.getParameter("codeUrl");
        ServletOutputStream outputStream = null;
        if(!StringUtils.isBlank(codeUrl)){
            try {
                QrConfig qrConfig = new QrConfig();
                qrConfig.setWidth(250);
                qrConfig.setHeight(250);
                qrConfig.setMargin(2);
                outputStream = response.getOutputStream();
                QrCodeUtil.generate(codeUrl,qrConfig,"jpg",outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(null != outputStream){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
