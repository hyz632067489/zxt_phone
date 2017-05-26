package com.zxt.zxt_phone.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hkc on 2017/5/26.
 */

public class ShopTypeModel implements Serializable{

    /**
     * statusCode : 200
     * msg : 加载成功!
     * data : [{"className":"男女服装","sequence":0,"childs":[{"className":"男装","sequence":1,"parent":null,"childs":[{"className":"男西服","sequence":1,"parent":null,"childs":[],"level":2,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":21,"addTime":"2017-05-24 19:01:44","deleteStatus":false},{"className":"男衬衫","sequence":2,"parent":null,"childs":[],"level":2,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":22,"addTime":"2017-05-24 19:06:07","deleteStatus":false}],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":2,"addTime":"2013-12-24 10:22:46","deleteStatus":false},{"className":"女装","sequence":2,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":3,"addTime":"2013-12-24 10:22:55","deleteStatus":false},{"className":"童装","sequence":3,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":4,"addTime":"2013-12-24 10:23:12","deleteStatus":false},{"className":"家居内衣","sequence":4,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":5,"addTime":"2013-12-24 10:24:38","deleteStatus":false}],"level":0,"description_evaluate":4.7,"service_evaluate":2,"ship_evaluate":2.8,"id":1,"addTime":"2013-12-24 10:22:29","deleteStatus":false},{"className":"鞋包配饰","sequence":1,"childs":[{"className":"男鞋","sequence":1,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":7,"addTime":"2013-12-24 10:25:12","deleteStatus":false},{"className":"女鞋","sequence":2,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":8,"addTime":"2013-12-24 10:25:22","deleteStatus":false},{"className":"男包/女包","sequence":3,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":9,"addTime":"2013-12-24 10:25:47","deleteStatus":false},{"className":"旅行箱包","sequence":4,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":10,"addTime":"2013-12-24 10:26:06","deleteStatus":false},{"className":"时尚配饰","sequence":5,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":11,"addTime":"2013-12-24 10:26:26","deleteStatus":false}],"level":0,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":6,"addTime":"2013-12-24 10:24:56","deleteStatus":false},{"className":"户外运动","sequence":3,"childs":[{"className":"运动服/运动鞋","sequence":1,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":13,"addTime":"2013-12-24 10:27:24","deleteStatus":false},{"className":"户外装备","sequence":1,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":14,"addTime":"2013-12-24 10:27:37","deleteStatus":false},{"className":"运动健身","sequence":2,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":15,"addTime":"2013-12-24 10:27:47","deleteStatus":false}],"level":0,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":12,"addTime":"2013-12-24 10:26:56","deleteStatus":false},{"className":"数码电子","sequence":4,"childs":[{"className":"笔记本电脑","sequence":1,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":17,"addTime":"2013-12-24 10:28:26","deleteStatus":false},{"className":"摄像照相","sequence":1,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":18,"addTime":"2013-12-24 10:28:36","deleteStatus":false},{"className":"办公数码","sequence":2,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":19,"addTime":"2013-12-24 10:28:48","deleteStatus":false},{"className":"数码配件","sequence":4,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":20,"addTime":"2013-12-24 10:29:01","deleteStatus":false}],"level":0,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":16,"addTime":"2013-12-24 10:28:09","deleteStatus":false},{"className":"重庆优品","sequence":5,"childs":[{"className":"水果蔬菜","sequence":1,"parent":null,"childs":[{"className":"一品哈密瓜","sequence":1,"parent":null,"childs":[],"level":2,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":28,"addTime":"2017-05-25 09:02:44","deleteStatus":false}],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":27,"addTime":"2017-05-25 09:02:13","deleteStatus":false}],"level":0,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":23,"addTime":"2017-05-25 08:52:24","deleteStatus":false},{"className":"重庆土特产","sequence":6,"childs":[{"className":"重庆火锅","sequence":1,"parent":null,"childs":[{"className":"重庆宽板凳老火锅","sequence":1,"parent":null,"childs":[],"level":2,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":26,"addTime":"2017-05-25 09:01:20","deleteStatus":false}],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":25,"addTime":"2017-05-25 09:00:45","deleteStatus":false}],"level":0,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":24,"addTime":"2017-05-25 08:55:06","deleteStatus":false}]
     */

    private int statusCode;
    private String msg;
    private List<DataBean> data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * className : 男女服装
         * sequence : 0
         * childs : [{"className":"男装","sequence":1,"parent":null,"childs":[{"className":"男西服","sequence":1,"parent":null,"childs":[],"level":2,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":21,"addTime":"2017-05-24 19:01:44","deleteStatus":false},{"className":"男衬衫","sequence":2,"parent":null,"childs":[],"level":2,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":22,"addTime":"2017-05-24 19:06:07","deleteStatus":false}],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":2,"addTime":"2013-12-24 10:22:46","deleteStatus":false},{"className":"女装","sequence":2,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":3,"addTime":"2013-12-24 10:22:55","deleteStatus":false},{"className":"童装","sequence":3,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":4,"addTime":"2013-12-24 10:23:12","deleteStatus":false},{"className":"家居内衣","sequence":4,"parent":null,"childs":[],"level":1,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":5,"addTime":"2013-12-24 10:24:38","deleteStatus":false}]
         * level : 0
         * description_evaluate : 4.7
         * service_evaluate : 2
         * ship_evaluate : 2.8
         * id : 1
         * addTime : 2013-12-24 10:22:29
         * deleteStatus : false
         */

        private String className;
        private int sequence;
        private int level;
        private double description_evaluate;
        private int service_evaluate;
        private double ship_evaluate;
        private int id;
        private String addTime;
        private boolean deleteStatus;
        private List<ChildsBeanX> childs;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public double getDescription_evaluate() {
            return description_evaluate;
        }

        public void setDescription_evaluate(double description_evaluate) {
            this.description_evaluate = description_evaluate;
        }

        public int getService_evaluate() {
            return service_evaluate;
        }

        public void setService_evaluate(int service_evaluate) {
            this.service_evaluate = service_evaluate;
        }

        public double getShip_evaluate() {
            return ship_evaluate;
        }

        public void setShip_evaluate(double ship_evaluate) {
            this.ship_evaluate = ship_evaluate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public boolean isDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(boolean deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public List<ChildsBeanX> getChilds() {
            return childs;
        }

        public void setChilds(List<ChildsBeanX> childs) {
            this.childs = childs;
        }

        public static class ChildsBeanX implements Serializable{
            /**
             * className : 男装
             * sequence : 1
             * parent : null
             * childs : [{"className":"男西服","sequence":1,"parent":null,"childs":[],"level":2,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":21,"addTime":"2017-05-24 19:01:44","deleteStatus":false},{"className":"男衬衫","sequence":2,"parent":null,"childs":[],"level":2,"description_evaluate":0,"service_evaluate":0,"ship_evaluate":0,"id":22,"addTime":"2017-05-24 19:06:07","deleteStatus":false}]
             * level : 1
             * description_evaluate : 0
             * service_evaluate : 0
             * ship_evaluate : 0
             * id : 2
             * addTime : 2013-12-24 10:22:46
             * deleteStatus : false
             */

            private String className;
            private int sequence;
            private Object parent;
            private int level;
            private int description_evaluate;
            private int service_evaluate;
            private int ship_evaluate;
            private int id;
            private String addTime;
            private boolean deleteStatus;
            private List<ChildsBean> childs;

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }

            public Object getParent() {
                return parent;
            }

            public void setParent(Object parent) {
                this.parent = parent;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getDescription_evaluate() {
                return description_evaluate;
            }

            public void setDescription_evaluate(int description_evaluate) {
                this.description_evaluate = description_evaluate;
            }

            public int getService_evaluate() {
                return service_evaluate;
            }

            public void setService_evaluate(int service_evaluate) {
                this.service_evaluate = service_evaluate;
            }

            public int getShip_evaluate() {
                return ship_evaluate;
            }

            public void setShip_evaluate(int ship_evaluate) {
                this.ship_evaluate = ship_evaluate;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public boolean isDeleteStatus() {
                return deleteStatus;
            }

            public void setDeleteStatus(boolean deleteStatus) {
                this.deleteStatus = deleteStatus;
            }

            public List<ChildsBean> getChilds() {
                return childs;
            }

            public void setChilds(List<ChildsBean> childs) {
                this.childs = childs;
            }

            public static class ChildsBean implements Serializable{
                /**
                 * className : 男西服
                 * sequence : 1
                 * parent : null
                 * childs : []
                 * level : 2
                 * description_evaluate : 0
                 * service_evaluate : 0
                 * ship_evaluate : 0
                 * id : 21
                 * addTime : 2017-05-24 19:01:44
                 * deleteStatus : false
                 */

                private String className;
                private int sequence;
                private Object parent;
                private int level;
                private int description_evaluate;
                private int service_evaluate;
                private int ship_evaluate;
                private int id;
                private String addTime;
                private boolean deleteStatus;
                private List<?> childs;

                public String getClassName() {
                    return className;
                }

                public void setClassName(String className) {
                    this.className = className;
                }

                public int getSequence() {
                    return sequence;
                }

                public void setSequence(int sequence) {
                    this.sequence = sequence;
                }

                public Object getParent() {
                    return parent;
                }

                public void setParent(Object parent) {
                    this.parent = parent;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getDescription_evaluate() {
                    return description_evaluate;
                }

                public void setDescription_evaluate(int description_evaluate) {
                    this.description_evaluate = description_evaluate;
                }

                public int getService_evaluate() {
                    return service_evaluate;
                }

                public void setService_evaluate(int service_evaluate) {
                    this.service_evaluate = service_evaluate;
                }

                public int getShip_evaluate() {
                    return ship_evaluate;
                }

                public void setShip_evaluate(int ship_evaluate) {
                    this.ship_evaluate = ship_evaluate;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getAddTime() {
                    return addTime;
                }

                public void setAddTime(String addTime) {
                    this.addTime = addTime;
                }

                public boolean isDeleteStatus() {
                    return deleteStatus;
                }

                public void setDeleteStatus(boolean deleteStatus) {
                    this.deleteStatus = deleteStatus;
                }

                public List<?> getChilds() {
                    return childs;
                }

                public void setChilds(List<?> childs) {
                    this.childs = childs;
                }
            }
        }
    }
}
