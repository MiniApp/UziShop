/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.pay.alipay.util;

import com.iskyshop.pay.alipay.config.AlipayConfig;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public class AlipayNotify {
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

    private static final String HTTP_VERIFY_URL = "http://notify.alipay.com/trade/notify_query.do?";

    public static boolean verify(AlipayConfig config, Map<String, String> params) {
        String responseTxt = "true";
        if (params.get("notify_id") != null) {
            String notify_id = (String) params.get("notify_id");
            responseTxt = verifyResponse(config, notify_id);
        }
        String sign = "";
        if (params.get("sign") != null) {
            sign = (String) params.get("sign");
        }
        boolean isSign = getSignVeryfy(config, params, sign, true);

        String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数："
                + AlipayCore.createLinkString(params);

        return ((isSign) && (responseTxt.equals("true")));
    }

    public static Map<String, String> decrypt(AlipayConfig config, Map<String, String> inputPara) throws Exception {
        inputPara
                .put("notify_data",
                        RSA.decrypt((String) inputPara.get("notify_data"), AlipayConfig.private_key,
                                config.getInput_charset()));
        return inputPara;
    }

    public static boolean verifyWapNotify(AlipayConfig config, Map<String, String> params) throws Exception {
        if (config.getSign_type().equals("0001")) {
            params = decrypt(config, params);
        }

        String responseTxt = "true";
        try {
            Document document = DocumentHelper.parseText((String) params.get("notify_data"));
            String notify_id = document.selectSingleNode("//notify/notify_id").getText();
            responseTxt = verifyResponse(config, notify_id);
        } catch (Exception e) {
            responseTxt = e.toString();
        }

        String sign = "";
        if (params.get("sign") != null) {
            sign = (String) params.get("sign");
        }
        boolean isSign = getSignVeryfy(config, params, sign, false);

        return ((isSign) && (responseTxt.equals("true")));
    }

    private static boolean getSignVeryfy(AlipayConfig config, Map<String, String> Params, String sign, boolean isSort) {
        Map sParaNew = AlipayCore.paraFilter(Params);

        String preSignStr = "";
        if (isSort)
            preSignStr = AlipayCore.createLinkString(sParaNew);
        else {
            preSignStr = AlipayCore.createLinkStringNoSort(sParaNew);
        }

        boolean isSign = false;
        if (config.getSign_type().equals("MD5")) {
            isSign = MD5.verify(preSignStr, sign, config.getKey(), config.getInput_charset());
        }
        if (config.getSign_type().equals("0001")) {
            isSign = RSA.verify(preSignStr, sign, AlipayConfig.getAli_public_key(), config.getInput_charset());
        }
        return isSign;
    }

    private static String verifyResponse(AlipayConfig config, String notify_id) {
        String veryfy_url = "";
        if (config.getTransport().equalsIgnoreCase("https"))
            veryfy_url = "https://mapi.alipay.com/gateway.do?service=notify_verify&";
        else {
            veryfy_url = "http://notify.alipay.com/trade/notify_query.do?";
        }
        String partner = config.getPartner();
        veryfy_url = veryfy_url + "partner=" + partner + "&notify_id=" + notify_id;

        return checkUrl(veryfy_url);
    }

    private static String checkUrl(String urlvalue) {
        String inputLine = "";
        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }

        return inputLine;
    }
}