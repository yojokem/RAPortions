package filestealer;

import java.nio.file.Paths;

import net.frostq.filestealer.FileStealer;
import net.frostq.filestealer.stealer.Stealer;

public class 기능테스트 {
	public static void main(String[] args) {
		Stealer s = new Stealer(Paths.get("C:\\Users\\yojok\\Desktop\\2020\\교육\\온라인 개학"), -1, null, null);
		s.setOutputDirectory(Paths.get("C:\\Users\\yojok\\Documents\\DD"));
		System.out.println(s.getCursor());
		
		System.out.println(s.getAveragedCapacity());
		System.out.println(s.getMiddleCapacity());
		System.out.println(s.getCenteredCapacity());
		
		System.out.println(FileStealer.instances.size());
		System.out.println(s.isForDivision());
		s.start();
		
		System.out.println(s.getOutputPath());
	}
}