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
package cn.wensiqun.asmsupport.core.operator.logical;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.KernelParam;
import cn.wensiqun.asmsupport.core.operator.Operator;

/**
 * 
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public class KernelLogicalAnd extends BinaryLogical {
    
    protected KernelLogicalAnd(KernelProgramBlock block, KernelParam factor1, KernelParam factor2) {
        super(block, factor1, factor2, Operator.BIT_AND);
    }

    @Override
    protected void executing() {
        insnHelper.bitAnd(AClassFactory.getType(boolean.class).getType());    
    }

}
