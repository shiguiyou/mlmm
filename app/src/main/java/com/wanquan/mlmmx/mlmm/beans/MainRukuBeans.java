package com.wanquan.mlmmx.mlmm.beans;

/**
 * Created by Administrator on 2017/8/31.
 */

public class MainRukuBeans {


    /**
     * data : {"commonParam":"","msg":"操作成功！","result":{"msg":"操作成功！","result":1},"totalCount":0,"validatePass":true,"validateResult":1}
     * dynamicKey :
     * msg : 操作成功！
     * result : true
     * resultCode : 1
     * token :
     */

    private DataBean data;
    private String dynamicKey;
    private String msg;
    private String result;
    private int resultCode;
    private String token;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDynamicKey() {
        return dynamicKey;
    }

    public void setDynamicKey(String dynamicKey) {
        this.dynamicKey = dynamicKey;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class DataBean {
        /**
         * commonParam :
         * msg : 操作成功！
         * result : {"msg":"操作成功！","result":1}
         * totalCount : 0
         * validatePass : true
         * validateResult : 1
         */

        private String commonParam;
        private String msg;
        private ResultBean result;
        private int totalCount;
        private boolean validatePass;
        private int validateResult;

        public String getCommonParam() {
            return commonParam;
        }

        public void setCommonParam(String commonParam) {
            this.commonParam = commonParam;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public boolean isValidatePass() {
            return validatePass;
        }

        public void setValidatePass(boolean validatePass) {
            this.validatePass = validatePass;
        }

        public int getValidateResult() {
            return validateResult;
        }

        public void setValidateResult(int validateResult) {
            this.validateResult = validateResult;
        }

        public static class ResultBean {
            /**
             * msg : 操作成功！
             * result : 1
             */

            private String msg;
            private int result;

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public int getResult() {
                return result;
            }

            public void setResult(int result) {
                this.result = result;
            }
        }
    }
}
