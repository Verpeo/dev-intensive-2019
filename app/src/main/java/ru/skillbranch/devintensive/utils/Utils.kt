package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?): Pair<String?,String?>{
        val parts: List<String>? = fullName?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return Pair(firstName,lastName)
    }

    fun transliteration(payload: String,divider:String = " "): String {
        var Str:String = ""
        payload.forEach { char -> Str += cir2trans(char) }
        return Str
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return "${if (!firstName.isNullOrEmpty()) firstName.get(0)else ""}${if (!lastName.isNullOrEmpty()) lastName.get(0) else ""}"
    }

    private fun cir2trans(char: Char):String?{
        var letter:String =         when(char.toLowerCase()){
            'а' -> "a"
            'б' -> "b"
            'в' -> "v"
            'г' -> "g"
            'д' -> "d"
            'е' -> "e"
            'ё' -> "yo"
            'ж' -> "zh"
            'з' -> "z"
            'и' -> "i"
            'й' -> "y"
            'к' -> "k"
            'л' -> "l"
            'м' -> "m"
            'н' -> "n"
            'о' -> "o"
            'п' -> "p"
            'р' -> "r"
            'с' -> "s"
            'т' -> "t"
            'у' -> "u"
            'ф' -> "f"
            'х' -> "kh"
            'ц' -> "c"
            'ч' -> "ch"
            'ш' -> "sh"
            'щ' -> "jsh"
            'Ъ' -> "hh"
            'ы' -> "ih"
            'ь' -> ""
            'э' -> "eh"
            'ю' -> "ju"
            'я' -> "ya"
            else -> char.toString()
        }
        if (char.isUpperCase() ){
            letter = letter.capitalize()
        }
        return letter
    }
}