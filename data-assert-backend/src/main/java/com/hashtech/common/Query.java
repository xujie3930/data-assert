package com.hashtech.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Project：business-build
 * Package：com.jinninghui.datasphere.framework.result
 * ClassName: Query
 * Description:  Query类
 * Date: 2021/3/24 3:22 上午
 *
 * @author liyanhui
 */
public class Query<T> {

    /**
     * 当前页码
     */
    public static final String PAGE = "pageNum";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "pageSize";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "orderBy";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if (params.get(PAGE) != null) {
            curPage = Long.parseLong((String) params.get(PAGE));
        }
        if (params.get(LIMIT) != null) {
            limit = Long.parseLong((String) params.get(LIMIT));
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
        params.put(PAGE, page);

        //排序字段
        //防止SQL注入（因为 ORDER_FIELD、ORDER 是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject((String) params.get(ORDER_FIELD));
        String order = (String) params.get(ORDER);


        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (ASC.equalsIgnoreCase(order)) {
                return page.setAsc(orderField);
            } else {
                return page.setDesc(orderField);
            }
        }

        //没有排序字段，则不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        //默认排序
        if (isAsc) {
            page.setAsc(orderField);
        } else {
            page.setDesc(orderField);
        }

        return page;
    }


    public IPage<T> getPage(BusinessBasePageForm pageRequest) {

        return this.getPage(pageRequest, null, false);
    }

    public IPage<T> getPage(BusinessBasePageForm pageRequest, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if (StringUtils.isNotEmpty(pageRequest.getPageNum() + "")) {
            curPage = Long.parseLong(pageRequest.getPageNum() + "");
        }
        if (StringUtils.isNotEmpty(pageRequest.getPageSize() + "")) {
            limit = Long.parseLong(pageRequest.getPageSize() + "");
        }
        if (-1L == limit){
            pageRequest.setPageSize(10);
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //排序字段
        //防止SQL注入（因为 ORDER_FIELD、ORDER 是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject(pageRequest.getOrderBy());
        String order = pageRequest.getOrder();

        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (ASC.equalsIgnoreCase(order)) {
                return page.setAsc(orderField);
            } else {
                return page.setDesc(orderField);
            }
        }

        //没有排序字段，则不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        //默认排序
        if (isAsc) {
            page.setAsc(orderField);
        } else {
            page.setDesc(orderField);
        }

        return page;
    }
}
