package uk.gov.ros.teaching.functionalinterfaces;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.Test;

public class FunctionalInterfaceTests {
	
//	private <R> void printStuff(Function<? super String,? extends R> aFunction, String thing) {
//		
//		R result = aFunction.apply(thing);
//		System.out.println(result);
//	}
	
	private <T> T printStuff(Supplier<? extends T> sup) {
		T result  = sup.get();
		System.out.println(result);
		return result;
	}
	private <T,R> R printStuff(Function<? super T,? extends R> func, T item) {
		R result  = func.apply(item);
		System.out.println(result);
		return result;
	}

	@Test
	public void testFunctionInterfaceArgsrContravariant(){
		printStuff(x->x.toUpperCase(),"whee"); //function
		printStuff(Object::toString,"whee"); //function
		printStuff(()->"whee"); //supplier
		printStuff(a -> a.toUpperCase()  //supplier
				,printStuff(()->Arrays.asList(new String []{"w","h","e","e"})).stream().collect(Collectors.joining(""))); //function
		String val = printStuff(a -> a.stream().collect(Collectors.joining("")), Arrays.asList(new String []{"w","h","e","e"}));//function
		printStuff(x->x.contains("whee"),val);//function 
	}
}
