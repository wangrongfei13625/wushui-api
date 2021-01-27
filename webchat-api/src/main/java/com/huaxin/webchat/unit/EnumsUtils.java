package com.huaxin.webchat.unit;

import lombok.Getter;

@Getter
public enum  EnumsUtils {

    FAIL(000, "操作失败"),
    SUCCESS(100, "操作成功"),
    BASEMESSAGE_ISEMPTY(110, "基础信息不能为null"),
    MSGTYPE_ISEMPTY(120, "发送消息类型不能为空"),
    WEIXINPARAMES_ISEMPTY(130, "微信参数不能为null"),
    AGENTID_ISEMPTY(131, "应用ID不能为空"),
    CORPID_ISEMPTY(132, "企业ID不能为空"),
    AGENTSECRET_ISEMPTY(133, "应用密钥不能为空"),
    WEIXIN_CONNECT_FAILED(134, "请求微信接口失败"),
    WEIXIN_HAPPEN_EXCEPTION(135, "请求微信接口发生未知错误"),
    APPLY_TOKEN_FAILED(136, "请求应用token发生未知错误"),
    SENDMESSAGE_FAILED(137, "发送企业微信消息失败"),
    SENDMAIL_PARAM_ISEMPTY(138, "发送Email基础参数不能为空"),
    SENDMAIL_FROM_ISEMPTY(139, "Email邮件发送人不能为空"),
    SENDMAIL_TO_ISEMPTY(140, "Email邮件收件人不能为空"),
    SENDMAIL_MESSAGING_EXCEPTION(141, "Email邮件发送缺少发送类");

    private int code;

    private String msg;

    EnumsUtils(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
