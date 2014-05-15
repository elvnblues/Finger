package com.elvn.finger.constrant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import org.apache.commons.codec.binary.Base64;

public class ConstClass {
	/**
	 * ����String��ShareedPreferences��
	 */
	public static void setSharedPreferencesVal(Context context,
			String sharedPerferencesName, String sharedPerferencesKey,
			String sharedPerferencesVal) {
		SharedPreferences sharedPrefernces = context.getSharedPreferences(
				sharedPerferencesName, Activity.MODE_PRIVATE);
		sharedPrefernces.edit()
				.putString(sharedPerferencesKey, sharedPerferencesVal).commit();
	}

	/**
	 * ����int��ShareedPreferences��
	 */
	public static void setSharedPreferencesVal(Context context,
			String sharedPerferencesName, String sharedPerferencesKey,
			int sharedPerferencesVal) {
		SharedPreferences sharedPrefernces = context.getSharedPreferences(
				sharedPerferencesName, Activity.MODE_PRIVATE);
		sharedPrefernces.edit()
				.putInt(sharedPerferencesKey, sharedPerferencesVal).commit();
	}

	/**
	 * ����boolean��ShareedPreferences��
	 */
	public static void setSharedPreferencesVal(Context context,
			String sharedPerferencesName, String sharedPerferencesKey,
			boolean sharedPerferencesVal) {
		SharedPreferences sharedPrefernces = context.getSharedPreferences(
				sharedPerferencesName, Activity.MODE_PRIVATE);
		sharedPrefernces.edit()
				.putBoolean(sharedPerferencesKey, sharedPerferencesVal)
				.commit();
	}

	/**
	 * ����float��ShareedPreferences��
	 */
	public static void setSharedPreferencesVal(Context context,
			String sharedPerferencesName, String sharedPerferencesKey,
			float sharedPerferencesVal) {
		SharedPreferences sharedPrefernces = context.getSharedPreferences(
				sharedPerferencesName, Activity.MODE_PRIVATE);
		sharedPrefernces.edit()
				.putFloat(sharedPerferencesKey, sharedPerferencesVal).commit();
	}

	/**
	 * ȡ��SharedPreferences�б��������(String)
	 * Ĭ��ֵΪ��
	 */
	public static String getSharedPreferencesVal(Context context,
			String sharedPerferencesName, String sharedPerferencesKey) {
		String sharedPreferencesVal = context.getSharedPreferences(
				sharedPerferencesName, 0).getString(sharedPerferencesKey, "");
		return sharedPreferencesVal;
	}

	/**
	 * ȡ��ShareedPreferences�б��������(int)
	 * Ĭ��ֵΪ0
	 */
	public static int getSharedPreferencesVal(String sharedPerferencesName,
			Context context, String sharedPerferencesKey) {
		if (context != null) {
			int sharedPreferencesVal = context.getSharedPreferences(
					sharedPerferencesName, 0).getInt(sharedPerferencesKey, 0);
			return sharedPreferencesVal;
		}
		return 0;
	}

	/**
	 * ȡ��ShareedPreferences�б��������(boolean)
	 * Ĭ��ֵΪfalse
	 */
	public static boolean getSharedPreferencesVal(String sharedPerferencesName,
			String sharedPerferencesKey, Context context) {
		if (context != null) {
			boolean sharedPreferencesVal = context.getSharedPreferences(
					sharedPerferencesName, 0).getBoolean(sharedPerferencesKey,
					false);
			return sharedPreferencesVal;
		}
		return false;
	}

	/**
	 * ȡ��ShareedPreferences�б��������(float)
	 * Ĭ��ֵΪ0
	 */
	public static float getFloatSharedPreferencesVal(Context context,
			String sharedPerferencesName, String sharedPerferencesKey) {
		if (context != null) {
			float sharedPreferencesVal = context.getSharedPreferences(
					sharedPerferencesName, 0).getFloat(sharedPerferencesKey, 0);
			return sharedPreferencesVal;
		}
		return 0;
	}

	/**
	 * ����Object����
	 */
	public static void setObjectSharedPreferencesVal(Activity activity,
			Object model, String sharedPerferencesName,
			String sharedPerferencesKey) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			// ���������OutputStream��
			oos = new ObjectOutputStream(baos);
			oos.writeObject(model);
			SharedPreferences listModelSharedPreferences = activity
					.getSharedPreferences(sharedPerferencesName,
							Activity.MODE_PRIVATE);
			// ���������byte�����У�����Base64Ϊ����
			String listModelBase64 = new String(Base64.encodeBase64(baos
					.toByteArray()));
			SharedPreferences.Editor editor = listModelSharedPreferences.edit();
			// ���������ַ���д������Ϊ:sharedPerferencesKey�ļ���
			editor.putString(sharedPerferencesKey, listModelBase64);
			editor.commit();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ȡ��Object���͵�
	 */
	public static Object getobjectSharedPreferencesVal(Activity activity,
			String sharedPerferencesName, String sharedPerferencesKey) {
		Object model = null;
		if (activity != null) {
			SharedPreferences listModelSharedPreferences = activity
					.getSharedPreferences(sharedPerferencesName,
							Activity.MODE_PRIVATE);
			String listModelBase64 = listModelSharedPreferences.getString(
					sharedPerferencesKey, "");
			if (listModelBase64 != null && listModelBase64 != "") {
				// ��Base64λ���н���
				byte[] base64Bytes = Base64.decodeBase64(listModelBase64
						.getBytes());
				ByteArrayInputStream bais = new ByteArrayInputStream(
						base64Bytes);
				try {
					ObjectInputStream ois = new ObjectInputStream(bais);
					model = ois.readObject();
				} catch (StreamCorruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return model;
	}

	/**
	 * ���SharedPreferences
	 */
	@SuppressWarnings("static-access")
	public static void clearSharedPreferences(Context context,
			String sharedPreferencesName) {
		SharedPreferences spf = context.getSharedPreferences(
				sharedPreferencesName, context.MODE_PRIVATE);
		spf.edit().clear().commit();
	}
}