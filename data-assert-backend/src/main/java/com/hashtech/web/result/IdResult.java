package com.hashtech.web.result;

/**
 * @author xujie
 * @description 返回当前删除主题、资源的id，及其下一个id，无下一个id就返回第一个id
 * 前端用做高亮
 * @create 2021-12-16 16:25
 **/
//@Data
public class IdResult {
    private String current;
    private String next;
    private String previous;
    //当前资源的主题id
    private String themeId;

    public IdResult() {
    }

    public IdResult(String current, String next, String previous, String themeId) {
        this.current = current;
        this.next = next;
        this.previous = previous;
        this.themeId = themeId;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }
}
