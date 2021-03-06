package cn.wensiqun.asmsupport.core.block.method;

import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;

/**
 * Created by sqwen on 2016/7/1.
 */
public abstract class NoReturnMethodBody extends AbstractMethodBody {

    private static final Log LOG = LogFactory.getLog(NoReturnMethodBody.class);

    private boolean returned = false;

    @Override
    public void doExecute() {
        super.doExecute();
        if(!returned) {
            if(LOG.isPrintEnabled()) {
                LOG.print("direct return from method");
            }
            getMethod().getInstructions().returnInsn();
        }
    }

    @Deprecated
    @Override
    public void return_() {
        returned = true;
        super.return_();
    }

}
