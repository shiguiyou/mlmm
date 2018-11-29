package com.wanquan.mlmmx.mlmm.beans;

import java.util.Comparator;

public class PeopleComparatpor implements Comparator<People> {
    private String type;
    /**
     * 1降序
     * 其余升序
     * */
  public PeopleComparatpor(String type){
      this.type=type;
  }
    @Override
    public int compare(People lhs, People rhs) {
        // TODO 自动生成的方法存根
        if(type.equals("1")){
            return Integer.parseInt(rhs.getTime())-Integer.parseInt(lhs.getTime());
        }else{
            return Integer.parseInt(lhs.getTime())-Integer.parseInt(rhs.getTime());
        }
    }

}