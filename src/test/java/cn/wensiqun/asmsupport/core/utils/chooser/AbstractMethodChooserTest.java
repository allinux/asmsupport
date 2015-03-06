package cn.wensiqun.asmsupport.core.utils.chooser;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.wensiqun.asmsupport.core.clazz.AClass;
import cn.wensiqun.asmsupport.core.clazz.AClassFactory;
import cn.wensiqun.asmsupport.core.definition.method.meta.AMethodMeta;
import cn.wensiqun.asmsupport.core.utils.jls15_12_2.MethodChooser;

public class AbstractMethodChooserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIdentifyPotentiallyApplicableMethods() {
		MethodChooser am = new MethodChooser(AClassFactory.getProductClass(this.getClass()), 
				AClassFactory.getProductClass(Child.class), 
				"work", new AClass[]{AClass.OBJECT_ACLASS}){

			@Override
			public AMethodMeta firstPhase() {
				return null;
			}

			@Override
			public AMethodMeta secondPhase() {
				return null;
			}

			@Override
			public AMethodMeta thirdPhase() {
				return null;
			}
			
		};
		Map<AClass, List<AMethodMeta>> map = am.identifyPotentiallyApplicableMethods();
		System.out.println(map);
	}

}