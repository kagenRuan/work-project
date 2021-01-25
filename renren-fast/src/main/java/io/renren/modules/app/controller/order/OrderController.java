package io.renren.modules.app.controller.order;

import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.vo.order.OrderVO;
import io.renren.modules.app.form.order.UserOrderForm;
import io.renren.modules.app.service.OrderService;
import io.renren.modules.app.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName OrderController
 * @Author ruanyuanyuan
 * @Date 2021/1/24-22:23
 * @Version 1.0
 * @Description TODO 订单控制器
 **/
@RestController
@RequestMapping("/app/order/")
@Api("订单接口")
public class OrderController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 订单列表
     */
    @Login //表示登录后才能调用此接口
    @PostMapping("list")
    @ApiOperation("订单列表接口")
    public R list(@RequestBody UserOrderForm userOrderForm, @RequestHeader HashMap header){
        //表单校验
        ValidatorUtils.validateEntity(userOrderForm);
        String token = header.get("token").toString();
        int userId = Integer.parseInt(jwtUtils.getClaimByToken(token).getSubject());
        //根据用户ID查询订单列表
        List<OrderVO> list = orderService.list(userId);
        return R.ok().put("list",list);
    }
}
