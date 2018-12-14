package com.java.page;

import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DRAGON
 * @param <Po> 
 * @param <Req>
 * @param <Resp>
 */
@SuppressWarnings("unchecked")
public abstract class BaseDto<Po, Req, Resp> {

	private Class<Po> poClass;
	private Class<Req> reqClass;
	private Class<Resp> respClass;
	
    BeanCopier copierReq2Po;
    BeanCopier copierPo2Resp;
	
	public BaseDto() {
		Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        poClass = (Class<Po>) params[0];
        reqClass = (Class<Req>) params[1];
        respClass = (Class<Resp>) params[2];
        copierReq2Po  = BeanCopier.create(reqClass, poClass, false);
        copierPo2Resp  = BeanCopier.create(poClass, respClass, false);
	}
	
	/**
	 * 将Request转化为Po
	 * @param req
	 * @return
	 */
	public Po copyReq2Po(Req req) {
		
		if (req == null) return null;
		
		Object tragetBean;
		try {
			tragetBean = poClass.newInstance();
			copierReq2Po.copy(req, tragetBean, null);
			return (Po) tragetBean;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	/**
	 * 将Response转化为Response
	 * @param po
	 * @return
	 */
	public Resp copyPo2Resp(Po po) {
		
		if (po == null) return null;
		
		Object tragetBean;
		try {
			tragetBean = respClass.newInstance();
			copierPo2Resp.copy(po, tragetBean, null);
			return (Resp) tragetBean;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	/**
	 * req数组拷贝为po数组
	 * @param reqs
	 * @return
	 */
	public List<Po> copyReq2Po(List<Req> reqs) {
		
		if (reqs == null || reqs.size() == 0)
			return null;
		
		final List<Po> pos = new ArrayList<Po>();
		for (Req req:reqs) {
			pos.add(copyReq2Po(req));
		}
		return pos;
	}

	/**
	 * po数组拷贝为resp数组
	 * @param pos
	 * @return
	 */
	public List<Resp> copyPo2Resp(List<Po> pos) {
		
		if (pos == null || pos.size() == 0)
			return null;
		
		final List<Resp> resps = new ArrayList<Resp>();
		for (Po po:pos) {
			resps.add(copyPo2Resp(po));
		}
		return resps;
	}
	
}
