package com.example.profile_nfc.scanning.utility

fun splitter(fullText: String): String {
    return fullText.split('.')[3]
}

fun tagTypeSplitter(tag: String): List<String> {
    return tag.split("[", "]")[1].split(", ")
}
