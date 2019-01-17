package ch.cern.cmms.wshub.interceptors;

import java.util.List;
import java.util.Map;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

public class SOAPActionCXFInInterceptor extends AbstractSoapInterceptor {
    public SOAPActionCXFInInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>)soapMessage.get(Message.PROTOCOL_HEADERS));
        headers.remove("SOAPAction");
    }
}