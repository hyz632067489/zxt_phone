package com.zxt.zxt_phone.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hkc on 2017/4/27.
 */

public class SjclListModel implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * statusCode : 200
     * data : {"totalRow":3,"pageCurrent":1,"list":[{"areaApp":{"areaId":0,"areaName":"鲁能西路社区居委会"},"editEventDate":"2017-04-28 09:45:11","eventContent":"","eventId":4,"eventLevel":{"eventLevelId":0,"eventLevelName":"一般"},"eventPic":"","eventTitle":"沙发上","eventType":{"eventTypeId":0,"eventTypeName":"家庭暴力"},"gridApp":{"gridId":0,"gridName":"鲁能西路第四网格"},"gridStaffApp":{"gridStaffId":0,"gridStaffName":"张大善"},"isFinished":0,"isImportant":0,"sourceType":{"sourceTypeId":0,"sourceTypeName":"自己发现"}},{"areaApp":{"areaId":0,"areaName":"鲁能西路社区居委会"},"editEventDate":"2017-04-14 09:51:54","eventContent":"","eventId":3,"eventLevel":{"eventLevelId":0,"eventLevelName":"紧急"},"eventPic":"","eventTitle":"dfd","eventType":{"eventTypeId":0,"eventTypeName":"城市管理"},"gridApp":{"gridId":0,"gridName":"鲁能西路第一网格"},"gridStaffApp":{"gridStaffId":0,"gridStaffName":"张可路"},"isFinished":0,"isImportant":0,"sourceType":{"sourceTypeId":0,"sourceTypeName":"群众反映"}},{"areaApp":{"areaId":0,"areaName":"鲁能西路社区居委会"},"editEventDate":"2017-04-14 09:27:49","eventContent":"","eventId":2,"eventLevel":{"eventLevelId":0,"eventLevelName":"紧急"},"eventPic":"","eventTitle":"2017/4/14","eventType":{"eventTypeId":0,"eventTypeName":"城市管理"},"gridApp":{"gridId":0,"gridName":"鲁能西路第一网格"},"gridStaffApp":{"gridStaffId":0,"gridStaffName":"张可路"},"isFinished":1,"isImportant":1,"sourceType":{"sourceTypeId":0,"sourceTypeName":"群众反映"}}]}
     */

    private int statusCode;
    private DataBean data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * totalRow : 3
         * pageCurrent : 1
         * list : [{"areaApp":{"areaId":0,"areaName":"鲁能西路社区居委会"},"editEventDate":"2017-04-28 09:45:11","eventContent":"","eventId":4,"eventLevel":{"eventLevelId":0,"eventLevelName":"一般"},"eventPic":"","eventTitle":"沙发上","eventType":{"eventTypeId":0,"eventTypeName":"家庭暴力"},"gridApp":{"gridId":0,"gridName":"鲁能西路第四网格"},"gridStaffApp":{"gridStaffId":0,"gridStaffName":"张大善"},"isFinished":0,"isImportant":0,"sourceType":{"sourceTypeId":0,"sourceTypeName":"自己发现"}},{"areaApp":{"areaId":0,"areaName":"鲁能西路社区居委会"},"editEventDate":"2017-04-14 09:51:54","eventContent":"","eventId":3,"eventLevel":{"eventLevelId":0,"eventLevelName":"紧急"},"eventPic":"","eventTitle":"dfd","eventType":{"eventTypeId":0,"eventTypeName":"城市管理"},"gridApp":{"gridId":0,"gridName":"鲁能西路第一网格"},"gridStaffApp":{"gridStaffId":0,"gridStaffName":"张可路"},"isFinished":0,"isImportant":0,"sourceType":{"sourceTypeId":0,"sourceTypeName":"群众反映"}},{"areaApp":{"areaId":0,"areaName":"鲁能西路社区居委会"},"editEventDate":"2017-04-14 09:27:49","eventContent":"","eventId":2,"eventLevel":{"eventLevelId":0,"eventLevelName":"紧急"},"eventPic":"","eventTitle":"2017/4/14","eventType":{"eventTypeId":0,"eventTypeName":"城市管理"},"gridApp":{"gridId":0,"gridName":"鲁能西路第一网格"},"gridStaffApp":{"gridStaffId":0,"gridStaffName":"张可路"},"isFinished":1,"isImportant":1,"sourceType":{"sourceTypeId":0,"sourceTypeName":"群众反映"}}]
         */

        private int totalRow;
        private int pageCurrent;
        private List<ListBean> list;

        public int getTotalRow() {
            return totalRow;
        }

        public void setTotalRow(int totalRow) {
            this.totalRow = totalRow;
        }

        public int getPageCurrent() {
            return pageCurrent;
        }

        public void setPageCurrent(int pageCurrent) {
            this.pageCurrent = pageCurrent;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * areaApp : {"areaId":0,"areaName":"鲁能西路社区居委会"}
             * editEventDate : 2017-04-28 09:45:11
             * eventContent :
             * eventId : 4
             * eventLevel : {"eventLevelId":0,"eventLevelName":"一般"}
             * eventPic :
             * eventTitle : 沙发上
             * eventType : {"eventTypeId":0,"eventTypeName":"家庭暴力"}
             * gridApp : {"gridId":0,"gridName":"鲁能西路第四网格"}
             * gridStaffApp : {"gridStaffId":0,"gridStaffName":"张大善"}
             * isFinished : 0
             * isImportant : 0
             * sourceType : {"sourceTypeId":0,"sourceTypeName":"自己发现"}
             */

            private AreaAppBean areaApp;
            private String editEventDate;
            private String eventContent;
            private String eventId;
            private EventLevelBean eventLevel;
            private String eventPic;
            private String eventTitle;
            private EventTypeBean eventType;
            private GridAppBean gridApp;
            private GridStaffAppBean gridStaffApp;
            private int isFinished;
            private int isImportant;
            private SourceTypeBean sourceType;

            public AreaAppBean getAreaApp() {
                return areaApp;
            }

            public void setAreaApp(AreaAppBean areaApp) {
                this.areaApp = areaApp;
            }

            public String getEditEventDate() {
                return editEventDate;
            }

            public void setEditEventDate(String editEventDate) {
                this.editEventDate = editEventDate;
            }

            public String getEventContent() {
                return eventContent;
            }

            public void setEventContent(String eventContent) {
                this.eventContent = eventContent;
            }

            public String getEventId() {
                return eventId;
            }

            public void setEventId(String eventId) {
                this.eventId = eventId;
            }

            public EventLevelBean getEventLevel() {
                return eventLevel;
            }

            public void setEventLevel(EventLevelBean eventLevel) {
                this.eventLevel = eventLevel;
            }

            public String getEventPic() {
                return eventPic;
            }

            public void setEventPic(String eventPic) {
                this.eventPic = eventPic;
            }

            public String getEventTitle() {
                return eventTitle;
            }

            public void setEventTitle(String eventTitle) {
                this.eventTitle = eventTitle;
            }

            public EventTypeBean getEventType() {
                return eventType;
            }

            public void setEventType(EventTypeBean eventType) {
                this.eventType = eventType;
            }

            public GridAppBean getGridApp() {
                return gridApp;
            }

            public void setGridApp(GridAppBean gridApp) {
                this.gridApp = gridApp;
            }

            public GridStaffAppBean getGridStaffApp() {
                return gridStaffApp;
            }

            public void setGridStaffApp(GridStaffAppBean gridStaffApp) {
                this.gridStaffApp = gridStaffApp;
            }

            public int getIsFinished() {
                return isFinished;
            }

            public void setIsFinished(int isFinished) {
                this.isFinished = isFinished;
            }

            public int getIsImportant() {
                return isImportant;
            }

            public void setIsImportant(int isImportant) {
                this.isImportant = isImportant;
            }

            public SourceTypeBean getSourceType() {
                return sourceType;
            }

            public void setSourceType(SourceTypeBean sourceType) {
                this.sourceType = sourceType;
            }

            public static class AreaAppBean implements Serializable{
                /**
                 * areaId : 0
                 * areaName : 鲁能西路社区居委会
                 */

                private String areaName;

                public String getAreaName() {
                    return areaName;
                }

                public void setAreaName(String areaName) {
                    this.areaName = areaName;
                }
            }

            public static class EventLevelBean implements Serializable{
                /**
                 * eventLevelId : 0
                 * eventLevelName : 一般
                 */

                private String eventLevelName;

                public String getEventLevelName() {
                    return eventLevelName;
                }

                public void setEventLevelName(String eventLevelName) {
                    this.eventLevelName = eventLevelName;
                }
            }

            public static class EventTypeBean implements Serializable{
                /**
                 * eventTypeId : 0
                 * eventTypeName : 家庭暴力
                 */

                private String eventTypeName;

                public String getEventTypeName() {
                    return eventTypeName;
                }

                public void setEventTypeName(String eventTypeName) {
                    this.eventTypeName = eventTypeName;
                }
            }

            public static class GridAppBean implements Serializable{
                /**
                 * gridId : 0
                 * gridName : 鲁能西路第四网格
                 */

                private String gridName;

                public String getGridName() {
                    return gridName;
                }

                public void setGridName(String gridName) {
                    this.gridName = gridName;
                }
            }

            public static class GridStaffAppBean implements Serializable{
                /**
                 * gridStaffId : 0
                 * gridStaffName : 张大善
                 */

                private String gridStaffName;

                public String getGridStaffName() {
                    return gridStaffName;
                }

                public void setGridStaffName(String gridStaffName) {
                    this.gridStaffName = gridStaffName;
                }
            }

            public static class SourceTypeBean implements Serializable{
                /**
                 * sourceTypeId : 0
                 * sourceTypeName : 自己发现
                 */

                private String sourceTypeName;

                public String getSourceTypeName() {
                    return sourceTypeName;
                }

                public void setSourceTypeName(String sourceTypeName) {
                    this.sourceTypeName = sourceTypeName;
                }
            }
        }
    }
}
