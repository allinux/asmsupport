/**    
 *  Asmsupport is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package cn.wensiqun.asmsupport.core.operator.common;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.AbstractParamOperator;
import cn.wensiqun.asmsupport.core.operator.Operator;
import cn.wensiqun.asmsupport.core.operator.method.MethodInvoker;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelStrAdd extends AbstractParamOperator {
    
    private KernelParam[] paras;

    private boolean byOtherUsed;
    private MethodInvoker invoker;
    
    protected KernelStrAdd(KernelProgramBlock block, KernelParam par1, KernelParam... pars) {
        super(block, Operator.COMMON);
        this.paras = new KernelParam[pars.length + 1];
        this.paras[0] = par1;
        System.arraycopy(pars, 0, this.paras, 1, pars.length);
        
        IClass strBlderCls = getType(StringBuilder.class);

        MethodInvoker mi = block.call(block.new_(strBlderCls), "append", par1);
        for(KernelParam par : pars){
            mi = block.call(mi, "append", par);
        }
        invoker = block.call(mi, "toString");
    }

    @Override
    protected void verifyArgument() {
        // Do nothing
    }

    @Override
    protected void checkAsArgument() {
        // Do nothing
    }
    
    @Override
    public void execute() {
        if(byOtherUsed){
            super.execute();
        }else{
            throw new ASMSupportException("the string append operator has not been used by other operator.");
        }
    }

    @Override
    protected void doExecute() {
        if(paras.length == 1){
            paras[0].loadToStack(block);
        }else{
            invoker.loadToStack(block);
        }
    }

    @Override
    public void loadToStack(KernelProgramBlock block) {
        this.execute();
    }

    @Override
    public IClass getResultType() {
        return getType(String.class);
    }

    @Override
    public void asArgument() {
        byOtherUsed = true;
        block.removeExe(this);
        if(paras.length == 1){
            paras[0].asArgument();
        }else{
            invoker.asArgument();
        }
    }

}
