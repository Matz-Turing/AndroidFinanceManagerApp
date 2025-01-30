package com.example.orcamentopessoal.data

import androidx.room.TypeConverter
import com.example.orcamentopessoal.model.TipoTransacao
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromTipoTransacao(tipo: TipoTransacao): String {
        return tipo.name
    }

    @TypeConverter
    fun toTipoTransacao(tipo: String): TipoTransacao {
        return TipoTransacao.valueOf(tipo)
    }
}
