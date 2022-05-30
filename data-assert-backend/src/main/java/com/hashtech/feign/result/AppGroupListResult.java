package com.hashtech.feign.result;

import java.io.Serializable;
import java.util.List;

public class AppGroupListResult  implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private List<AppListResult> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AppListResult> getChildren() {
        return children;
    }

    public void setChildren(List<AppListResult> children) {
        this.children = children;
    }

    class AppListResult implements Serializable {
        private static final long serialVersionUID = 1L;
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
