package demo.pay;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016041301295313";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgjyxqn+SdDOL+De8+vMGQBeR5VFcODSJ4iY9i7xBdoXSeei5hwe4NNPg6OK4oAXdJDic1o7dMMIaqFQtlse/rN4k6LpCMssaBmM/bBfQoSqMOIAG8UsQGeWG9+IFXlNYxOaU2S6ejIa70OvTca7nKfy62XgZU1lP6441bne3aKrXJ+9nD6WEKp6NQqaQY7xtUp1CAGZavOR9TNyN9tQL8L4xYpUDEpwRW8a6gGvYxb+aAqAPKuvEKIH+LhZMwxOlreOS5XrzNH3w/+rZ3henqRb5VERjfoBCIOxP6/31ehVTBiPp3inrnv1UexsF9vNcvG0O2mXZkN8qbqwOC58lZAgMBAAECggEAFYb8ypMDoPcAXFXb9Qpz3W6c0pO3VF/4djbjbb1l8VfbfgAdxeetlkG64nuFBkIkDzyBj1STF/kNpTJLI+h2pY/9qzphESxLJ0co31HuCZTwRc2OzgQ+2iteKo1uMiL3kQQMnboJEPtMLNzWxdL/xyHETplOugM+oU3vPxBuuqh2B8hgVU1WKMbBE999t1/lKbu5NapNmxU7HXGbYeKlp6/szbRxCFSZHLDBBd6oV55xMMcs0U1Pm+9wb4+2h+2WHpmq2kywGmb/AQlkk1BV3tU5LsJku+88bY52ap9xelYWz2nKqZPV+SVTnn5g8Neva5Y7d9IbIsU4Sc0qy5Ct3QKBgQD0B1R3unSLtEiGMK7Tc5LewzEPFAZewTKsVnoxONq4THfOVnKKpZj7FSXDicIBwpqekyuv/t+mENrnJv6HlurlULionCwJUyHgurEPkw6v05RNGiLWE0cN+UQNzKiJXD3v2FfaYrN0uzjh7sP8H987ApK3AYy0obOfUhY5Qvl1TwKBgQCob5TDUxZlk4a2B96RKr8wXzWM+/tZkXitd/QhkXHrDx1RaHN73CyOPr3L9D2t5D/M3AYVnE5nV89jVvnUk9FGQyMDzJGUIDJqXugfbZoQ1H+Bmp5y3BS1nyqmeTfOWFpqFQVdKh9Ri0KTH6M6ZCtj2aDYgyBsbhrZIQrpKyx81wKBgQCzQgcshE1EqsRzvqNONSH4fY42M1GSnX0p18SfD9Kre5CuhBZT/t1n49r/ztwzCs8mGZoICMQla1BCaQSkeZpHpYpgYYh/uQ+z07Bpbm9lfZj9ImvgsfyKKUdyk5pifN4Kp/OxOrv0zes1HH3fYVJMONGpmXvQ3M/kAxrMKYSyRQKBgQCENonsRGUZOJMOgeDXzFkk7CdJ0mW7KfAzIix0yQlFvKiDxVyZBfvHcEJlPQPJYpgeAzuXbQkyH4A/9v7GOX2VDJAIpGRFhHxueK72NH8x5MttfWQQvuZWpmjBIfeHNl1KVdPPDIhm5sxMVhI3fHd+uk6ChXNHCapzc3dwDZ6vIwKBgDhaaV9wirmiUyR0GADWx/om+ER+3lSMEuQSsU2VPeM2nsUvKwQ6s0wFTj+E/KMYswWrVTJI3w+39OQZqfmetzBAkee2Ah0+aS6NZjs8RDJXTA1Tyq7J1t/BZq6+YMQjStuv0V+8CgAsUIbHTkFYlGvOGIrWBajquWkWOksvCFhg";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnbkwZmPwAs2IYfPEU8laLQiMNS00SQsekLBMelzAtEP7dJqm+dCcUuDc8h05oA4kSIhrMV9RLmJOABZDn6Smg4jMJGnPGxxpOhLyHKuDijUAVNYlT2Y9XwCrAXKvhDwLMnmQKS3EuTx9QuFmZUwIfQ//Y4l6fuppAp6anKnIDnL0W4FKB6RBwiyS11hsTREUTlHOMJMzZmCKgazTFDP6ZoZ5N303BG5KkjDbbrg5u5wAaylNOmqfgjB5f6vlovKqcbDHM8ttZAzhW9FItMZ7ghqUXe9i1TEx6Qq+1jQE9A7OIiN1MjsCJ0BOJhH82DhkGMHT+B2+ZbVovR/0Vu/zBQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://taotao.3w.dkys.org/notify_result.html";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://taotao.3w.dkys.org/return_result.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";



//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}

