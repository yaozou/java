package com.yaozou.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2019/2/26 12:22
 */
public class AlipayTest {
    public static void main(String[] args){
        String app_id = "";
        String private_key = "";
        String alipay_public_key = "";

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                app_id,private_key,
                "json","utf-8",
                alipay_public_key,"RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"20190223X14403\""+
                "  }");
        try{
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            System.out.println("支付宝返回数据："+response.getBody());
            if(response.isSuccess()){
                System.out.println("调用成功");
            } else {
                System.out.println("调用失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
