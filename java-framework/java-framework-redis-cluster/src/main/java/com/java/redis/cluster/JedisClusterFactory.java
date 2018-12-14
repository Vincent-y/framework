package com.java.redis.cluster;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <p>jediscluster实现类
 * <p>@author dragon
 * <p>@date 2016年6月25日
 * <p>@version 1.0
 */
public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean{
	//日志
	private final static Logger logger = LoggerFactory.getLogger(JedisClusterFactory.class);
	
    private Properties addressConfig;
    private String addressKeyPrefix ;  
  
    private JedisCluster jedisCluster;

	private int connectionTimeout;
	private int soTimeout;
	private int maxAttempts;
	private String password;
    private Integer maxRedirections;
    private GenericObjectPoolConfig genericObjectPoolConfig;

	private boolean requirePassword;
    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

	@Override
	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		if(requirePassword){
			jedisCluster = new JedisCluster(haps,this.connectionTimeout,this.soTimeout,this.maxAttempts,this.password,this.genericObjectPoolConfig);
		}else{
			jedisCluster = new JedisCluster(haps,this.connectionTimeout,this.soTimeout,this.maxAttempts,this.genericObjectPoolConfig);
		}
	}

	@Override
	public JedisCluster getObject() throws Exception {
		return this.jedisCluster;
	}

	@Override
	public Class<?> getObjectType() {
		 return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);  
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	/**
	 * 解析配置文件
	 * @return
	 * @throws Exception
	 */
	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {
			Properties prop = this.addressConfig;
			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			for (Object key : prop.keySet()) {
				if (!((String) key).startsWith(addressKeyPrefix)) {
					continue;
				}

				String val = (String) prop.get(key);
				boolean isIpPort = p.matcher(val).matches();

				if (!isIpPort) {
					logger.error("ip or port is invalide");
					throw new IllegalArgumentException("ip or port is invalide");
				}
				String[] ipAndPort = val.split(":");

				HostAndPort hap = new HostAndPort(ipAndPort[0].trim(),Integer.parseInt(ipAndPort[1].trim()));
				haps.add(hap);
			}

			return haps;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("解析 jedis 配置文件失败:{}", ex.getCause());
			throw new Exception("解析 jedis 配置文件失败", ex);
		}
	}



	public void setAddressConfig(Properties addressConfig) {
		this.addressConfig = addressConfig;
	}

	public Properties getAddressConfig() {
		return addressConfig;
	}

	public String getAddressKeyPrefix() {
		return addressKeyPrefix;
	}

	public void setAddressKeyPrefix(String addressKeyPrefix) {
		this.addressKeyPrefix = addressKeyPrefix;
	}

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public int getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMaxRedirections() {
		return maxRedirections;
	}

	public void setMaxRedirections(Integer maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public GenericObjectPoolConfig getGenericObjectPoolConfig() {
		return genericObjectPoolConfig;
	}

	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}

	public boolean isRequirePassword() {
		return requirePassword;
	}

	public void setRequirePassword(boolean requirePassword) {
		this.requirePassword = requirePassword;
	}


}
