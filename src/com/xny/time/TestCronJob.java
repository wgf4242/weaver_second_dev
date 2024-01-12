package com.xny.time;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weaver.conn.RecordSet;
import weaver.interfaces.schedule.BaseCronJob;


public class TestCronJob  extends BaseCronJob {
    Log log = LogFactory.getLog(TestCronJob.class);

    @Override
    public void execute() {
        log.error("定时任务执行测试任务");
        RecordSet rs = new RecordSet();
        rs.execute("select * from hrmresource");
        if (rs.next()) {
            log.error("lastname:" + rs.getString("lasetname"));
        }
    }
}
