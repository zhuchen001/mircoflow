/**
 * 
 */
package com.center.microflow.impl;

import com.center.microflow.api.DeepCopy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author Administrator
 *
 */
public class DeepCopyDefaultImpl implements DeepCopy {

	private static final Logger log = LoggerFactory.getLogger(DeepCopyDefaultImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.center.microflow.api.IDeepCopy#copy(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T copy(T obj) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;

		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;

		Object o = null;
		// 如果子类没有继承该接口，这一步会报错
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			bais = new ByteArrayInputStream(baos.toByteArray());
			ois = new ObjectInputStream(bais);

			o = ois.readObject();
			return (T) o;
		} catch (Exception e) {
			log.error(e.toString(), e);
			return null;
		} finally {
			closeStream(baos);
			closeStream(oos);
			closeStream(bais);
			closeStream(ois);
		}
	}

	@Override
	public int order() {
		return 0;
	}

	private void closeStream(Closeable out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				log.error(e.toString(), e);
			}
		}
	}

}
