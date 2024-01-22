// 节点后附加操作 外部接口， 接口来源的加号,
// 接口动作类文件 weaver.interfaces.workflow.action.demo.ActionDemo1 , 接口动作名称，标识随便填
// 可以从路径设置导入 workflow.ActionDemo1.wewf , 在创建节点 - 节点后附加操作操作 添加上面的类名
package weaver.interfaces.workflow.action.demo;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

import java.util.HashMap;

// 正常有个 RollBackAction 可以执行回滚逻辑  1206AM【E9】附加操作Action及操作者Action相关开发 1:13:32
public class ActionDemo1 implements Action {
    @Override
    public String execute(RequestInfo request) {
        String requestid = request.getRequestid();

        // 流转上下文信息
        RequestManager requestManager = request.getRequestManager();
        // 表单的主键id
        int billid = requestManager.getBillid();

        //主表字段值
        MainTableInfo mainTableInfo = request.getMainTableInfo();

        // 路径id
        String workflowid = request.getWorkflowid();
        Property[] property = mainTableInfo.getProperty();

        // 存放表单
        HashMap<String, String> fieldValueMap = new HashMap<>();
        for (Property property1 : property) {
            fieldValueMap.put(property1.getName(), property1.getValue());
        }

        String je = Util.null2String(fieldValueMap.get("je")); // jin e 金额
        RecordSet recordSet = new RecordSet();
        recordSet.execute("update formtabl_main_24 set wb1 = " + je + " where requestid=" + requestid);
        return null;
    }
}
