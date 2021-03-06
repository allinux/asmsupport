package cn.wensiqun.asmsupport.client.def;

import cn.wensiqun.asmsupport.core.definition.KernelParam;

import java.lang.reflect.Array;

public class ParamPostern {

    /*public static KernelParam getTarget(Param param) {
        return param.getTarget();
    }*/
    
    public static KernelParam[] getTarget(Param[] params) {
        if(params == null) {
            return null;
        }
        KernelParam[] paras = new KernelParam[params.length];
        for(int i=0; i<params.length; i++) {
            paras[i] = params[i].getTarget();
        }
        return paras;
    }
    
    public static KernelParam[] getTarget(Param[] params, int start) {
        if(params == null || params.length == 0) {
            return null;
        }
        KernelParam[] paras = new KernelParam[params.length - start];
        for(int i=start; i<params.length; i++) {
            paras[i] = params[i].getTarget();
        }
        return paras;
    }
    
    
    
    public static Object getTarget(Object clientArray) {
        if(clientArray == null) {
            throw new NullPointerException("Client is null.");
        }
        if(clientArray.getClass().isArray()) {
            int len = Array.getLength(clientArray);
            Object[] internalArray = new Object[len];
            for(int i=0; i<len; i++) {
                internalArray[i] = getTarget(Array.get(clientArray, i));
            }
            return internalArray;
        } else if (clientArray instanceof Param){
            return ((Param)clientArray).getTarget();
        }
        throw new IllegalArgumentException();
    }
    
    public static Param[] unionParam(Param p, Param... ps) {
        if(ps == null || ps.length == 0) {
            return new Param[]{p};
        } else {
            Param[] operands = new Param[1 + ps.length];
            operands[0] = p;
            System.arraycopy(ps, 0, operands, 1, ps.length);
            return operands;
        }
    }
}
