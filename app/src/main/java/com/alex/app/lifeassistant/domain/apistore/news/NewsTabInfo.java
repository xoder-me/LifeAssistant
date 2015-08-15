package com.alex.app.lifeassistant.domain.apistore.news;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.lang.reflect.Constructor;

/**
 * 单个选项卡类，每个选项卡包含名字，图标以及提示（可选，默认不显示）
 * <p/>
 * Created by alex.lee on 2015-07-07.
 */
public class NewsTabInfo implements Parcelable {
	private int id;
	private int icon;
	private String name = null;
	public boolean hasTips = false;
	public Fragment fragment = null;
	public boolean notifyChange = false;
	public Class fragmentClass = null;

	public NewsTabInfo(int id, String name, Class clazz) {
		this(id, name, 0, clazz);
	}

	public NewsTabInfo(int id, String name, boolean hasTips, Class clazz) {
		this(id, name, 0, clazz);
		this.hasTips = hasTips;
	}

	public NewsTabInfo(int id, String name, int iconid, Class clazz) {
		super();

		this.name = name;
		this.id = id;
		icon = iconid;
		fragmentClass = clazz;
	}

	public NewsTabInfo(Parcel p) {
		this.id = p.readInt();
		this.name = p.readString();
		this.icon = p.readInt();
		this.notifyChange = p.readInt() == 1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIcon(int iconid) {
		icon = iconid;
	}

	public int getIcon() {
		return icon;
	}

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	public Fragment createFragment() {
		if (fragment == null) {
			Constructor constructor;
			try {
				constructor = fragmentClass.getConstructor();
				fragment = (Fragment) constructor.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fragment;
	}

	public static final Creator<NewsTabInfo> CREATOR = new Creator<NewsTabInfo>() {
		public NewsTabInfo createFromParcel(Parcel p) {
			return new NewsTabInfo(p);
		}

		public NewsTabInfo[] newArray(int size) {
			return new NewsTabInfo[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p, int flags) {
		p.writeInt(id);
		p.writeString(name);
		p.writeInt(icon);
		p.writeInt(notifyChange ? 1 : 0);
	}
}
