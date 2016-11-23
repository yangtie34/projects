package cn.gilight.framework.uitl.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ListUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> deepClone(List<T> list1) {
		try {
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(buf);
			out.writeObject(list1);
			out.close();
			byte[] bytes = buf.toByteArray();
			ByteArrayInputStream byIn = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(byIn);
			List<T> list2 = (List<T>) in.readObject();
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
