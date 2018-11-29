package com.example.administrator.mlmmx.kotlin

/**
 * 定义一些常量，配置等信息
 * Created by shiguiyou on 2017/5/25.
 */

object AppConfig {
    const val BASE_URL = "http://api.env365.cn:12080/"
    const val RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDV5rEPXs5XLEOI59vPHy7NUxMZYpCu1DhqI73JfRJJaF/dHDX+8dj9lplNcNVirRCe/y2eRL0HUGMsMTlUPA3RuY+CX/zJvSCtsePfg9VmEZYoENopWRXD9j1AFDVelqx007CZm0gbydiwyw61ITuH+q37 yTyrX0zCcAMQu/blRwIDAQAB"

    const val RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMiLaWDXjWDTXlEwvyWl53NCL5ZRcpAx7NO4Xq2UXhSraGbFbz/VunQpjFaL96vyKDW9A5kt4V3qZF7fxMaagCwLA5mH90i7VxPCjwcEjTBvyx5dfYN/5Z3DSh6vZeRDyDsJKo2Acq5tFprFdXa0g9hTIwx5tF5UaK66fqlSVl1JAgMBAAECgYAen+t/tmsIlj6Y93C5NxSZLUpHEjkEfBtGWalbqISVc3eF+jB69xB6HL4pmUZBI8GwMyURh0gbJ2gcdN9/1KgtzEru7vM/p2VZWPdxB6rJv9NS9Xte3X3JmzJ5ADJCDCUgIATSYC9bDcfSkmpDL2pd5fRGFHZtfZzcS4/H8lc7gQJBAPEN6fCQToDscaDgb2ZmCs5yz6qlD5bN/kHnAyeXXwFzgCzgdd1o86rI6BxJplMItfx37RtzfhWC2i32LQgPj4UCQQDU+oPARhEUbkw6N9oluqdNhNjOuAJQuU8O5CArOk51Ghg/aVcmkymKXT3BmpVnEZxLJzvLNZItCMY2r6+Zfef1AkEA2c/qvRaH+PKtUeK6hlpaFWWkw9rWFTwZLM9jCFk83YDSJrrDhccZtg5HvZfHFGxglLqAozdejG1qW/F/izMUSQJBAJiV1lilmk0B7Lj1FORdAsoF+HK54GbHLL3lcKqnvlglVuPva5Hmcyd/P+R6BLnwIwKw3CTtf4mmNKQvYGZejxUCQEGeHIxCf55SUppZxVkZw7mIX0RZUj8X2AQK0oUfVykE/dcT4y21v+PCNlYjpqct5DM0dpja1h02eIR8ZbLqxSM="

    const val RSA_SIGN = "amlhbmdzdWxpbmdoYW8zMDAw"

    const val DEFAULT_TIMEOUT: Long = 10
    const val PREFS_KEY_USER_ID = "userId"
    const val API_INTERFACE_ADD_DEVICE = "012" //添加设备
    const val API_INTERFACE_DELETE_DEVICE = "013" //删除、解绑设备
    const val API_INTERFACE_MODIFY_DEVICE_NAME = "014" //修改设备名称
}