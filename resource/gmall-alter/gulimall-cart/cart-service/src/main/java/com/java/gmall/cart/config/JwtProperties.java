package com.java.gmall.cart.config;

import com.java.core.utils.RsaUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PublicKey;

@ConfigurationProperties(prefix = "gmall.jwt")
@Component
public class JwtProperties {

    private String pubKeyPath;// 公钥文件地址

	private String cookieName; // cookie名称

	private PublicKey publicKey; // 公钥

	private String userKey;

	public String getPubKeyPath() {
		return pubKeyPath;
	}

	public void setPubKeyPath(String pubKeyPath) {
		this.pubKeyPath = pubKeyPath;
	}

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
     * @PostContruct：在构造方法执行之后执行该方法
     */
    @PostConstruct
    public void init() {
        try {
            File pubKey = new File(pubKeyPath);
            // 获取公钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            throw new RuntimeException("初始化公钥和私钥失败");
        }
    }
}