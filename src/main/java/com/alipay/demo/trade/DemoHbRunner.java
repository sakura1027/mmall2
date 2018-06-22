package com.alipay.demo.trade;

import com.alipay.demo.trade.model.builder.AlipayHeartbeatSynRequestBuilder;
import com.alipay.demo.trade.model.hb.*;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.impl.hb.AbsHbRunner;
import com.alipay.demo.trade.service.impl.hb.HbQueue;
import com.alipay.demo.trade.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DemoHbRunner extends AbsHbRunner {
    public DemoHbRunner(AlipayMonitorService monitorService) {
        super(monitorService);
    }

    @Override
    public String getAppAuthToken() {
        return null;
    }

    @Override
    public AlipayHeartbeatSynRequestBuilder getBuilder() {
        List<SysTradeInfo> sysTradeInfoList = HbQueue.poll();
        List<ExceptionInfo> exceptionInfoList = new ArrayList<>();

        AlipayHeartbeatSynRequestBuilder builder = new AlipayHeartbeatSynRequestBuilder()
                .setProduct(Product.FP).setType(Type.CR).setEquipmentId("cr1000001")
                .setEquipmentStatus(EquipStatus.NORMAL).setTime(Utils.toDate(new Date()))
                .setStoreId("store10001").setMac("0a:00:27:00:00:00").setNetworkType("LAN")
                .setProviderId("2088911212323549")
                .setSysTradeInfoList(sysTradeInfoList)
                .setExceptionInfoList(exceptionInfoList);
        return builder;
    }
}
