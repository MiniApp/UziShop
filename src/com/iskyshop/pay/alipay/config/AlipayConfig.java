/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.pay.alipay.config;

public class AlipayConfig {
    private String partner = "";

    private String key = "";

    private String seller_email = "";

    private String notify_url = "";

    private String return_url = "";

    private String log_path = "D:\\alipay_log_" + System.currentTimeMillis() + ".txt";

    private String input_charset = "utf-8";

    private String sign_type = "MD5";

    private String transport = "http";

    public static String private_key = "";

    public static String ali_public_key = "";

    public static String getPrivate_key() {
        return private_key;
    }

    public static void setPrivate_key(String private_key) {
        private_key = private_key;
    }

    public static String getAli_public_key() {
        return ali_public_key;
    }

    public static void setAli_public_key(String ali_public_key) {
        ali_public_key = ali_public_key;
    }

    public String getPartner() {
        return this.partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSeller_email() {
        return this.seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getNotify_url() {
        return this.notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return this.return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getLog_path() {
        return this.log_path;
    }

    public void setLog_path(String log_path) {
        this.log_path = log_path;
    }

    public String getInput_charset() {
        return this.input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getSign_type() {
        return this.sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getTransport() {
        return this.transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }
}