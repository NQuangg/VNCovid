package com.quang.vncovid.util

import java.lang.StringBuilder

class VNCharacterUtils {
    companion object {
        fun convert(str: String): String {
            val string = str.lowercase()
            return string
                .replace("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ".toRegex(), "a")
                .replace("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ".toRegex(), "e")
                .replace("ì|í|ị|ỉ|ĩ".toRegex(), "i")
                .replace("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ".toRegex(), "o")
                .replace("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ".toRegex(), "u")
                .replace("ỳ|ý|ỵ|ỷ|ỹ".toRegex(), "y")
                .replace("đ".toRegex(), "d")
        }
    }
}