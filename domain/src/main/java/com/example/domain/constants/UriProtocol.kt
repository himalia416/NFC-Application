package com.example.domain.constants

//    NFC Forum "URI Record Type Definition"
//    This is a mapping of "URI Identifier Codes" to URI string prefixes, per section 3.2.2 of the NFC Forum URI Record Type Definition document.
val protocols = arrayOf(
    "", // 0x00
    "http://www.", // 0x01
    "https://www.", // 0x02
    "http://", // 0x03
    "https://", // 0x04
    "tel:", // 0x05
    "mailto:", // 0x06
    "ftp://anonymous:anonymous@", // 0x07
    "ftp://ftp.", // 0x08
    "ftps://", // 0x09
    "sftp://", // 0x0A
    "smb://", // 0x0B
    "nfs://", // 0x0C
    "ftp://", // 0x0D
    "dav://", // 0x0E
    "news:", // 0x0F
    "telnet://", // 0x10
    "imap:", // 0x11
    "rtsp://", // 0x12
    "urn:", // 0x13
    "pop:", // 0x14
    "sip:", // 0x15
    "sips:", // 0x16
    "tftp:", // 0x17
    "btspp://", // 0x18
    "btl2cap://", // 0x19
    "btgoep://", // 0x1A
    "tcpobex://", // 0x1B
    "irdaobex://", // 0x1C
    "file://", // 0x1D
    "urn:epc:id:", // 0x1E
    "urn:epc:tag:", // 0x1F
    "urn:epc:pat:", // 0x20
    "urn:epc:raw:", // 0x21
    "urn:epc:", // 0x22
    "urn:nfc:" // 0x23
)