package uk.gov.ros.teaching.lambdas;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class LambdasTest {
	private Lambdas testee;
	private Path testRoot;

	@Before
	public void setUp() throws IOException {
		testee = new Lambdas();
		testRoot = Files.createTempDirectory("lambdatests");
	}

	@Test
	public void shouldReturnEmptyListForEmptyDir() throws IOException {
		assertEquals(0,testee.getSubDirectories(testRoot.toFile()).size());
	}
	
	@Test
	public void shouldReturnAllSubDirsDepthOfOne() throws IOException {
		Files.createTempDirectory(testRoot,"lambdatests1");
		Files.createTempDirectory(testRoot,"lambdatests2");
		assertEquals(2,testee.getSubDirectories(testRoot.toFile()).size());
	}
	
	@Test
	public void shouldReturnAllSubDirsDepthOfTwo() throws IOException {
		Files.createTempDirectory(testRoot,"lambdatests1");
		Path lambdaTwo = Files.createTempDirectory(testRoot,"lambdatests2");
		Files.createTempDirectory(lambdaTwo,"lambdatests3");
		assertEquals(3,testee.getSubDirectories(testRoot.toFile()).size());
	}
	
	@Test
	public void shouldReturnAllSubDirsAsArrayDepthOfTwo() throws IOException {
		Files.createTempDirectory(testRoot,"lambdatests1");
		Path lambdaTwo = Files.createTempDirectory(testRoot,"lambdatests2");
		Files.createTempDirectory(lambdaTwo,"lambdatests3");
		assertEquals(3,testee.getSubDirectoriesArray(testRoot.toFile()).length);
	}
	
	@Test
	public void runnersEnhancedExperiment() throws IOException {
		String [] names = {"Dave","Bob","Sue"};
		List <Runnable> runners = new ArrayList<>();
		for (String name: names){
			runners.add(()->System.out.println(name));
		}
		for (Runnable r: runners){
			r.run();
		}
	}
//	@Test
//	public void runnersTradExperiment() throws IOException {
//		String [] names = {"Dave","Bob","Sue"};
//		List <Runnable> runners = new ArrayList<>();
//		for (int i=0;i<names.length;i++){
//			runners.add(()->System.out.println(names[i]));
//		}
//		for (Runnable r: runners){
//			r.run();
//		}
//	}
	
	@Test
	public void optionalTest1(){
		assertEquals(4D,safeInverse(0.25).orElse(Double.POSITIVE_INFINITY),0);
	}
	
	@Test
	public void optionalTest2(){
		assertEquals(2D,safeInverse(0.25).flatMap(this::safeSqrt).orElse(Double.POSITIVE_INFINITY),0);
	}
	
	@Test
	public void optionalTest3(){
		assertEquals(Double.POSITIVE_INFINITY,safeInverse(0D).flatMap(this::safeSqrt).orElse(Double.POSITIVE_INFINITY),0);
	}
	
	@Test
	public void optionalTest4(){
		assertEquals(Double.POSITIVE_INFINITY,safeInverse(-0.25).flatMap(this::safeSqrt).orElse(Double.POSITIVE_INFINITY),0);
	}

	private Optional<Double> safeInverse(Double x){
		if(x==0){
			return Optional.empty();
		}
		return Optional.of(1/x);
	}
	
	private Optional<Double> safeSqrt(Double x){
		if(x<0){
			return Optional.empty();
		}
		return Optional.of(Math.sqrt(x));
	}
}
