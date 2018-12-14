package com.java.page;

import java.util.List;

/**
 * <p>分页查询--适合各种分页查询
 * <p>@author dragon
 * <p>@date 2015年7月15日
 * <p>@version 1.0
 */
public class PageService<Po>{

	/**
	 * 分页查询
	 * @param page
	 * @param condition
	 * @param dao
	 * @return
	 */
	public Page findByPage(Page page,PageCondition condition,DaoTemplate<Po> dao) {
		condition.setStartLine(page.getStartLine());
		condition.setCurrentPage(page.getCurrentPage());
		condition.setPageSize(page.getPageSize());
		List<Po> poLists  = dao.listByPage(condition);
		page.setList(poLists);
		int count 				= dao.getTotalRowsByPage(condition);
		page.setTotalRows(count);
		page.initDatas();
		return page;
	}
	
	
	/*
	<!-- 分页查询 -->
	<select id="listByPage" parameterType="com.wing.usersystem.params.integrate.SystemCondition" resultMap="PSystemRes">
		select * from t_system
		
		<where>
			<if test="enable != null">
				 p_enable = #{enable}
			</if>
			<if test="name != null">
				and p_name like #{name}
			</if>
			<if test="ptype != null">
				and p_type = #{ptype}
			</if>
		</where>
		
		<choose>
			<when test="ascending==0">
				order by p_id desc
			</when>
			<otherwise>
				order by p_id asc
			</otherwise>
		</choose>
	
		limit #{startLine}, #{pageSize}
	</select>
	
	<!-- 查询所有数量 -->
	<select id="getTotalRowsByPage" parameterType="com.wing.usersystem.params.integrate.SystemCondition" resultType="Integer">
		select count(p_id) from t_system
		<where>
			<if test="enable != null">
				 p_enable = #{enable}
			</if>
			<if test="name != null">
				and p_name like #{name}
			</if>
			<if test="ptype != null">
				and p_type = #{ptype}
			</if>
		</where>
	</select>


@Override
	public JsonResponse<Page> findByPage(SystemCondition condition) {
		logger.debug("接收参数:{}",condition.toString());
		
		Page page = new Page(condition.getCurrentPage(), condition.getPageSize());
		
		if (condition.getName()!= null ) {
			String name = "%"+condition.getName()+"%";
			condition.setName(name);
		}

		
		
		logger.debug("分页查询系统成功");
		String appkey = userOperatorLogsService.getAppKey();
		String uid        = userOperatorLogsService.getUid();
		userOperatorLogsService.writeLogs(uid, appkey,"启用|禁止系统", "禁止系统成功", 0);
		
		findByPage(page,condition, systemDao);
		return new JsonResponse<Page>("0", "1100004", "分页查询系统成功", page, "1.0");
	}
	
	*/
	
	
	
}
