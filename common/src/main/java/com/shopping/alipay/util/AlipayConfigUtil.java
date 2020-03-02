package com.shopping.alipay.util;

/**
 * 阿里支付配置
 * 
 * @author zhuowei.luo
 * @date 2018/7/9
 */
public class AlipayConfigUtil {

	public static final String serverUrlDev = "https://openapi.alipaydev.com/gateway.do"; // 开发调试
	//https://openauth.alipaydev.com/oauth2/publicAppAuthorize.htm?app_id=APPID&scope=SCOPE&redirect_uri=ENCODED_URL
	public static final String oauthUrlDev ="https://openauth.alipaydev.com/oauth2/publicAppAuthorize.htm";
	
	public static final String serverUrlRun = "https://openapi.alipay.com/gateway.do"; // 正式上线
	public static final String oauthUrlRun ="https://openauth.alipay.com/oauth2/publicAppAuthorize.htm";
	
	public static final String serverUrl = serverUrlRun; // 阿里调用网关地址
	public static final String oauthUrl = oauthUrlRun; // 阿里授权地址
	public static final String appId; // appId
	public static final String privateKeyForPkcs8; // rsa私有key，注意：这里是pkcs8编码的私有key！！！
	public static final String publicKeyForAlipay; // rsa公共key，注意：不是应用公共key，是阿里的公共key！！！这里是个坑
	public static final String format = "json"; // 格式：json
	public static final String charset = "UTF-8"; // 编码：UTF-8
	public static final String signType = "RSA2"; // 非对称加密类型：RSA2

	static {
		//测试环境
//		appId = "2016091700534806";
//		privateKeyForPkcs8 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDdPWXOO8l8SqKP3nxXfMX805jSo2kvVy61PUXR3sjNTiFPk3GY+PM/GCojPTyQxbYU8x/iHZ5XegE/j/tDtZ6ll3dNrLVHuUgXnw64WtlT5ThS9MQtQQ4PDG53axncbKAdhLRtRyK4JhouQBGb0LYoJ142kHm2KBhCeIRSSmLr40/hHff9sslNv4cqnlR0JnFIpWAFOX2Jf2UqjkQ8v9A49vcgLwwH0FuwKTE4qVNQH9epN0xTnhbUCEeCveD89LJx7JhLv7yOeHzSmvDsxqZ6IjsWqEkwIAv0+9Fitso0FEwFapDMvPIvfNP32tZsmV84Cep8wEN2bWMKAqOw6VL1AgMBAAECggEBALOBO3bnf4U/MUK17AE4FZQbQuW0/IUSoa0ft+SedCJQACsGoKbTZtmMi7Pn3ZpBMIky8m2ORokHTrf55I59b+k/SedV+AfnxxyTS2g9HhQ56Uc1nW5yWCvRlFeEKIl2ATuyDoqTqxHqqFdaWG8qGoHd3006T+aCBZz5F7OE81obpOUNWa75WLRCMhdM+LLi4vz12v1tnRtCx+mw9srPf8Pg9bWSRtMJE15858xVzhoArPROl4tV9Sa9XqAc7Pr2qWvopacJL+HHePHT1A16bvdiNIhVrEtw5VG7ozCg/mwjylwW1FEfJ2CAjgiUVtKmTp02a1Ildpq8YkK40w/mVwkCgYEA7roKKDz5T6ADgywc8G3ZK6YniZ7qqkmwGFsy9NuVa0XnJCAeEHpg9a1i+vyMEuBXbLHEYTlvntSLqSqa/L8AxaaTNAiuZpfndbVceU8vf2PzV9NMB6cmjahpkyRAMeCKHuaAyrwmxpkPL8ZB0xZeJI+kX60OWDSOnsgxieB+pWMCgYEA7T9yVWYGtY1eKY7zeOty/4TPx04gjnAZO9KT/gb2QHWdYPHpgLZFbzLjTInoL3s31ln7dHnpwWn6eqwVAGqTAnTQrMcaWoDWtSrMLxrM8ksCs6Vyl3ne9mLUD5nZV7PEw8+/1qVQqGW3eODKkz5dp8/SS+xRSmrdXZvv08fXIccCgYAibD0OtEwWyTH4diHuIUjRXHITDBE2YM29lVjxqcAP6dJ8iQ0Mvzi/9DJzknr4NdUlXTS9+DQ6KNfCmtwiWcBYv742IJJVRM4hUPd2no5wxg7OuS8bY2TRxFkE2JNRHdmMvxwPH7ZPqL5h2yqer3j/uJ6xFRkeauWty5wpD2/zlQKBgAFI/bKOueyT1nW9K351MAtISePXfX+oBcr4KgsPOJhCytZymQQ0eI3QpcCNYq+XKEdF94G7A1Qj4dJ2SirVaPRzc4nsDi19UyxqDe8XYxLQ6zPT6pn9dAK7qFBpUe8SrzcsUeq0vIoc+vBxr0c66ED3dDkrD4mqqIZQSO1JwNWTAoGAYOxl4i8VasOylPygTMMYA/tG8V07CSYMdtTdt/gVB9xkwBSjLdTjtOVbvvVw3EuW9/OZLrqHTBnpezbemIjkOD50eJLkH/WfuJ1R8PPnwBbhKuNdAx+NkVex7kbWtImgL8kHCdhHISZH/V7srBtZlKezkwNe/gFjd+uzTFPgsTs=";
//		publicKeyForAlipay = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiCOlRX7pn+NMQ+BOcdqV5BRPqRBB4TPQkn/e4993DxWB+76lTppHX8+2dOQ7Bq22rVt7+oJoQpQk0IRfgmnZJixH8RBJO4pgbTnMN5CGYw4YfM3VcFUloUloF43/3PPWV4HSQFQ+BaQq4GLQxW1ZVYHPm2y1M8yLIyij6t6jRd5vAwv4vrhfIYm/tRwnY3aDQAxNGnk5H+W0n4S3dNdWPvPyg03Z0u+lo+o2NhInRVehpFGcy9DuRrHFmQtakVrgmoiB2/ueodhEdE59Tn9vk2Bu+vY7mrqGy6nG3QyiJ66CNGvx9mifIH9fpEoXKcETi8MDSARtymIrmfEBQ2UpSwIDAQAB";

		appId = "2018080860964396";
		privateKeyForPkcs8 = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPzfJG+Lz/P8fOYfIl3ugM4eIh6NBbLL27fEd7JLESiZbrIWygoaMAHwiNAHKdSaeCdZIvSayCqO2ZLt+Kj8xSFgBDJ+aMlDvcJi6S8c3yANDjuoc0/+2E69O+h2SKzBiUuoFYFp1Papt1MJ1sACq/mpuUNxyzP+C+OozWrKN0uPKfrZCazYI+vK4Qq3UVvsClOdUnxZ3SIONU3p/kUl8qIO2CmBjS1/xMXxgec7Vu1ZBQzapx6QgLo6rTcXa5715qpukJOkA/+xTc+k8mH+TZLdU9c9wPP5hYrDBPVV+psca24Qa2cNgacU4G6vS3ulEy75hpNQoEzbefeerE27yBAgMBAAECggEAYiPU8qaSpUgr3iduEHZdu1z07NtsRqz0F12rktjpl5Ma0gEPl1h4W37SYRgrIDjB/wCp8WfAObN744zftjqI4IRvTWWPq+UGLSpJorMvL7dq19/Wok7/FDW1XlQcQ7UiGW5nE2zrkuVYjtPMu4YppnWnM7Gg/pvbjzd6Io/TRbZby9iTpFGqZZBr2WOlF0o2zxeF7wTHq08kepDuPgqIyjK7iZOWF5akMFcmdp9FKPeJfL8ReXYxT/HF4DbBdLznue5Poy8FQTZF1NZCOiYryepQRvdz//Inua2sj6dgxX0RJS9ryFgtmgPujUa7fQZkysQcdD/jT7AJ0fagJ7zOWQKBgQD5L9R5hSa4B86OgkRo7kgD4HsDCkfRjZRxwBSThInhafRbR+5ytQ4ymR8DFPSBnupcXkZkAgcfKYv9XIR1VKSssiAw8I8Iy+pp6JHQR5CX38CjH6osgnvmOaXMN01Ja+GSGLzN1IYeOi0oU6zhxAZrLdfOg7/3anfgcohblTrPVwKBgQCTvH+KGo4VBzw7l5v0ebTcvs1gR0JY41rXzCQlll8rbXrxMhfyf6j1MeukFgt7Jsops0Q27DQ5Xp+YNOeR3aMWySuw9L4rz1ma7AFWFGUd+HEEo79vSYJ1XtBSu11Vdf+bHfX4Bbx9cZVLkq2xrDPTPUiPM/k/bwKkn5xGYMRj5wKBgQDZzpjl7yC80vYVUoABR+XinaBoM+A6pooOM1rI5pyuTzKrZuBhmyhhyb11sCdX9VPckPruB2e6MVKPjwKom+ekm73PiTU89yBamlJ5v1P/rDBxZQgWB6fMwK7PJbeEaautS7ocH5traGZ4TUtbuz/xBw0PEdIFrSZDJVlS08mn2QKBgAC/A/njlnIevjwi0OPUNYij4g+pbHlcdJoAavJRpxtmCyfqs9pCsMzEOsdyYQ53LLA3OpH4cW8UvkObiMvgzbEQxQq4wbkg6/fvVzZjFfunxJJcbVwXh/QlzXGNhrQmy2qyJQripIgRqcpe7Eeocf6QIayve4PJgL7qG3TziipjAoGAWiQciHtZcFsnUHIc75uv2ei4Ymiz7WmocT5EZQYZjmTNo8KWSLbdf53tdyL9IuotgMvtvjT2JvjMttYcd5Di7MmIt0h+1okBe1MYLYQQ5b9L7SJ32VdfKYIUdFgTaqDTKogHKQEO6Jkhys6o7z5wb6WjNzXq+TC1hci60eF7O2M=";
		publicKeyForAlipay = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhP7GfLx1kl3ZGcXyGQalcX9mfqXwxFR0vfvyIwlWdLZ1kiM/EQi5yX6IgSFOSeHXKswBQ3OHw14ywzCCWQz+/a+ajhc5k+rmKqaKxog5X7OXo05IOX9CJoUUY9+c0kxAjp7rpvzjvK12TJqFT054INhHynVXQ8NKBwcFUWH35xARiBK6k5/s7oTCs+vK0MdiLFNubKfP53PDRuTr07gCQGU8tGHWm3shFD4ToEDuih46TJkMHUQcdiK+94IO6/Aiouv/96elxgkiz5ToNTSel6eZoCXyvJLFqvdf90xRhAqyGkXIUEB/d5gUMAkuAlSYjdUs0ct+GoMUtmeWE9QsIwIDAQAB";
	}

	
}