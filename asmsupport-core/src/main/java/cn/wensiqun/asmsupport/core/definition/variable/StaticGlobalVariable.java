/**
 * Asmsupport is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.wensiqun.asmsupport.core.definition.variable;

import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.AbstractOperator;
import cn.wensiqun.asmsupport.core.utils.log.Log;
import cn.wensiqun.asmsupport.core.utils.log.LogFactory;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.utils.IClassUtils;

/**
 * Represent a static global variable
 *
 */
public class StaticGlobalVariable extends GlobalVariable {

    private static final Log LOG = LogFactory.getLog(StaticGlobalVariable.class);

    private IClass owner;

    /**
     *
     * @param owner
     * @param meta
     */
    public StaticGlobalVariable(IClass owner, Field meta) {
        super(meta);
        this.owner = owner;
    }

    public IClass getOwner() {
        return owner;
    }

    @Override
    public IClass getResultType() {
        return meta.getType();
    }

    @Override
    public void loadToStack(KernelProgramBlock block) {
        if (!IClassUtils.visible(block.getMethodDeclaringClass(), meta.getDirectOwnerType(), meta.getDeclaringClass(),
                meta.getModifiers())) {
            throw new IllegalArgumentException("Cannot access field " + meta.getDeclaringClass() + "#"
                    + meta.getName() + " from " + block.getMethodDeclaringClass());
        }

        if (LOG.isPrintEnabled()) {
            LOG.print("get field " + meta.getName() + " from class " + meta.getDirectOwnerType().getName()
                    + " and push to stack!");
        }
        block.getMethod().getInstructions().getStatic(owner.getType(), meta.getName(), meta.getType().getType());
    }

    @Override
    public boolean availableFor(AbstractOperator operator) {
        return true;
    }

}
