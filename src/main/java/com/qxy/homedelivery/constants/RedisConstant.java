package com.qxy.homedelivery.constants;

/**
 * @Author: SayHello
 * @Date: 2023/4/1 19:47
 * @Introduction:
 */
public class RedisConstant {
    /**
     * 验证码统一前缀
     */
    public static final String PREFIX_VALIDATE_CODE = "HBNUHomeDelivery:Validate:";
    /**
     * 验证码有效期
     */
    public static final int VALIDATE_CODE_TIME = 5;

    /**
     * 用户登录信息
     */
    public static final String PREFIX_USER = "HBNUHomeDelivery:User:";

    /**
     * 菜品信息
     */
    public static final String PREFIX_DISH = "HBNUHomeDelivery:Dish:";

    /**
     * 菜品有效期
     */
    public static final int DISH_TIME = 60;
    /**
     * 套餐信息
     */
    public static final String PREFIX_SETMEAL = "HBNUHomeDelivery:SetMeal:";

    /**
     * 套餐有效期
     */
    public static final int SETMEAL_TIME = 60;

    /**
     * 临时图片名前缀
     */
    public static final String PREFIX_IMG_TMP = "HBNUHomeDelivery:Image:Tmp";

    /**
     * 存入数据库的图片名前缀
     */
    public static final String PREFIX_IMG_DB = "HBNUHomeDelivery:Image:DB";
}
