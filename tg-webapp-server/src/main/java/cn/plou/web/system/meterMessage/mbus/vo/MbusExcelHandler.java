package cn.plou.web.system.meterMessage.mbus.vo;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import cn.plou.web.system.meterMessage.mbus.entity.Mbus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MbusExcelHandler extends ExcelDataHandlerDefaultImpl<Mbus> {
    private static final Logger log = LoggerFactory.getLogger(MbusExcelHandler.class);
    @Override
    public Object importHandler(Mbus obj, String name, Object value) {
        log.info(name+":"+value);
        return super.importHandler(obj, name, value);
    }
}
