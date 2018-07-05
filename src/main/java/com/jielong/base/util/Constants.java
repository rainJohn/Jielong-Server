package com.jielong.base.util;

public class Constants {
  //小程序AppId 
  public static final String APPID="wx902b90dd5d20c4e0";
  public static final String SECRET="42d1b4194710b0241e57e16b22a35623";
  public enum SignType {
      MD5, HMACSHA256
  }
  public static final String FIELD_SIGN = "sign";
  //支付商户id
  //public static final String MCH_ID="1501533611";  //其实就是商户Id
  public static final String MCH_ID="1493631082";  //其实就是商户Id
  //支付密钥
  public static final String PAY_KEY="E0E1954FB86B4CDDA3DB69E13629AAA7";
  //支付异步通知url
//  public static final String NOTIFY_URL="https://www.95cfuns.com/wxpay/notify";
  public static final String NOTIFY_URL="http://47.88.54.113:8081/wxpay/notify";
  //微信支付统一下单url
  public static final String UNIFIEDORDER_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";
  //服务器ip地址
  public static final String SERVER_IP="47.88.54.113";
  
  public static final String UPLOADED_FOLDER=System.getProperty("user.dir")+"\\uploadFiles\\"; 
  public static final String SAVED_FOLDER=System.getProperty("user.dir")+"\\savedFiles\\";

  //分销比例
  public  static final double FIRST_DISTRIBUTION_PERCENT=0.10;  //一级分销比例 10%
  public  static final double SECOND_DISTRIBUTION_PERCENT=0.06;  //二级分销比例 6%

}
