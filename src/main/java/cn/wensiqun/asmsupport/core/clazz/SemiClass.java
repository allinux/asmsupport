package cn.wensiqun.asmsupport.core.clazz;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import cn.wensiqun.asmsupport.core.definition.variable.meta.GlobalVariableMeta;
import cn.wensiqun.asmsupport.core.utils.lang.InterfaceLooper;
import cn.wensiqun.asmsupport.core.utils.reflect.ModifierUtils;
import cn.wensiqun.asmsupport.org.objectweb.asm.Opcodes;


/**
 * 
 * @author 温斯群(Joe Wen)
 *
 */
public class SemiClass extends NewMemberClass {

	SemiClass(int version, int access, String name, Class<?> superCls,
            Class<?>[] interfaces) {
        this.version = version;
        this.name = name;
        this.mod = access;
        this.superClass = superCls;
        this.interfaces = interfaces;

        if(!ModifierUtils.isInterface(mod)){
            this.mod += Opcodes.ACC_SUPER;
        }
        
    }

    @Override
    public String getDescription() {
        return new StringBuilder("L").append(getName().replace(".", "/"))
                .append(";").toString();
    }
    
    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public int getDimension() {
        return -1;
    }

    @Override
    public int getCastOrder() {
        return 9;
    }

    @Override
    public List<GlobalVariableMeta> getGlobalVariableMeta(final String name) {
        
        final LinkedList<GlobalVariableMeta> found = new LinkedList<GlobalVariableMeta>();
        
        for(GlobalVariableMeta gv : getGlobalVariableMetas()){
            if(gv.getName().equals(name)){
                found.add(gv);
            }
        }
        
        if(found.isEmpty()) {
            Class<?> fieldOwner = getSuperClass();
            for(;!fieldOwner.equals(Object.class); fieldOwner = fieldOwner.getSuperclass()){
                try {
                    Field f = fieldOwner.getDeclaredField(name);
                    found.add(new GlobalVariableMeta(this,
                            AClassFactory.getProductClass(fieldOwner),
                            AClassFactory.getProductClass(f.getType()), f.getModifiers(), name));
                    break;
                } catch (NoSuchFieldException e) {
                }
            }
        }
        
        new InterfaceLooper() {
            @Override
            protected boolean process(Class<?> inter) {
                try {
                    Field f = inter.getDeclaredField(name);
                    found.add(new GlobalVariableMeta(SemiClass.this,
                            AClassFactory.getProductClass(inter),
                            AClassFactory.getProductClass(f.getType()), f.getModifiers(), name));
                    return true;
                } catch (NoSuchFieldException e) {
                    return false;
                }
            }
        }.loop(getInterfaces());
        
        return found;
    }

    /*@Override
    public GlobalVariable getGlobalVariable(String name) {
        for(GlobalVariable gv : getGlobalVariables()){
            if(gv.getVariableMeta().getName().equals(name)){
                return gv;
            }
        }
        
        Class<?> fieldOwner = superClass;
        Field f = null;
        for(;!fieldOwner.equals(Object.class)  ;fieldOwner = fieldOwner.getSuperclass()){
            try {
                f = fieldOwner.getDeclaredField(name);
                break;
            } catch (NoSuchFieldException e) {
            }
        }
        
        if(f == null){
            throw new ASMSupportException("no such field exception : " + name);
        }
        
        return new GlobalVariable(this, AClassFactory.getProductClass(fieldOwner),
                new ProductClass(f.getType()), f.getModifiers(), name);
    }*/
}