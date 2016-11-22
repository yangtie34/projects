package com.jhnu.syspermiss.catche;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.permiss.entity.Resources;
import com.jhnu.syspermiss.permiss.entity.User;

public class PermissCache {

	private static LFUCache<String, PermissCatchEntity> userIdMap = new LFUCache<String, PermissCatchEntity>(20000, 0);
	private static Map<String, String> userNameMap = new LinkedHashMap<String, String>();

	public static PermissCatchEntity getCatcheEntityById(String userId) {
		return userIdMap.get(userId);
	}

	public static PermissCatchEntity getCatcheEntityByName(String userName) {
		return getCatcheEntityById(userNameMap.get(userName));
	}

	public static void setAll(PermissCatchEntity entity) {
		if (userIdMap.isFull()) {
			userIdMap.seoCache(1);
		}
		User user = entity.getUser();
		userIdMap.put(user.getId().toString(), entity);
		userNameMap.put(user.getUsername(), user.getId().toString());
	}

	public static void setSysRes(String userName, List<Resources> Menus) {
		userIdMap.get(userNameMap.get(userName)).setSysMenus(Menus);
	}

	public static void removeByname(String userName) {
		userIdMap.remove(userNameMap.get(userName));
		userNameMap.remove(userName);
	}
}
