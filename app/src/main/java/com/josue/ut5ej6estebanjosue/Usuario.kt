package com.josue.ut5ej6estebanjosue

data class Usuario(val login:String, val contra:String, val perfil:Int=0){



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Usuario

        if (login != other.login) return false
        if (contra != other.contra) return false

        return true
    }

    override fun hashCode(): Int {
        var result = login.hashCode()
        result = 31 * result + contra.hashCode()
        return result
    }
}


