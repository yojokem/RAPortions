package com.bombom_9.raportions;

import java.io.PrintStream;

public interface Logger1 {
	public static PrintStream out() {
		return System.out;
	}
	
	public String getPrefix();
	public String getSuffix();
	public default String getOnlyForPrint(Object content) {
		return getPrefix() + " " + content + " " + getSuffix();
	}
	
	public default void println(Object content) {
		rawPrintln(getOnlyForPrint(content));
	}
	
	public default void print(Object content) {
		rawPrint(getOnlyForPrint(content));
	}
	
	public default void rawPrintln(Object content) {
		rawPrint(content + ""); //왜 \n이 자꾸 한 줄 더 띄운 채로 나오는지 잘 모르겠지만.. 우선 삭제.
	}
	
	public default void rawPrint(Object content) {
		out().print(content);
	}
}