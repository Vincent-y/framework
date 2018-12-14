package com.java.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.nustaq.serialization.FSTConfiguration;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.*;

/**
 * <p>
 * tiger序列化
 * <p>
 * @author dragon
 * <p>
 * @date 2015年9月6日
 * <p>
 * @version 1.0
 */
public class ObjectSerialize {

	/** FST序列化 **/
	private static FSTConfiguration configuration = FSTConfiguration.createStructConfiguration();
	public static byte[] fstSerialize(Object obj) {
		return configuration.asByteArray(obj);
	}
	@SuppressWarnings("unchecked")
	public static <T> T fstDeSerialize(byte[] sec,Class<T> cls) {
		return (T) configuration.asObject(sec);
	}
	
	
	public static byte[] kryoSerizlize(Object obj) {
		Kryo kryo = new Kryo();
		
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		
		byte[] buffer = new byte[2048];
		try (Output output = new Output(buffer);) {

			kryo.writeClassAndObject(output, obj);
			return output.toBytes();
		} catch (Exception e) {
		}
		return buffer;
	}

	/** Kryo序列化 **/
	static Kryo kryo = new Kryo();
	@SuppressWarnings("unchecked")
	public static <T>T kryoDeSerizlize(byte[] src,Class<T> cls) {
		try (Input input = new Input(src);) {
			kryo.setReferences(false);
			kryo.setRegistrationRequired(false);
			kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
			Registration registration = kryo.register(cls);
			return (T) kryo.readObject(input, registration.getType());
			//return (T) kryo.readClassAndObject(input);
		} catch (Exception e) {
		}
		return (T) kryo;
	}

	/**jdk原生序列换方案**/
	public static byte[] jdkSerialize(Object obj) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);) {
			oos.writeObject(obj);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T jdkDeserialize(byte[] bits,Class<T> cls) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(bits);
				ObjectInputStream ois = new ObjectInputStream(bais);) {
			return (T) ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
