package com.ayuan.readcontact;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取联系人的工具类
 */
public class ReadContastUtils {

    private static String TAG = "ReadContastUtils";

    public static List<Contact> readContact(Context context) {
        //创建集合对象
        ArrayList<Contact> contactLists = new ArrayList<>();

        //由于联系人的数据库也是通过内容提供者暴露出来了 所以想操作数据库就要使用内容解析者
        Uri raw_contactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        //先查询raw_contacts表的contact_id字段的值
        Cursor query = context.getContentResolver().query(raw_contactsUri, new String[]{"contact_id"}, null, null, null);
        if (query != null && query.getCount() > 0) {
            while (query.moveToNext()) {
                String contact_id = query.getString(0);
                if (contact_id == null) {
                    continue;
                }
                //创建JavaBean对象
                Contact contact = new Contact();
                contact.setId(contact_id);
                //根据contact_id去查询data表的data1字段和mimetype_id字段
                //查询的不是data表 二十View_data的视图  查询条件为联系人id
                Cursor query1 = context.getContentResolver().query(dataUri, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{contact_id}, null);
                if (query1 != null && query1.getCount() > 0) {
                    while (query1.moveToNext()) {
                        String data1 = query1.getString(0);
                        String mimetype_id = query1.getString(1);
                        if ("vnd.android.cursor.item/name".equals(mimetype_id)) {
                            contact.setName(data1);
                        } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype_id)) {
                            contact.setPhone(data1);
                        } else if ("vnd.android.cursor.item/email_v2".equals(mimetype_id)) {
                            contact.setEmail(data1);
                        } else if ("vnd.android.cursor.item/postal-address_v2".equals(mimetype_id)) {
                            contact.setAddress(data1);
                        }
                    }
                    contactLists.add(contact);
                }

            }
        }
        return contactLists;
    }
}
