package io.renren.modules.app.controller.wx;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.entity.UserEntity;
import io.renren.modules.app.enumutils.OrderStatusEnum;
import io.renren.modules.app.form.WxLoginForm;
import io.renren.modules.app.form.order.PayOrderForm;
import io.renren.modules.app.service.OrderService;
import io.renren.modules.app.service.UserService;
import io.renren.modules.app.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName WxPayController
 * @Author ruanyuanyuan
 * @Date 2021/1/24-23:25
 * @Version 1.0
 * @Description TODO 微信支付控制器
 **/
@RestController
@RequestMapping("/app/wx/pay")
@Api("微信直接接口")
public class WxPayController {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * 微信小程序支付
     */
    @Login
    @PostMapping("/microAppPayOrder")
    @ApiOperation("小程序订单支付支付")
    public R microAppPayOrder(@RequestBody PayOrderForm form, @RequestHeader Map<String,Object> header){

        //表单校验
        ValidatorUtils.validateEntity(form);

        //获取token并解析,获取userId
        long userId = Long.valueOf(jwtUtils.getClaimByToken(header.get("token").toString()).getSubject());
        String orderId = form.getId();

        //验证此用户是否存在
        UserEntity userInfo = userService.getById(userId);
        if(null == userInfo){
            return R.error("用户不存在");
        }
        //验证此用户是否有此订单
        OrderEntity orderInfo = orderService.getOne(new QueryWrapper<OrderEntity>().
                eq("user_id",userId).
                eq("id",orderId));
        if(null == orderInfo){
            return R.error("该用户不存在此订单");
        }
        //验证订单是否有效
        if(!OrderStatusEnum.NOT_PAY.getCode().equals(orderInfo.getStatus())){
            return R.error("订单无效");
        }
        //验证购物券是否有效
        //验证团购活动是否有效

        //


        return R.ok();
    }
}
