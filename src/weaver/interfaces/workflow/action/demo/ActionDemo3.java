// 节点后附加操作 外部接口， 接口来源的加号,
// 接口动作类文件 weaver.interfaces.workflow.action.demo.ActionDemo2 , 接口动作名称，标识随便填
// 参数设置, 添加 actionMaxNum 值为 2000
// 可以从路径设置导入 workflow.ActionDemo2.wewf , 在创建节点 - 节点后附加操作操作 添加上面的类名
package weaver.interfaces.workflow.action.demo;

import weaver.conn.RecordSetTrans;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.*;
import weaver.workflow.request.RequestManager;

import java.util.HashMap;
import java.util.StringJoiner;

public class ActionDemo3 implements Action {

    @Override
    public String execute(RequestInfo request) {
        //获取明细表大对象
        DetailTableInfo detailTableInfo = request.getDetailTableInfo();
        StringJoiner stringJoiner = new StringJoiner(",");
        // 遍历每一个明细表
        for (DetailTable detailTable : detailTableInfo.getDetailTable()) {
            // 遍历每一行数据
            for (Row row : detailTable.getRow()) {
                for (Cell cell : row.getCell()) {
                    if ("mawb1".equals(cell.getName())) {
                        stringJoiner.add(Util.null2String(cell.getValue()));
                    }
                    String name = cell.getName();
                    String value = cell.getValue();
                }

            }
        }

        /*
         * 事务更新
         * 主要要回滚或提交事务，否则数据库不会释放锁
         * */
        RecordSetTrans recordSetTrans = new RecordSetTrans();
        recordSetTrans.setAutoCommit(false);
        try {
            // 更新字符串类型需要拼接下单引号 {}
            recordSetTrans.execute("update formtable_main_26 set zbwb1 = " + stringJoiner.toString() + " where requestid = " + request.getRequestid());
        } catch (Exception e) {
            recordSetTrans.rollback();
            request.getRequestManager().setMessagecontent("更新值报错");
            return FAILURE_AND_CONTINUE;
        }
        recordSetTrans.commit();
        return SUCCESS;
    }
}
