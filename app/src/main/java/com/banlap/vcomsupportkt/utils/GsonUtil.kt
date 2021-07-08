package com.banlap.vcomsupportkt.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type
import java.util.*

/**
 *@author Banlap on 2021/7/2
 */

/** 饿汉式实现单实例 */
object GsonUtil {
    private var gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()
    /**
     * 对象转换json
     * */
    fun toJson(objects: Objects?): String {
        objects?.let{}?.let{return "{}"}
        return gson.toJson(objects)
    }
    fun toJson(any: Any?): String {
        any?.let{}?.let{return "{}"}
        return gson.toJson(any)
    }
    /**
     * 根据json 按key值获取value
     * */
    fun getValue(data: String, key: String): String? {
        try {
            val jsonObject = JSONObject(data)
            return jsonObject.getString(key)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     *  根据json 按key值获取value（ boolean布尔值 ）
     * */
    fun getBooleanValue(data: String, key: String): Boolean {
        try {
            val jsonObject = JSONObject(data)
            return jsonObject.getBoolean(key)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 将json格式转换成List对象
     */
    fun <T> jsonToList(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }
}