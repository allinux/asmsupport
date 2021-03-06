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
package cn.wensiqun.asmsupport.core.definition.variable;

import cn.wensiqun.asmsupport.standard.def.clazz.ArrayClass;
import cn.wensiqun.asmsupport.standard.def.clazz.IClass;
import cn.wensiqun.asmsupport.standard.def.var.meta.Field;
import cn.wensiqun.asmsupport.standard.error.ASMSupportException;
import cn.wensiqun.asmsupport.utils.Modifiers;

/**
 * @author wensiqun at 163.com(Joe Wen)
 *
 */
public abstract class AbstractVariable implements IVariable {

    @Override
    public final void asArgument() {
        //don't do anything if this class don't extends AbstractExecuteable
    }

    @Override
    public final GlobalVariable field(String name) {
        if(this.getResultType() instanceof ArrayClass){
            throw new ASMSupportException("Cannot get global variable from array type variable : " + this);
        }
		try {
			Field field = getMeta().getType().getField(name);
	        if(Modifiers.isStatic(field.getModifiers())){
	            return new StaticGlobalVariable(field.getDeclaringClass(), field);
	        } else {
	            return new NonStaticGlobalVariable(this, field);
	        }
		} catch (NoSuchFieldException e) {
			throw new ASMSupportException(e);
		}
    }

    @Override
    public final String getName() {
        return getMeta().getName();
    }

    @Override
    public final IClass getFormerType() {
        return getResultType();
    }

    @Override
    public final int getModifiers() {
        return getMeta().getModifiers();
    }
    
    @Override
    public final String toString() {
        return getName();
    }

    
}
