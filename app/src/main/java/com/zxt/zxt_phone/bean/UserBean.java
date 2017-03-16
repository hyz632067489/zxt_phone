package com.zxt.zxt_phone.bean;

import java.util.List;

/**
 * Created by hyz on 2017/3/8.
 * powered by company
 */

public class UserBean {

    /**
     * userId : 2
     * areaId : 28
     * roleId : 1
     * userName : admin
     * password : ******
     * headIcon : images/user/201703061320372.jpg
     * mobileTel : 15854435787
     * officeTel :
     * editUserName : admin
     * editUserDate : 1488777637000
     * user_enable : 1
     * role : {"roleId":1,"roleName":"系统管理员","roleMemo":"系统最高权限,管理系统所有的功能","editRoleName":"admin","editRoleDate":1484993213000,"role_enable":1,"roleLevel":0,"permissions":[],"menus":[],"userCount":null}
     * area : {"areaId":null,"areaCode":null,"areaParentCode":null,"areaName":null,"shortName":null,"areaLevel":null,"nodes":[]}
     */

    private int userId;
    private int areaId;
    private int roleId;
    private String userName;
    private String password;
    private String headIcon;
    private String mobileTel;
    private String officeTel;
    private String editUserName;
    private long editUserDate;
    private int user_enable;
    private RoleBean role;
    private AreaBean area;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getEditUserName() {
        return editUserName;
    }

    public void setEditUserName(String editUserName) {
        this.editUserName = editUserName;
    }

    public long getEditUserDate() {
        return editUserDate;
    }

    public void setEditUserDate(long editUserDate) {
        this.editUserDate = editUserDate;
    }

    public int getUser_enable() {
        return user_enable;
    }

    public void setUser_enable(int user_enable) {
        this.user_enable = user_enable;
    }

    public RoleBean getRole() {
        return role;
    }

    public void setRole(RoleBean role) {
        this.role = role;
    }

    public AreaBean getArea() {
        return area;
    }

    public void setArea(AreaBean area) {
        this.area = area;
    }

    public static class RoleBean {
        /**
         * roleId : 1
         * roleName : 系统管理员
         * roleMemo : 系统最高权限,管理系统所有的功能
         * editRoleName : admin
         * editRoleDate : 1484993213000
         * role_enable : 1
         * roleLevel : 0
         * permissions : []
         * menus : []
         * userCount : null
         */

        private int roleId;
        private String roleName;
        private String roleMemo;
        private String editRoleName;
        private long editRoleDate;
        private int role_enable;
        private int roleLevel;
        private Object userCount;
        private List<?> permissions;
        private List<?> menus;

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleMemo() {
            return roleMemo;
        }

        public void setRoleMemo(String roleMemo) {
            this.roleMemo = roleMemo;
        }

        public String getEditRoleName() {
            return editRoleName;
        }

        public void setEditRoleName(String editRoleName) {
            this.editRoleName = editRoleName;
        }

        public long getEditRoleDate() {
            return editRoleDate;
        }

        public void setEditRoleDate(long editRoleDate) {
            this.editRoleDate = editRoleDate;
        }

        public int getRole_enable() {
            return role_enable;
        }

        public void setRole_enable(int role_enable) {
            this.role_enable = role_enable;
        }

        public int getRoleLevel() {
            return roleLevel;
        }

        public void setRoleLevel(int roleLevel) {
            this.roleLevel = roleLevel;
        }

        public Object getUserCount() {
            return userCount;
        }

        public void setUserCount(Object userCount) {
            this.userCount = userCount;
        }

        public List<?> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<?> permissions) {
            this.permissions = permissions;
        }

        public List<?> getMenus() {
            return menus;
        }

        public void setMenus(List<?> menus) {
            this.menus = menus;
        }
    }

    public static class AreaBean {
        /**
         * areaId : null
         * areaCode : null
         * areaParentCode : null
         * areaName : null
         * shortName : null
         * areaLevel : null
         * nodes : []
         */

        private Object areaId;
        private Object areaCode;
        private Object areaParentCode;
        private Object areaName;
        private Object shortName;
        private Object areaLevel;
        private List<?> nodes;

        public Object getAreaId() {
            return areaId;
        }

        public void setAreaId(Object areaId) {
            this.areaId = areaId;
        }

        public Object getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(Object areaCode) {
            this.areaCode = areaCode;
        }

        public Object getAreaParentCode() {
            return areaParentCode;
        }

        public void setAreaParentCode(Object areaParentCode) {
            this.areaParentCode = areaParentCode;
        }

        public Object getAreaName() {
            return areaName;
        }

        public void setAreaName(Object areaName) {
            this.areaName = areaName;
        }

        public Object getShortName() {
            return shortName;
        }

        public void setShortName(Object shortName) {
            this.shortName = shortName;
        }

        public Object getAreaLevel() {
            return areaLevel;
        }

        public void setAreaLevel(Object areaLevel) {
            this.areaLevel = areaLevel;
        }

        public List<?> getNodes() {
            return nodes;
        }

        public void setNodes(List<?> nodes) {
            this.nodes = nodes;
        }
    }
}
