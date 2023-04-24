package org.luismi.objects.example.manage.contracts

/**
 * @author Luis Miguel Barcos
 */
data class CustomObject(
    val name: String,
    val pluralName: String,
    val fieldName: String,
    val customObjectResourceName: String,
    val objectLayoutResourceName: String,
    val layoutName: String = "${name}Layout"
)
