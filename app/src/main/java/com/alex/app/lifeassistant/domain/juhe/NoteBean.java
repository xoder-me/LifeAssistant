package com.alex.app.lifeassistant.domain.juhe;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by alex.lee on 2015-07-19.
 */
@Table(name = "note")
public class NoteBean implements Parcelable {
	private int id;
	@Column(column = "content")
	private String content; // 内容
	@Column(column = "picture")
	private String picture; // 配图
	@Column(column = "date")
	private String date;    // 时间

	@Override
	public String toString() {
		return "NoteBean{" +
				"id=" + id +
				", content='" + content + '\'' +
				", picture='" + picture + '\'' +
				", date='" + date + '\'' +
				'}';
	}

	public NoteBean() {
	}

	public NoteBean(String content, String date) {
		this.content = content;
		this.date = date;
	}

	public NoteBean(String content, String picture, String date) {
		this.content = content;
		this.picture = picture;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.content);
		dest.writeString(this.picture);
		dest.writeString(this.date);
	}

	private NoteBean(Parcel in) {
		this.id = in.readInt();
		this.content = in.readString();
		this.picture = in.readString();
		this.date = in.readString();
	}

	public static final Parcelable.Creator<NoteBean> CREATOR = new Parcelable.Creator<NoteBean>() {
		public NoteBean createFromParcel(Parcel source) {
			return new NoteBean(source);
		}

		public NoteBean[] newArray(int size) {
			return new NoteBean[size];
		}
	};
}
