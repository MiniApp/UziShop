/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.pay.alipay.util;

import com.iskyshop.pay.alipay.config.AlipayConfig;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlipayCore {
    public static String buildMysign(AlipayConfig config, Map<String, String> sArray) {
        String prestr = createLinkString(sArray);
        String mysign = MD5.sign(prestr, config.getKey(), config.getInput_charset());
        return mysign;
    }

    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map result = new HashMap();

        if ((sArray == null) || (sArray.size() <= 0)) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = (String) sArray.get(key);
            if ((value == null) || (value.equals("")) || (key.equalsIgnoreCase("sign")))
                continue;
            if (key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    public static String createLinkString(Map<String, String> params) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); ++i) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);

            if (i == keys.size() - 1)
                prestr = prestr + key + "=" + value;
            else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static String createLinkStringNoSort(Map<String, String> params) {
        List<String> keys = new ArrayList();
        keys.add("service");
        keys.add("v");
        keys.add("sec_id");
        keys.add("notify_data");
        String prestr = "";
        for (String key : keys) {
            prestr = prestr + key + "=" + ((String) params.get(key)) + "&";
        }
        prestr = prestr.substring(0, prestr.length() - 1);
        return prestr;
    }

    public static String createLinkStringNoSort1(Map<String, String> params) {
        Map<String, String> sParaSort = new HashMap();
        sParaSort.put("service", (String) params.get("service"));
        sParaSort.put("v", (String) params.get("v"));
        sParaSort.put("sec_id", (String) params.get("sec_id"));
        sParaSort.put("notify_data", (String) params.get("notify_data"));

        String prestr = "";
        for (String key : sParaSort.keySet()) {
            prestr = prestr + key + "=" + ((String) sParaSort.get(key)) + "&";
        }
        prestr = prestr.substring(0, prestr.length() - 1);

        return prestr;
    }

    public static void logResult(AlipayConfig config, String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(config.getLog_path());
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}